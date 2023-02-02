package tutorial.model;

import java.util.function.Consumer;
import com.amalgamasimulation.engine.Engine;

public class TransportationTask {
  private final String id;
  private final Truck truck;
  private final TransportationRequest request;
  private final Consumer<Truck> truckReleaseHandler;
  private final Engine engine;
  private final double duration;

  private double beginTime;

  public TransportationTask(String id, Truck truck, TransportationRequest request,
      Consumer<Truck> truckReleaseHandler, Engine engine) {
    this.id = id;
    this.truck = truck;
    this.request = request;
    this.truckReleaseHandler = truckReleaseHandler;
    this.engine = engine;
    this.duration = request.getRouteLength() / truck.getSpeed();
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
    truck.onTaskStarted(this);
    engine.scheduleRelative(duration, () -> {
      truck.onTaskCompleted();
      request.setCompletedTime(engine.time());
      truckReleaseHandler.accept(truck);
    });
  }
}