package tutorial.model;

import java.util.function.Consumer;
import java.util.function.ToDoubleBiFunction;

import com.amalgamasimulation.engine.Engine;

import tutorial.scenario.Asset;

public class TransportationTask {
	private final String id;
	private final Truck truck;
	private final TransportationRequest request;
	private final ToDoubleBiFunction<Asset, Asset> routeLengthGetter;
	private final Consumer<Truck> truckReleaseHandler;
	private final Engine engine;
	private double beginTime;

	public TransportationTask(String id, Truck truck, TransportationRequest request, ToDoubleBiFunction<Asset, Asset> routeLengthGetter,
			Consumer<Truck> truckReleaseHandler, Engine engine) {
		this.id = id;
		this.truck = truck;
		this.request = request;
		this.routeLengthGetter = routeLengthGetter;
		this.truckReleaseHandler = truckReleaseHandler;
		this.engine = engine;
	}

	public String getId() {
		return id;
	}

	public Truck getTruck() {
		return truck;
	}

	public TransportationRequest getRequest() {
		return request;
	}
	
	public double getBeginTime() {
		return beginTime;
	}

	public void execute() {
		this.beginTime = engine.time();
		double toWarehouseDistance = routeLengthGetter.applyAsDouble(truck.getCurrentStore(), request.getWarehouse());
		double warehouseToStoreDistance = routeLengthGetter.applyAsDouble(request.getWarehouse(), request.getStore());
		double totalTravelTime = (toWarehouseDistance + warehouseToStoreDistance) / truck.getSpeed();
		//System.out.println("%.3f\tTask #%s : TRANSPORTATION_STARTED. Request #%s; Truck #%s at %s; From %s -> To %s"
		//		.formatted(engine.time(), getId(), request.getId(), truck.getId(), truck.getCurrentStore().getName(), 
		//				request.getWarehouse().getName(), request.getStore().getName()));
		truck.onTaskStarted(this);
		engine.scheduleRelative(totalTravelTime, () -> {
			truck.onTaskCompleted();
			request.setCompletedTime(engine.time());
			//System.out.println("%.3f\tTask #%s : TRANSPORTATION_FINISHED".formatted(engine.time(), getId()));
			truckReleaseHandler.accept(truck);
		});
	}

}
