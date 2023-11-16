public class PerformanceResult {
    ThreadType threadType;
    int numThreads;
    long avgExecutionTime;

    public PerformanceResult(ThreadType threadType, int numThreads, long avgExecutionTime) {
        this.threadType = threadType;
        this.numThreads = numThreads;
        this.avgExecutionTime = avgExecutionTime;
    }
}
