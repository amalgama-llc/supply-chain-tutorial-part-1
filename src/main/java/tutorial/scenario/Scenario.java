package tutorial.scenario;

import java.time.LocalDateTime;

public class Scenario {
  private final int truckCount;
  private final double truckSpeed;
  private final double intervalBetweenRequests;
  private final double maxDeliveryTimeHrs;
  private final double[] routeLengthDistribution;
  private final LocalDateTime simulationStartDt;
  private final LocalDateTime simulationEndDt;

  public Scenario(int truckCount,
      double truckSpeed,
      double intervalBetweenRequests,
      double maxDeliveryTimeHrs,
      double[] routeLengthDistribution,
      LocalDateTime simulationStartDt, LocalDateTime simulationEndDt) {
    this.truckCount = truckCount;
    this.truckSpeed = truckSpeed;
    this.intervalBetweenRequests = intervalBetweenRequests;
    this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
    this.routeLengthDistribution = routeLengthDistribution;
    this.simulationStartDt = simulationStartDt;
    this.simulationEndDt = simulationEndDt;
  }

  public int getTruckCount() {
    return truckCount;
  }

  public double getTruckSpeed() {
    return truckSpeed;
  }

  public double getIntervalBetweenRequests() {
    return intervalBetweenRequests;
  }

  public double getMaxDeliveryTimeHrs() {
    return maxDeliveryTimeHrs;
  }

  public double[] getRouteLengthDistribution() {
    return routeLengthDistribution;
  }

  public LocalDateTime getSimulationStartDt() {
    return simulationStartDt;
  }

  public LocalDateTime getSimulationEndDt() {
    return simulationEndDt;
  }
}