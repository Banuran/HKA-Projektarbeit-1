package Runnables;

import java.util.concurrent.Semaphore;

public class BlockingSemaphore implements Runnable {
    private final Semaphore semaphore;
    private final Runnable runCalc;

    public BlockingSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
        this.runCalc = new Factorization();
    }

    @Override
    public void run() {

        this.runCalc.run();

        try {
            semaphore.acquire();
            this.runCalc.run();
            semaphore.release();
        } catch (InterruptedException e) {
            System.out.println("Failed acquiring Semaphore.");
            throw new RuntimeException(e);
        }
    }
}
