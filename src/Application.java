import java.util.List;

public class Application {
    public static void main(String[] args) throws InterruptedException {

        PerformanceMeasurement perfMeas = new PerformanceMeasurement();
        List<PerformanceResult> performanceResultList = perfMeas.startMeasurementSeries();

        OutputHandler outputHandler = new OutputHandler();
        outputHandler.printMeasurmentSeries(performanceResultList, "Heading");
    }
}
