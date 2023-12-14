import java.util.List;

public class Application {
    public static void main(String[] args) throws InterruptedException {
//        int cores = Runtime.getRuntime().availableProcessors();
//
//        System.out.println(cores);
        PerformanceMeasurement perfMeas = new PerformanceMeasurement();
        List<PerformanceResult> performanceResultList = perfMeas.startMeasurementSeries();

        OutputHandler outputHandler = new OutputHandler();
        outputHandler.printMeasurementSeries(performanceResultList, "Comparison of needed time for different Thread types");
        outputHandler.printRelativeChange(performanceResultList, "Relative change for different Thread types");
    }
}
