package tutorial;

import java.time.LocalDateTime;

import com.amalgamasimulation.engine.Engine;

import tutorial.scenario.Scenario;
import tutorial.model.Statistics;
import com.amalgamasimulation.utils.format.Formats;

public class Main {
	public static void main(String[] args) {
        for (int numberOfTrucks = 1; numberOfTrucks <= 10; numberOfTrucks++) {
            Scenario scenario = new Scenario(numberOfTrucks, 35, 0.1, 1, new double[] {20.0, 30.0, 40.0},
                    LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 2, 0));
            runExperiment(scenario);
        }
    }
	
	private static void runExperiment(Scenario scenario) {
	    ExperimentRun experiment = new ExperimentRun(scenario, new Engine());
	    experiment.run();
	    Statistics statistics = experiment.getStatistics();
	    System.out.println("Trucks count:\t" + scenario.getTruckCount()
	        + "\tSL:\t" + Formats.getDefaultFormats().percentTwoDecimals(statistics.getServiceLevel())
	        + "\tExpenses:\t" + Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpenses())
	        + "\tExpenses/SL:\t" + Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpensesPerServiceLevelPercent()));
	}
}