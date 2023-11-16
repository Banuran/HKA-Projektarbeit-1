import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread( new DateCommand() );
        t1.start();
        Thread t2 = new Thread( new CounterCommand() );
        t2.start();

        new DateThread().start();

        new DaemonThread().start();
    }
}

class DateThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(new java.util.Date());
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class DateCommand implements Runnable {
    @Override
    public void run() {
        System.out.println( Thread.currentThread().getState() );
        for (int i = 0; i < 20; i++) {
            System.out.println(new java.util.Date());
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class CounterCommand implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(i);
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class DaemonThread extends Thread {
    DaemonThread() {
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Lauf, Thread, lauf");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new DaemonThread().start();
    }
}

class JoinTheThread {
    static class JoinerThread extends Thread {
        public int result;

        @Override
        public void run() {
            result = 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JoinerThread t = new JoinerThread();
        t.start();
 t.join();
        System.out.println(t.result);
    }
}