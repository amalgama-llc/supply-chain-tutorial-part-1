package tutorial.model;

public class TransportationRequest {
    private final int id;
    private final double routeLength;
    private final double createdTime;
    private final double deadlineTime;
    private double completedTime;
    private boolean completed;

    public TransportationRequest(int id, double routeLength, double beginTime, double deadlineTime) {
        this.id = id;
        this.routeLength = routeLength;
        this.createdTime = beginTime;
        this.deadlineTime = deadlineTime;
        //System.out.println(String.format("%.3f Request #%s created", createdTime, id));
    }

    public int getId() {
        return id;
    }

    public double getRouteLength() {
        return routeLength;
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