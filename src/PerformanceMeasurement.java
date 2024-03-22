import Runnables.BlockingSemaphore;
import Runnables.Factorization;
import Runnables.FileOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
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

    private final int numSemaphorePermits = 2;
    private Semaphore semaphore = new Semaphore(numSemaphorePermits);

    // do not run platform threads to speed up runtime if true
    private final boolean fakePlatform = false;

    public PerformanceMeasurement(InteractionHandler interactionHandler, PerformanceMeasurementConfig config) {
        this.interactionHandler = interactionHandler;
        this.config = config;
    }

    public List<PerformanceResult> startMeasurementSeries() throws InterruptedException {

        this.warmup();

        if (config.getRunnableType() == RunnableType.FILEOPERATIONS) FileOperations.createSubdirectory();

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

        if (config.getRunnableType() == RunnableType.FILEOPERATIONS) FileOperations.deleteSubdirectory();

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

        // Do not run platform threads if flag is active to improve runtime
        if (threadType == ThreadType.PLATFORM && fakePlatform) return 0;

        long start = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            Thread thread;
            if (threadType == ThreadType.VIRTUAL)
                thread = Thread.ofVirtual().start(this.getRunnable(config.getRunnableType()));
            else
                thread = Thread.ofPlatform().start(this.getRunnable(config.getRunnableType()));
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
            executorService.submit(this.getRunnable(config.getRunnableType()));
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

    private Runnable getRunnable(RunnableType type) {

        return switch(type) {
            case FACTORIZATION -> new Factorization();
            case BLOCKINGSEMAPHORE -> new BlockingSemaphore(this.semaphore);
            case FILEOPERATIONS -> new FileOperations();
            case null, default -> new Factorization();
        };
    }
}
