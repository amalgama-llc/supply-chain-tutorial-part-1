package tutorial.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import com.amalgamasimulation.engine.Engine;

import tutorial.scenario.Store;
import tutorial.scenario.Warehouse;

public class RequestGenerator {
	private final Engine engine;
	private final RealDistribution newRequestIntervalDistribution;
	private final RandomGenerator randomGenerator;
	private final List<Warehouse> warehouses;
	private final List<Store> stores;
	
	private final double maxDeliveryTimeHrs;
	private List<Consumer<TransportationRequest>> newRequestHandlers = new ArrayList<>();
	private int lastUsedRequestId = 0;

	public RequestGenerator(Engine engine, 
							RealDistribution newRequestIntervalDistribution,
							RandomGenerator randomGenerator,
							List<Warehouse> warehouses,
							List<Store> stores,
							double maxDeliveryTimeHrs) {
		this.engine = engine;
		this.newRequestIntervalDistribution = newRequestIntervalDistribution;
		this.randomGenerator = randomGenerator;
		this.warehouses = warehouses;
		this.stores = stores;
		this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
		engine.scheduleRelative(0, this::createTransportationRequest);
	}
	
	public void addNewRequestHandler(Consumer<TransportationRequest> newRequestHandler) {
		newRequestHandlers.add(newRequestHandler);
	}
	
	private void createTransportationRequest() {
		Warehouse from = warehouses.get(randomGenerator.nextInt(warehouses.size()));
		Store to = stores.get(randomGenerator.nextInt(stores.size()));
		TransportationRequest newRequest = new TransportationRequest(getNextRequestId(), from, to,
				engine.time(), engine.time() + maxDeliveryTimeHrs * engine.hour());
		newRequestHandlers.forEach(handler -> handler.accept(newRequest));
		engine.scheduleRelative(newRequestIntervalDistribution.sample(), this::createTransportationRequest);		
	}
	
	private int getNextRequestId() {
		return ++lastUsedRequestId;
	}
}
