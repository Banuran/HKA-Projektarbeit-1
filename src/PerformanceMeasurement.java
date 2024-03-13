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

    private final InteractionHandler interactionHandler;
    private final PerformanceMeasurementConfig config;
    private final int cores = Runtime.getRuntime().availableProcessors();

    public PerformanceMeasurement(InteractionHandler interactionHandler, PerformanceMeasurementConfig config) {
        this.interactionHandler = interactionHandler;
        this.config = config;
    }

    public List<PerformanceResult> startMeasurementSeries() throws InterruptedException {

        this.warmup();

        int numRepeats = config.getNumRepeats();
        int[] numsThreads = config.getNumsThreads();
        List<PerformanceResult> performanceResultList = new ArrayList<>();

        // calculate total runs for output
        int totalRuns = numRepeats * numsThreads.length * ThreadType.values().length;
        interactionHandler.initRemainingRuns(totalRuns);

        for (int numThreads : numsThreads) {
            for (ThreadType threadType : ThreadType.values()) {
                long totalTime = 0;
                // repeat measurement multiple times
                for (int i = 0; i < numRepeats; i++) {
                    totalTime += startThreads(numThreads, threadType);
                    interactionHandler.updateRemainingRuns();
                }
                // calculate the time for all repitions
                long avgTime = totalTime / numRepeats;
                // add result to list
                PerformanceResult res = new PerformanceResult(threadType, numThreads, avgTime);
                performanceResultList.add(res);
            }
        }

        return performanceResultList;
    }

    /**
     * Wrapper, different methods are used for different Thread types
     * @param numThreads number of threads
     * @param threadType type of threads to be used
     * @return the elapsed time
     * @throws InterruptedException
     */
    private long startThreads(int numThreads, ThreadType threadType) throws InterruptedException {
        if (threadType == ThreadType.POOLED) {
            return startPooledThreads(numThreads);
        } else {
            return startSimpleThreads(numThreads, threadType);
        }
    }

    /**
     * Start threads of type virtual or platform
     * @param numThreads number of threads
     * @param threadType type of threads to be used
     * @return the elapsed time
     * @throws InterruptedException
     */
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

        // wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        long end = System.nanoTime();

        return end-start;
    }

    /**
     * start Threadpools
     * @param numThreads number of threads
     * @return the elapsed time
     * @throws InterruptedException
     */
    private long startPooledThreads(int numThreads) throws InterruptedException {

        long start = System.nanoTime();
        ExecutorService executorService = Executors.newFixedThreadPool(cores);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(new Factorization());
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            throw e;
        }
        long end = System.nanoTime();

        return end-start;
    }

    /**
     * Run some threads to warm up the Java Virtual Machine
     * @throws InterruptedException
     */
    private void warmup() throws InterruptedException {
        int numWarmupThreads = 1000;
        Thread[] threads = new Thread[numWarmupThreads];

        for (int i = 0; i < numWarmupThreads; i++) {
            Thread thread = Thread.ofVirtual().start(new Factorization());
            threads[i] = thread;
        }

        // wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
