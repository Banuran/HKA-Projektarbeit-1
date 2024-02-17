import java.util.Scanner;

public class InteractionHandler {

    private String remainingRunsText = "Remaining Runs:";
    private int remainingRuns;

    public void changeConfigRequest(PerformanceMeasurementConfig config) {
        System.out.format("Default config:%n%s%n%n", config.toString());

        Scanner scanner = new Scanner(System.in);
        String userInput;
        do {
            System.out.format("Do you want to change the config? (Y)es / (N)o%n");
            userInput = scanner.nextLine();
            if (!userInput.isEmpty()) userInput = userInput.substring(0, 1);
        } while (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n"));
        System.out.format("Break%n");
    }

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

        if(remainingRuns == 0) System.out.format("%n%n");
    }
}
