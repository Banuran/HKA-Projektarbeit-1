import java.util.List;

public class Application {
    public static void main(String[] args) throws InterruptedException {

        PerformanceMeasurment perfMeas = new PerformanceMeasurment();
        List<PerformanceResult> performanceResultList = perfMeas.startMeasurementSeries();

        OutputHandler outputHandler = new OutputHandler();
        outputHandler.printMeasurmentSeries(performanceResultList, "Heading");
    }
}
