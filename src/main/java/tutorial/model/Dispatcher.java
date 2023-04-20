package tutorial.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleBiFunction;

import com.amalgamasimulation.engine.Engine;

import tutorial.scenario.Asset;

public class Dispatcher {
	private final Engine engine;
	private final Model model;
	private final ToDoubleBiFunction<Asset, Asset> routeLengthGetter;
	private int lastTaskId = 0;
	private List<TransportationTask> transportationTasks = new ArrayList<>();

	public Dispatcher(Engine engine, Model model, ToDoubleBiFunction<Asset, Asset> routeLengthGetter) {
		this.engine = engine;
		this.model = model;
		this.routeLengthGetter = routeLengthGetter;
	}

	public List<TransportationTask> getTransportationTasks() {
		return transportationTasks;
	}

	public void onNewRequest(TransportationRequest newRequest) {
		Optional<Truck> freeTruck = model.getTrucks().stream().filter(Truck::isIdle).findFirst();
		if (freeTruck.isPresent()) {
			startTransportation(freeTruck.get(), newRequest);
		} else {
			model.addWaitingRequest(newRequest);
		}
	}

	private void startTransportation(Truck truck, TransportationRequest request) {
		TransportationTask task = new TransportationTask(String.valueOf(++lastTaskId), truck, request, routeLengthGetter, this::onTruckRelease, engine);
		transportationTasks.add(task);
		task.execute();
	}

	private void onTruckRelease(Truck truck) {
		TransportationRequest waitingRequest = model.getNextWaitingRequest();
		if (waitingRequest != null) {
			startTransportation(truck, waitingRequest);
		}
	}
}
