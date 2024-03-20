package Runnables;

import java.util.concurrent.Semaphore;

public class BlockingSemaphore implements Runnable {
    private final Semaphore semaphore;
    private final Runnable runCalc;

    public BlockingSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore; // 19111680
        this.runCalc = new Factorization(); // 894682
        //this.runCalc2 = new Factorization(); // 315226
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
