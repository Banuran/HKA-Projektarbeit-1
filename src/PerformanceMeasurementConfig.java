import java.util.Arrays;

public class PerformanceMeasurementConfig {

    private int numRepeats = 10;
    private int[] numsThreads = {100,200,400,800,1600,3200,6400,12800,25600,51200,102400,204800,409600};
    private RunnableType runnableType = RunnableType.FACTORIZATION;

    public int getNumRepeats() {
        return numRepeats;
    }

    public int[] getNumsThreads() {
        return numsThreads;
    }

    public RunnableType getRunnableType() {
        return runnableType;
    }

    public void setNumRepeats(int numRepeats) {
        this.numRepeats = numRepeats;
    }

    public void setNumsThreads(int[] numsThreads) {
        this.numsThreads = numsThreads;
    }

    public void setRunnableType(RunnableType runnableType) {
        this.runnableType = runnableType;
    }

    public String toString() {
        return String.format("Number of Threads to test: %s%nNumber of repeats per test: %d%nRunnable to run: %s",
                Arrays.toString(numsThreads), numRepeats, runnableType.toString());
    }
}
