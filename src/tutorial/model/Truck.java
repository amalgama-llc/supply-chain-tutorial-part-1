package tutorial.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.utils.Pair;

public class Truck {
    private final double OWNERSHIP_COST_PER_HOUR = 10;
    private final double USAGE_COST_PER_HOUR = 25;

    private final String id;
    private final String name;
    private final double speed;
    private final Engine engine;

    private List<Pair<Double, Double>> activePeriods = new ArrayList<>();
    private Optional<Double> currentActivePeriodStartTime = Optional.empty();
    private TransportationTask currentTask;
    private List<TransportationTask> taskHistory = new ArrayList<>();

    public Truck(String id, String name, double speed, Engine engine) {
        this.id = id;
        this.name = name;
        this.engine = engine;
        this.speed = speed;
        //System.out.println(String.format("Truck #%s created", id));
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
        return ownershipDurationHours * OWNERSHIP_COST_PER_HOUR + usageDurationHours * USAGE_COST_PER_HOUR;
    }

    private double getAllActivePeriodsDurationHrs() {
        double result = activePeriods.stream().mapToDouble(p -> p.second - p.first).sum();
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
        activePeriods.add(new Pair<>(currentActivePeriodStartTime.get(), engine.time()));
        currentActivePeriodStartTime = Optional.empty();
        currentTask = null;
    }
}