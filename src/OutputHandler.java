import java.time.Duration;
import java.util.List;

public class OutputHandler {

    public void printMeasurementSeries(List<PerformanceResult> series, String heading) {
        System.out.format("+---------+----------------------------+----------------------------+----------------------------+%n");
        System.out.format("| %-94s |%n", heading);
        System.out.format("+---------+----------------------------+----------------------------+----------------------------+%n");
        System.out.format("| Threads | avg time Platform Thread   | avg time Virtual Thread    | avg time Pooled Thread     |%n");
        System.out.format("+---------+----------------------------+----------------------------+----------------------------+%n");

        for (int i = 0; i < series.size(); i += 3) {
            PerformanceResult result1 = series.get(i);
            PerformanceResult result2 = series.get(i+1);
            PerformanceResult result3 = series.get(i+2);
            if (result1.numThreads != result2.numThreads || result1.numThreads != result3.numThreads ||
                    result1.threadType != ThreadType.PLATFORM ||
                    result2.threadType != ThreadType.VIRTUAL ||
                    result3.threadType != ThreadType.POOLED) throw new RuntimeException("List not sorted");

            System.out.format("| %7d | %26s | %26s | %26s |%n", result1.numThreads, this.getTimeReadable(result1.avgExecutionTime), this.getTimeReadable(result2.avgExecutionTime), this.getTimeReadable(result3.avgExecutionTime));
        }

        System.out.format("+---------+----------------------------+----------------------------+----------------------------+%n");
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

    public void printRelativeChange(List<PerformanceResult> series, String heading) {
        System.out.format("+---------+----------------------------+----------------------------+----------------------------+%n");
        System.out.format("| %-94s |%n", heading);
        System.out.format("+---------+----------------------------+----------------------------+----------------------------+%n");
        System.out.format("| Threads | avg time Platform Thread   | avg time Virtual Thread    | avg time Pooled Thread     |%n");
        System.out.format("+---------+----------------------------+----------------------------+----------------------------+%n");

        System.out.format("| %7d | %26s | %26s | %26s |%n", 1, 1, 1, 1);

        int tN = ThreadType.values().length;

        for (int i = tN; i < series.size(); i += tN) {
            double[] change = new double[tN];

            for (int j = 0; j < tN; j += 1) {
                long current = series.get(j+i).avgExecutionTime;
                long prev = series.get(j+i-tN).avgExecutionTime;
                change[j] = (double) series.get(j + i).avgExecutionTime / series.get(j+i-tN).avgExecutionTime;
            }
            int current = series.get(i).numThreads;
            int prev = series.get(i-tN).numThreads;
            double numChange = (double) series.get(i).numThreads / series.get(i-tN).numThreads;

            System.out.format("| %7.2f | %26.2f | %26.2f | %26.2f |%n", numChange, change[0], change[1], change[2]);
        }

        System.out.format("+---------+----------------------------+----------------------------+----------------------------+%n");
    }
}
