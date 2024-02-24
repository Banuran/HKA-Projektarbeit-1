package Runnables;

public class Factorization implements Runnable {
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
            }
            else {
                teiler++;
            }
        }
    }
}

