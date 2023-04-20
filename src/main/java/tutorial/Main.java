package tutorial;

import java.time.LocalDateTime;
import java.util.List;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.utils.format.Formats;

import tutorial.scenario.RouteLengthContainer;
import tutorial.scenario.Scenario;
import tutorial.scenario.Store;
import tutorial.scenario.Warehouse;
import tutorial.model.Statistics;

public class Main {

	private static final double TRUCK_SPEED = 40;
	private static final double INTERVAL_BETWEEN_REQUESTS_HRS = 0.5;
	private static final double MAX_DELIVERY_TIME_HRS = 6;
	
	private static List<Warehouse> warehouses;
	private static List<Store> stores;
	private static RouteLengthContainer routeLengthContainer;
	
	static {
		Warehouse wh1 = new Warehouse("wh1", "Warehouse 1");
		Warehouse wh2 = new Warehouse("wh2", "Warehouse 2");
		Warehouse wh3 = new Warehouse("wh3", "Warehouse 3");
		warehouses = List.of(wh1, wh2, wh3);
		Store st1 = new Store("st1", "Store 1");
		Store st2 = new Store("st2", "Store 2");
		Store st3 = new Store("st3", "Store 3");
		stores = List.of(st1, st2, st3);
		routeLengthContainer = new RouteLengthContainer();
		routeLengthContainer.add(wh1, st1, 10);
		routeLengthContainer.add(wh1, st2, 20);
		routeLengthContainer.add(wh1, st3, 30);
		routeLengthContainer.add(wh2, st1, 40);
		routeLengthContainer.add(wh2, st2, 50);
		routeLengthContainer.add(wh2, st3, 60);
		routeLengthContainer.add(wh3, st1, 70);
		routeLengthContainer.add(wh3, st2, 80);
		routeLengthContainer.add(wh3, st3, 90);
	}
	
	public static void main(String[] args) {
		runScenarioAnalysis();
	}
	
	private static void runScenarioAnalysis() {
		for (int numberOfTrucks = 1; numberOfTrucks <= 15; numberOfTrucks++) {
			Scenario scenario = new Scenario(	numberOfTrucks, 
												TRUCK_SPEED,
												INTERVAL_BETWEEN_REQUESTS_HRS,
												MAX_DELIVERY_TIME_HRS,
												warehouses,
												stores,
												routeLengthContainer,
												LocalDateTime.of(2023, 1, 1, 0, 0), 
												LocalDateTime.of(2023, 2, 1, 0, 0));
			runExperimentWithStats(scenario);
		}
	}

	private static void createAndRunOneExperiment() {
		Scenario scenario = new Scenario(	1,
											TRUCK_SPEED,
											INTERVAL_BETWEEN_REQUESTS_HRS,
											MAX_DELIVERY_TIME_HRS,
											warehouses,
											stores,
											routeLengthContainer,
											LocalDateTime.of(2023, 1, 1, 0, 0), 
											LocalDateTime.of(2023, 1, 1, 12, 0));
		runExperiment(scenario);
	}

	private static void createAndRunOneExperimentWithStats() {
		Scenario scenario = new Scenario(	1,
											TRUCK_SPEED,
											INTERVAL_BETWEEN_REQUESTS_HRS,
											MAX_DELIVERY_TIME_HRS,
											warehouses,
											stores,
											routeLengthContainer,
											LocalDateTime.of(2023, 1, 1, 0, 0), 
											LocalDateTime.of(2023, 1, 1, 12, 0));
		runExperimentWithStats(scenario);
	}

	private static void runExperiment(Scenario scenario) {
		ExperimentRun experiment = new ExperimentRun(scenario, new Engine());
		experiment.run();
	}

	private static void runExperimentWithStats(Scenario scenario) {
		ExperimentRun experiment = new ExperimentRun(scenario, new Engine());
		experiment.run();
		Statistics statistics = experiment.getStatistics();
		System.out.println("Trucks count:\t" + scenario.getTruckCount() 
			+ "\tSL:\t" + Formats.getDefaultFormats().percentTwoDecimals(statistics.getServiceLevel())
			+ "\tExpenses:\t" + Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpenses())
			+ "\tExpenses/SL:\t" + Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpensesPerServiceLevelPercent()));
	}

}
