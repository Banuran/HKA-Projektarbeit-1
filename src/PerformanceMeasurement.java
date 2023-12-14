import Runnables.Factorization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

enum ThreadType {
    PLATFORM,
    VIRTUAL,
    POOLED
}

public class PerformanceMeasurement {

    public List<PerformanceResult> startMeasurementSeries() throws InterruptedException {
        int numRepeats = 20;
        int[] numsThreads = {10, 100, 1000, 10000, 100000};
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
        if (threadType == ThreadType.POOLED) {
            return startPooledThreads(numThreads);
        } else {
            return startSimpleThreads(numThreads, threadType);
        }
    }

    private long startSimpleThreads(int numThreads, ThreadType threadType) throws InterruptedException {
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

    private long startPooledThreads(int numThreads) throws InterruptedException {

        long start = System.nanoTime();
        ExecutorService executorService = Executors.newFixedThreadPool(12);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(new Factorization());
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            System.out.println("SHUTDOWN NOW!");
        }
        long end = System.nanoTime();

        return end-start;
    }
}
