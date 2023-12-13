import java.time.Duration;
import java.util.List;

public class OutputHandler {

    public void printMeasurmentSeries(List<PerformanceResult> series, String heading) {
        System.out.format("+---------+----------------------------+----------------------------+%n");
        System.out.format("| %-65s |%n", heading);
        System.out.format("+---------+----------------------------+----------------------------+%n");
        System.out.format("| Threads | avg time Platform Thread   | avg time Virtual Thread    |%n");
        System.out.format("+---------+----------------------------+----------------------------+%n");

        for (int i = 0; i < series.size(); i += 2) {
            PerformanceResult result1 = series.get(i);
            PerformanceResult result2 = series.get(i+1);
            if (result1.numThreads != result2.numThreads ||
                    result1.threadType != ThreadType.PLATFORM ||
                    result2.threadType != ThreadType.VIRTUAL) throw new RuntimeException("List not sorted");

            System.out.format("| %7d | %26s | %26s |%n", result1.numThreads, this.getTimeReadable(result1.avgExecutionTime), this.getTimeReadable(result2.avgExecutionTime));
        }

        System.out.format("+---------+----------------------------+----------------------------+%n");
    }

    private String getTimeReadable(long time) {
        Duration duration = Duration.ofNanos(time);

        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        long millis = duration.toMillisPart();
        long nanos = duration.toNanosPart() % 1000000L;

        StringBuilder formattedTime = new StringBuilder();

        if (minutes > 0) formattedTime.append(String.format("%02d m ", minutes));
        if (seconds > 0 || !formattedTime.isEmpty()) formattedTime.append(String.format("%02d s ", seconds));
        if (millis > 0 || !formattedTime.isEmpty()) formattedTime.append(String.format("%d ms ", millis));
        if (nanos > 0 || !formattedTime.isEmpty()) formattedTime.append(String.format("%d ns", nanos));

        return formattedTime.toString().trim();
    }
}