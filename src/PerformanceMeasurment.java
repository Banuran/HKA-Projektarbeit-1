import Runnables.Factorization;

import java.util.ArrayList;
import java.util.List;

enum ThreadType {
    PLATFORM,
    VIRTUAL
}

public class PerformanceMeasurment {

    public List<PerformanceResult> startMeasurementSeries() throws InterruptedException {
        int numRepeats = 20;
        int[] numsThreads = {6, 50, 100, 1000};
        List<PerformanceResult> performanceResultList = new ArrayList<>();

        for (int numThreads : numsThreads) {
            for (ThreadType threadType : ThreadType.values()) {
                long totalTime = 0;
                for (int i = 0; i < numRepeats; i++) {
                    totalTime += startThreads(numThreads, threadType);
                }
                long avgTime = totalTime / numRepeats;
                PerformanceResult res = new PerformanceResult(threadType, numThreads, avgTime);
                performanceResultList.add(res);
            }
        }

        return performanceResultList;
    }

    private long startThreads(int numThreads, ThreadType threadType) throws InterruptedException {
        Thread[] threads = new Thread[numThreads];

        long start = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            Thread thread;
            if (threadType == ThreadType.VIRTUAL)
                thread = Thread.ofVirtual().start(new Factorization());
            else
                thread = Thread.ofPlatform().start(new Factorization());
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long end = System.nanoTime();

        return end-start;
    }
}
