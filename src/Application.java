import Runnables.Factorization;

import java.time.Duration;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 100000;

        measureTime();
//        long elapsedTime = startThreads(numThreads, false);
//        System.out.println("\n\nPlatform Threads:");
//        String formattedElapsedTime = getReadableElapsedTime(elapsedTime);
//        System.out.println(formattedElapsedTime);
//
//        elapsedTime = startThreads(numThreads, true);
//        System.out.println("\n\nVirtual Threads:");
//        formattedElapsedTime = getReadableElapsedTime(elapsedTime);
//        System.out.println(formattedElapsedTime);
    }

    private static void measureTime() throws InterruptedException {
        int numOfRepeats = 20;
        int[] numsThreads = {6, 50, 100, 1000};

        for (int numThreads : numsThreads) {
            long totalTime = 0;
            for (int i = 0; i < numOfRepeats; i++) {
                totalTime += startThreads(numThreads, false);
            }
            long avgTime = totalTime / numOfRepeats;
            String formattedAvgTime = getReadableElapsedTime(avgTime);
            System.out.println(numThreads + " platform avg: " + formattedAvgTime);

             totalTime = 0;
            for (int i = 0; i < numOfRepeats; i++) {
                totalTime += startThreads(numThreads, true);
            }
            avgTime = totalTime / numOfRepeats;
            formattedAvgTime = getReadableElapsedTime(avgTime);
            System.out.println(numThreads + " virtual avg: " + formattedAvgTime);
        }
    }

    private static long startThreads(int numThreads, boolean virtual) throws InterruptedException {
        Thread[] threads = new Thread[numThreads];

        long start = System.nanoTime();


        for (int i = 0; i < numThreads; i++) {
            Thread thread;
            if (virtual)
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

    private static String getReadableElapsedTime(long elapsedTime) {
        Duration duration = Duration.ofNanos(elapsedTime);
        return String.format("%02d m %02d s %d ms %d ns",
                duration.toMinutesPart(), duration.toSecondsPart(),
                duration.toMillisPart(), duration.toNanosPart() % 1000000L);
    }
}
