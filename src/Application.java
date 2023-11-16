import java.time.Duration;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 100000;
        Thread[] threads = new Thread[numThreads];

        long start = System.nanoTime();


        for (int i = 0; i < numThreads; i++) {
            Thread thread = Thread.ofPlatform().start(new Factorization());
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long end = System.nanoTime();
        System.out.println("\n\nPlatform Threads:");
        String formattedElapsedTime = getReadableElapsedTime(start, end);
        System.out.println(formattedElapsedTime);

        start = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            Thread thread = Thread.ofVirtual().start(new Factorization());
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.join();
        }

        end = System.nanoTime();
        System.out.println("\n\nVirtual Threads:");
        formattedElapsedTime = getReadableElapsedTime(start, end);
        System.out.println(formattedElapsedTime);
    }

    public static String getReadableElapsedTime(long start, long end) {
        Duration duration = Duration.ofNanos(end - start);
        return String.format("%02d m %02d s %d ms %d ns",
                duration.toMinutesPart(), duration.toSecondsPart(),
                duration.toMillisPart(), duration.toNanosPart() % 1000000L);
    }
}

class Factorization implements Runnable {
    @Override
    public void run() {
        // https://www.katharinengymnasium.de/wolf/web/javaIDE/primfaktorenJavaIDE.html
        int zahl = 87654321; // integer bis 2 147 483 647
        int teiler = 2;
        int teilerMax = (int) Math.sqrt(zahl);
        while (teiler <= teilerMax) {
            if(zahl % teiler == 0) {
                zahl /= teiler;
                teilerMax = (int) Math.sqrt(zahl);
                //System.out.print("Z\b");
            }
            else {
                teiler++;
            }
        }
    }
}
