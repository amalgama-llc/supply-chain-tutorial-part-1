package tutorial.scenario;

import java.time.LocalDateTime;
import java.util.List;

public class Scenario {
	private final int truckCount;
	private final double truckSpeed;
	private final double intervalBetweenRequestsHrs;
	private final double maxDeliveryTimeHrs;
	private final RouteLengthContainer routeLengthContainer;
	private final List<Warehouse> warehouses;
	private final List<Store> stores;
	private final LocalDateTime simulationStartDt;
	private final LocalDateTime simulationEndDt;

	public Scenario(int truckCount, double truckSpeed, double intervalBetweenRequestsHrs, double maxDeliveryTimeHrs,
			List<Warehouse> warehouses, List<Store> stores, RouteLengthContainer routeLengthContainer,
			LocalDateTime simulationStartDt, LocalDateTime simulationEndDt) {
		this.truckCount = truckCount;
		this.truckSpeed = truckSpeed;
		this.intervalBetweenRequestsHrs = intervalBetweenRequestsHrs;
		this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
		this.routeLengthContainer = routeLengthContainer;
		this.warehouses = warehouses;
		this.stores = stores;
		this.simulationStartDt = simulationStartDt;
		this.simulationEndDt = simulationEndDt;
	}

	public int getTruckCount() {
		return truckCount;
	}

	public double getTruckSpeed() {
		return truckSpeed;
	}

	public double getIntervalBetweenRequestsHrs() {
		return intervalBetweenRequestsHrs;
	}

	public double getMaxDeliveryTimeHrs() {
		return maxDeliveryTimeHrs;
	}

	public double getRouteLength(Asset asset1, Asset asset2) {
		return routeLengthContainer.getRouteLength(asset1, asset2);
	}

	public LocalDateTime getSimulationStartDt() {
		return simulationStartDt;
	}

	public LocalDateTime getSimulationEndDt() {
		return simulationEndDt;
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}

	public List<Store> getStores() {
		return stores;
	}
}