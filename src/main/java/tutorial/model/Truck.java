package tutorial.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.amalgamasimulation.engine.Engine;

import tutorial.scenario.Asset;
import tutorial.scenario.Store;

public class Truck {
	private static final double OWNERSHIP_COST_PER_HOUR_USD = 10;
	private static final double USAGE_COST_PER_HOUR_USD = 25;
	
	private final String id;
	private final String name;
	private final double speed;
	private final Engine engine;
	
	private record ActivePeriod(double startTime, double endTime) {}

	private List<ActivePeriod> activePeriods = new ArrayList<>();
	private Optional<Double> currentActivePeriodStartTime = Optional.empty();
	private Asset currentAsset;
	private TransportationTask currentTask;
	private List<TransportationTask> taskHistory = new ArrayList<>();

	public Truck(String id, String name, double speed, Store initialStore, Engine engine) {
		this.id = id;
		this.name = name;
		this.engine = engine;
		this.speed = speed;
		this.currentAsset = initialStore;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public Asset getCurrentAsset() {
		return currentAsset;
	}
	
	public TransportationTask getCurrentTask() {
		return currentTask;
	}
	
	public List<TransportationTask> getTaskHistory() {
		return taskHistory;
	}
	
	public boolean isIdle() {
		return currentTask == null;
	}

	public double getExpenses() {
		double ownershipDurationHours = engine.time() / engine.hour();
		double usageDurationHours = getAllActivePeriodsDurationHrs();
		return ownershipDurationHours * OWNERSHIP_COST_PER_HOUR_USD + usageDurationHours * USAGE_COST_PER_HOUR_USD; 
	}

	private double getAllActivePeriodsDurationHrs() {
		double result = activePeriods.stream().mapToDouble(p -> p.endTime - p.startTime).sum();
		if (currentActivePeriodStartTime.isPresent()) {
			result += (engine.time() - currentActivePeriodStartTime.get()) / engine.hour();
		}
		return result;
	}

	public void onTaskStarted(TransportationTask task) {
		currentActivePeriodStartTime = Optional.of(engine.time());
		currentTask = task;
		taskHistory.add(currentTask);
	}

	public void onTaskCompleted() {
		activePeriods.add(new ActivePeriod(currentActivePeriodStartTime.get(), engine.time()));
		currentActivePeriodStartTime = Optional.empty();
		currentAsset = currentTask.getRequest().getDestAsset();
		currentTask = null;
	}
}
