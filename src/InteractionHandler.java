import java.util.Scanner;

public class InteractionHandler {

    private String remainingRunsText = "Remaining Runs:";
    private int remainingRuns;

    public PerformanceMeasurementConfig changeConfigRequest(PerformanceMeasurementConfig config) {
        System.out.format("Default config:%n%s%n%n", config.toString());

        Scanner scanner = new Scanner(System.in);
        String userInput;
        do {
            System.out.format("Do you want to change the config? (Y)es / (N)o%n");
            userInput = scanner.nextLine();
            if (!userInput.isEmpty()) userInput = userInput.substring(0, 1);

        } while (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n"));

        if (userInput.equalsIgnoreCase("y")) {
            config = this.changeConfigNumsThreadsRequest(config, scanner);
            config = this.changeNumRepeatsRequest(config, scanner);

            System.out.format("%nChanged Config:%n%s%n", config.toString());
        }

        System.out.format("%n");
        scanner.close();

        return config;
    }

    public PerformanceMeasurementConfig changeConfigNumsThreadsRequest(PerformanceMeasurementConfig config, Scanner scanner) {
        while (true) {
            System.out.println("Change Number of threads");
            System.out.println("Enter a comma-separated list of integers or (D)efault:");

            String userInput = scanner.nextLine();
            String firstChar = !userInput.isEmpty() ? userInput.substring(0, 1) : "";

            if (firstChar.equalsIgnoreCase("d")) {
                break;
            } else {
                String[] inputValues = userInput.split(",");

                if (inputValues.length >= 1) {
                    int[] numsThreads = new int[inputValues.length];

                    try {
                        for (int i = 0; i < inputValues.length; i++) {
                            numsThreads[i] = Integer.parseInt(inputValues[i].trim());
                        }

                        config.setNumsThreads(numsThreads);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input");
                    }
                } else {
                    System.out.println("Invalid input");
                }
            }
        }

        return config;
    }

    public PerformanceMeasurementConfig changeNumRepeatsRequest(PerformanceMeasurementConfig config, Scanner scanner) {
        while (true) {
            System.out.println("Change Number of repeats");
            System.out.println("Enter an integer or (D)efault:");

            String userInput = scanner.nextLine();
            String firstChar = !userInput.isEmpty() ? userInput.substring(0, 1) : "";

            if (firstChar.equalsIgnoreCase("d")) {
                break;
            } else {
                try {
                    int intValue = Integer.parseInt(userInput.trim());
                    config.setNumRepeats(intValue);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                }
            }
        }

        return config;
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
