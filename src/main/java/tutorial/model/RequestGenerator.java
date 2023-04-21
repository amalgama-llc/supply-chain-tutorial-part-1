package tutorial.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import com.amalgamasimulation.engine.Engine;

public class RequestGenerator {
	private final Engine engine;
	private final RealDistribution newRequestIntervalDistribution;
	private final double maxDeliveryTimeHrs;
	private List<Consumer<TransportationRequest>> newRequestHandlers = new ArrayList<>();
	private int lastUsedRequestId = 0;
	private final Model model;

	public RequestGenerator(Model model, 
							RealDistribution newRequestIntervalDistribution,
							double maxDeliveryTimeHrs) {
		this.engine = model.engine();
		this.model = model;
		this.newRequestIntervalDistribution = newRequestIntervalDistribution;
		this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
		engine.scheduleRelative(0, this::createTransportationRequest);
	}
	
	public void addNewRequestHandler(Consumer<TransportationRequest> newRequestHandler) {
		newRequestHandlers.add(newRequestHandler);
	}
	
	private void createTransportationRequest() {
		Warehouse from = model.getWarehouses().get(model.getRandomGenerator().nextInt(model.getWarehouses().size()));
		Store to = model.getStores().get(model.getRandomGenerator().nextInt(model.getStores().size()));
		TransportationRequest newRequest = new TransportationRequest(getNextRequestId(), from, to,
				engine.time(), engine.time() + maxDeliveryTimeHrs * engine.hour());
		newRequestHandlers.forEach(handler -> handler.accept(newRequest));
		engine.scheduleRelative(newRequestIntervalDistribution.sample(), this::createTransportationRequest);		
	}
	
	private int getNextRequestId() {
		return ++lastUsedRequestId;
	}
}
