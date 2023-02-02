package tutorial.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.math3.distribution.RealDistribution;

import com.amalgamasimulation.engine.Engine;

public class RequestGenerator {
  private final Engine engine;

  private final RealDistribution newRequestIntervalDistribution;
  private final RealDistribution routeLengthDistribution;
  private final double maxDeliveryTimeHrs;
  private List<Consumer<TransportationRequest>> newRequestHandlers = new ArrayList<>();
  private int lastUsedRequestId = 0;

  public RequestGenerator(Engine engine, RealDistribution newRequestIntervalDistribution, RealDistribution routeLengthDistribution,
      double maxDeliveryTimeHrs) {
    this.engine = engine;
    this.newRequestIntervalDistribution = newRequestIntervalDistribution;
    this.routeLengthDistribution = routeLengthDistribution;
    this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
    engine.scheduleRelative(0, this::createTransportationRequest);
  }

  public void addNewRequestHandler(Consumer<TransportationRequest> newRequestHandler) {
    newRequestHandlers.add(newRequestHandler);
  }

  private void createTransportationRequest() {
    TransportationRequest newRequest = new TransportationRequest(getNextRequestId(), routeLengthDistribution.sample(), engine.time(), engine.time() + maxDeliveryTimeHrs * engine.hour());
    newRequestHandlers.forEach(handler -> handler.accept(newRequest));
    engine.scheduleRelative(newRequestIntervalDistribution.sample(), this::createTransportationRequest);
  }

  private int getNextRequestId() {
    return ++lastUsedRequestId;
  }
}