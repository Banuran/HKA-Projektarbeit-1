import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws InterruptedException, IOException {
//        int cores = Runtime.getRuntime().availableProcessors();
//
//        System.out.println(cores);
        InteractionHandler interactionHandler = new InteractionHandler();
        PerformanceMeasurementConfig config = new PerformanceMeasurementConfig();

        config = interactionHandler.changeConfigRequest(config);

        PerformanceMeasurement perfMeas = new PerformanceMeasurement(interactionHandler, config);
        List<PerformanceResult> performanceResultList = perfMeas.startMeasurementSeries();

        OutputHandler outputHandler = new OutputHandler();
        outputHandler.printMeasurementSeries(performanceResultList, "Comparison of needed time for different Thread types");
        outputHandler.printRelativeChange(performanceResultList, "Relative change for different Thread types");

        System.out.format("%n");
        interactionHandler.saveResultsToFileRequest(performanceResultList);
    }
}
