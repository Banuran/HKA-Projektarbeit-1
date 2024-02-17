import java.util.Arrays;

public class PerformanceMeasurementConfig {

    private int numRepeats = 5;
    private int[] numsThreads = {10, 100};

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
