package tutorial.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.utils.random.DefaultRandomGenerator;

import tutorial.Mapping;
import tutorial.scenario.Scenario;

public class Model extends com.amalgamasimulation.engine.Model {

	private final Scenario scenario;
	private final RandomGenerator randomGenerator = new DefaultRandomGenerator(1);
	private Mapping mapping = new Mapping();
	private List<Warehouse> warehouses = new ArrayList<>();
	private List<Store> stores = new ArrayList<>();
	private List<Truck> trucks = new ArrayList<>();
	private List<TransportationRequest> requests = new ArrayList<>();
	private Queue<TransportationRequest> waitingRequests = new LinkedList<>();

	private final Dispatcher dispatcher;

	private final Statistics statistics;
	
	public Model(Engine engine, Scenario scenario) {
		super(engine);
		engine.setTemporal(scenario.getSimulationStartDt(), ChronoUnit.HOURS);
		engine.scheduleStop(engine.dateToTime(scenario.getSimulationEndDt()), "Stop");
		this.scenario = scenario;
	
		initializeModelObjects();
	
		RealDistribution newRequestIntervalDistribution = new ExponentialDistribution(randomGenerator, scenario.getIntervalBetweenRequestsHrs());
		RequestGenerator requestGenerator = new RequestGenerator(this, newRequestIntervalDistribution, scenario.getMaxDeliveryTimeHrs());
		requestGenerator.addNewRequestHandler(this::addRequest);

		dispatcher = new Dispatcher(this);
		requestGenerator.addNewRequestHandler(dispatcher::onNewRequest);
	
		statistics = new Statistics(this);
	}
	
	public double getRouteLength(Asset asset1, Asset asset2) {
		return scenario.getRouteLength(mapping.assetsMap.key(asset1), mapping.assetsMap.key(asset2));
	}
	
	private void initializeModelObjects() {
		initializeAssets();
		initializeTrucks();
	}
	
	private void initializeTrucks() {
		var initialAssetForTrucks = getStores().get(0);
		for (int i = 0; i < scenario.getTruckCount(); i++) {
			trucks.add(new Truck(String.valueOf(i + 1), String.valueOf(i + 1), scenario.getTruckSpeed(), initialAssetForTrucks, engine()));
		}
	}
	
	private void initializeAssets() {
		for (var scenarioWarehouse : scenario.getWarehouses()) {
			var wh = new Warehouse(scenarioWarehouse.getId(), scenarioWarehouse.getName());
			warehouses.add(wh);
			mapping.assetsMap.put(scenarioWarehouse, wh);
		}
		for (var scenarioStore : scenario.getStores()) {
			var store = new Store(scenarioStore.getId(), scenarioStore.getName());
			stores.add(store);
			mapping.assetsMap.put(scenarioStore, store);
		}
	}
	
	public RandomGenerator getRandomGenerator() {
		return randomGenerator;
	}
	
	public List<Truck> getTrucks() {
		return trucks;
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	
	public List<Store> getStores() {
		return stores;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public List<TransportationRequest> getRequests() {
		return requests;
	}
	
	public void addRequest(TransportationRequest request) {
		requests.add(request);
	}
	
	public TransportationRequest getNextWaitingRequest() {
		return waitingRequests.poll();
	}
	
	public void addWaitingRequest(TransportationRequest request) {
		waitingRequests.add(request);
	}

}
