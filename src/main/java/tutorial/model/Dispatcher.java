package tutorial.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;

public class Dispatcher {
	private final Model model;
	private int lastTaskId = 0;
	private List<TransportationTask> transportationTasks = new ArrayList<>();
	private Queue<TransportationTask> waitingTasks = new LinkedList<>();
	private List<Consumer<TransportationTask>> taskStateChangeHandlers = new ArrayList<>();

	public Dispatcher(Model model) {
		this.model = model;
	}

	public List<TransportationTask> getTransportationTasks() {
		return transportationTasks;
	}
	
	public void addTaskStateChangeHandler(Consumer<TransportationTask> handler) {
		taskStateChangeHandlers.add(handler);
	}

	public void onNewRequest(TransportationRequest newRequest) {
		TransportationTask task = new TransportationTask(String.valueOf(++lastTaskId), newRequest, this::onTaskCompleted, model);
		transportationTasks.add(task);
		Optional<Truck> freeTruck = model.getTrucks().stream().filter(Truck::isIdle).findFirst();
		if (freeTruck.isPresent()) {
			startTransportation(freeTruck.get(), task);
		} else {
			addWaitingTask(task);
		}
	}

	private void startTransportation(Truck truck, TransportationTask task) {
		task.execute(truck);
	}

	private void onTaskCompleted(TransportationTask task) {
		TransportationTask waitingTask = getNextWaitingTask();
		if (waitingTask != null) {
			startTransportation(task.getTruck(), waitingTask);
		}
		taskStateChangeHandlers.forEach(handler -> handler.accept(task));
	}
	
	private TransportationTask getNextWaitingTask() {
		return waitingTasks.poll();
	}
	
	private void addWaitingTask(TransportationTask task) {
		waitingTasks.add(task);
	}
}
