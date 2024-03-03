import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportHandler {

    private final static String defaultFilepathWithoutEnding = "ThreadComparisonResult";
    private final static String defaultFilepathEnding = ".csv";
    private final static String defaultFilepath = defaultFilepathWithoutEnding + defaultFilepathEnding;

    public static void exportToCSV(List<PerformanceResult> performanceResultList, String filePath) throws IOException {
        if (performanceResultList == null || performanceResultList.isEmpty()) {
            throw new RuntimeException("Nothing to export. The list is empty.");
        }

        if (filePath == null || filePath.trim().isEmpty()) {
            // set default fileName if none is provided
            filePath = defaultFilepathWithoutEnding;
            filePath += "_";
            filePath += new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            filePath += defaultFilepathEnding;
        } else if (filePath.endsWith(File.separator) || filePath.endsWith("/") || filePath.endsWith("\\")) {
            // add filename if only path is provided
            filePath += defaultFilepathWithoutEnding;
            filePath += "_";
            filePath += new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            filePath += defaultFilepathEnding;
        } else if (!filePath.endsWith(defaultFilepathEnding)) {
            // add file ending if none is provided
            filePath += defaultFilepathEnding;
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
