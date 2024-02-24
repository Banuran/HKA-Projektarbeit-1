import java.util.Arrays;

public class PerformanceMeasurementConfig {

    private int numRepeats = 10;
    private int[] numsThreads = {100,200,400,800,1600,3200,6400,12800,25600,51200,102400,204800,409600};

    public int getNumRepeats() {
        return numRepeats;
    }

    public int[] getNumsThreads() {
        return numsThreads;
    }

    public void setNumRepeats(int numRepeats) {
        this.numRepeats = numRepeats;
    }

    public void setNumsThreads(int[] numsThreads) {
        this.numsThreads = numsThreads;
    }

    public String toString() {
        return String.format("Number of Threads to test: %s%nNumber of repeats per test: %d",
                Arrays.toString(numsThreads), numRepeats);
    }
}
