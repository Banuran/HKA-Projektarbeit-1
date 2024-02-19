import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportHandler {

    private final static String defaultFilepath = "ThreadComparisonResult.csv";

    public static void exportToCSV(List<PerformanceResult> performanceResultList, String filePath) throws IOException {
        if (performanceResultList == null || performanceResultList.isEmpty()) {
            throw new RuntimeException("Nothing to export. The list is empty.");
        }

        // set default fileName if none is provided
        if (filePath == null || filePath.trim().isEmpty()) {
            filePath = defaultFilepath;
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV header
            writer.append("ThreadType,NumThreads,AvgExecutionTime\n");

            // Write data to CSV
            for (PerformanceResult result : performanceResultList) {
                writer.append(String.format("%s,%d,%d\n", result.threadType, result.numThreads, result.avgExecutionTime));
            }

            System.out.println("CSV file created successfully: " + filePath);

        } catch (IOException e) {
            System.out.println("Writing to CSV file failed.");
            throw e;
        }
    }

    public static String getDefaultFilepath() {
        return defaultFilepath;
    }
}
