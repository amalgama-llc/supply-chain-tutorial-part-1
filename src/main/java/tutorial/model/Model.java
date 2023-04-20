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

import tutorial.scenario.Scenario;
import tutorial.scenario.Store;

public class Model extends com.amalgamasimulation.engine.Model {

	private final RandomGenerator randomGenerator = new DefaultRandomGenerator(1);
	private final Scenario scenario;
	private List<Truck> trucks = new ArrayList<>();
	private List<TransportationRequest> requests = new ArrayList<>();
	private Queue<TransportationRequest> waitingRequests = new LinkedList<>();
	private final Statistics statistics;
	
	public Model(Engine engine, Scenario scenario) {
		super(engine);
		engine.setTemporal(scenario.getSimulationStartDt(), ChronoUnit.HOURS);
		engine.scheduleStop(engine.dateToTime(scenario.getSimulationEndDt()), "Stop");
		this.scenario = scenario;

		RealDistribution newRequestIntervalDistribution = new ExponentialDistribution(randomGenerator, scenario.getIntervalBetweenRequestsHrs());
		RequestGenerator requestGenerator = new RequestGenerator(engine, newRequestIntervalDistribution, randomGenerator,
				scenario.getWarehouses(), scenario.getStores(), scenario.getMaxDeliveryTimeHrs());
		
		initializeModelObjects();

		requestGenerator.addNewRequestHandler(this::addRequest);
		
		Dispatcher dispatcher = new Dispatcher(engine, this, scenario::getRouteLength);
		requestGenerator.addNewRequestHandler(dispatcher::onNewRequest);
		
		this.statistics = new Statistics(engine, this);
	}
	
	private void initializeModelObjects() {
		initializeTrucks();
	}

	private void initializeTrucks() {
		Store initialAssetForTrucks = scenario.getStores().get(0);
		for (int i = 0; i < scenario.getTruckCount(); i++) {
			trucks.add(new Truck(String.valueOf(i + 1), String.valueOf(i + 1), scenario.getTruckSpeed(), initialAssetForTrucks, engine()));
		}
	}
	
	public RandomGenerator getRandomGenerator() {
		return randomGenerator;
	}
	
	public List<Truck> getTrucks() {
		return trucks;
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
