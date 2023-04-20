package tutorial.model;

import tutorial.scenario.Store;
import tutorial.scenario.Warehouse;

public class TransportationRequest {
	private final int id;
	private final Warehouse warehouse;
	private final Store store;
	private final double createdTime;
	private final double deadlineTime;
	private boolean completed;
	private double completedTime;
	
	public TransportationRequest(int id, Warehouse warehouse, Store store, double beginTime, double deadlineTime) {
		this.id = id;
		this.warehouse = warehouse;
		this.store = store;
		this.createdTime = beginTime;
		this.deadlineTime = deadlineTime;
		//System.out.println(String.format("%.3f\tRequest #%s created", createdTime, id));
	}
	
	public int getId() {
		return id;
	}
	
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	public Store getStore() {
		return store;
	}

	public double getCreatedTime() {
		return createdTime;
	}

	public double getDeadlineTime() {
		return deadlineTime;
	}

	public double getCompletedTime() {
		return completedTime;
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompletedTime(double completedTime) {
		this.completedTime = completedTime;
		this.completed = true;
	}
}
