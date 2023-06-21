package tutorial.model;

import java.util.function.Consumer;

public class TransportationTask {
	
	public enum Status {
		NOT_STARTED, IN_PROGRESS, COMPLETED_ON_TIME, COMPLETED_AFTER_DEADLINE;
	}
	
	private final String id;
	private Truck truck;
	private final TransportationRequest request;
	private final Consumer<TransportationTask> taskCompletedHandler;
	private final Model model;
	private double beginTime;

	public TransportationTask(String id, TransportationRequest request,
			Consumer<TransportationTask> taskCompletedHandler, Model model) {
		this.id = id;
		this.request = request;
		this.taskCompletedHandler = taskCompletedHandler;
		this.model = model;
	}

	public String getId() {
		return id;
	}

	public Truck getTruck() {
		return truck;
	}

	public Status getStatus() {
		if (truck == null) {
			return Status.NOT_STARTED;
		}
		if (request.isCompleted()) {
			return request.getCompletedTime() <= request.getDeadlineTime() ? Status.COMPLETED_ON_TIME : Status.COMPLETED_AFTER_DEADLINE;
		}
		return Status.IN_PROGRESS;
	}
	
	public TransportationRequest getRequest() {
		return request;
	}
	
	public double getBeginTime() {
		return beginTime;
	}

	public void execute(Truck truck) {
		this.truck = truck;
		this.beginTime = model.engine().time();
		double toWarehouseDistance = model.getRouteLength(truck.getCurrentAsset(), request.getSourceAsset());
		double warehouseToStoreDistance = model.getRouteLength(request.getSourceAsset(), request.getDestAsset());
		double totalTravelTime = (toWarehouseDistance + warehouseToStoreDistance) / truck.getSpeed();
		//System.out.println("%.3f\tTask #%s : TRANSPORTATION_STARTED. Request #%s; Truck #%s at %s; From %s -> To %s"
		//		.formatted(model.engine().time(), getId(), request.getId(), truck.getId(), truck.getCurrentAsset().getName(), 
		//				request.getSourceAsset().getName(), request.getDestAsset().getName()));
		truck.onTaskStarted(this);
		model.engine().scheduleRelative(totalTravelTime, () -> {
			truck.onTaskCompleted();
			request.setCompletedTime(model.engine().time());
		//	System.out.println("%.3f\tTask #%s : TRANSPORTATION_FINISHED".formatted(model.engine().time(), getId()));
			taskCompletedHandler.accept(this);
		});
	}

}
