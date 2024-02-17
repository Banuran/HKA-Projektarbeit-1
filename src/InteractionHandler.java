public class InteractionHandler {

    private String remainingRunsText = "Remaining Runs:";
    private int remainingRuns;

    public void initRemainingRuns(int totalNumberOfRuns) {
        remainingRuns = totalNumberOfRuns;
        System.out.format("%s %d", remainingRunsText, remainingRuns);
    }
    public void updateRemainingRuns() {
        this.updateRemainingRuns(1);
    }
    public void updateRemainingRuns(int numSubtract) {
        remainingRuns -= numSubtract;
        System.out.format("\r%s %d", remainingRunsText, remainingRuns);

        if(remainingRuns == 0) System.out.format("%n");
    }
}
