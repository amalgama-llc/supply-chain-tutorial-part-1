package tutorial.scenario;

import java.util.HashMap;
import java.util.Map;

public class RouteLengthContainer {
	private Map<Warehouse, Map<Store, Double>> routeLengths = new HashMap<>();
	
	public void add(Warehouse warehouse, Store store, double routeLength) {
		routeLengths.computeIfAbsent(warehouse, wh -> new HashMap<>()).put(store, routeLength);
	}
	
	public double getRouteLength(Asset asset1, Asset asset2) {
		Warehouse warehouse;
		if (asset1 instanceof Warehouse wh) {
			warehouse = wh;
		} else if (asset2 instanceof Warehouse wh) {
			warehouse = wh;
		} else {
			throw new IllegalArgumentException("Either asset1 or asset2 must be a Warehouse");
		}
		Store store;
		if (asset1 instanceof Store s) {
			store = s;
		} else if (asset2 instanceof Store s) {
			store = s;
		} else {
			throw new IllegalArgumentException("Either asset1 or asset2 must be a Store");
		}
		double length = routeLengths.getOrDefault(warehouse, Map.of()).getOrDefault(store, Double.NaN);
		if (Double.isNaN(length)) {
			throw new IllegalStateException("No route length information found for the supplied assets");
		}
		
		return length;
	}
}
