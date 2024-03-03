import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Contains methods where user input is processed
 */
public class InteractionHandler {

    private final static Scanner scanner = new Scanner(System.in);
    private final String remainingRunsText = "Remaining Runs:";
    private int remainingRuns;

    /**
     * Ask if the config should be saved and initialise follow up questions
     * @param config the current config
     * @return the changed config or current config if nothing is changed
     */
    public PerformanceMeasurementConfig changeConfigRequest(PerformanceMeasurementConfig config) {
        System.out.format("Default config:%n%s%n%n", config.toString());

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

        return config;
    }

    /**
     * Validate input for the number of threads
     * @param config the current config
     * @param scanner input scanner
     * @return the changed config or current config if nothing is changed
     */
    public PerformanceMeasurementConfig changeConfigNumsThreadsRequest(PerformanceMeasurementConfig config, Scanner scanner) {
        while (true) {
            System.out.println("Change Number of threads");
            System.out.println("Enter a comma-separated list of integers or press enter to use the default:");

            String userInput = scanner.nextLine();
            String firstChar = !userInput.isEmpty() ? userInput.substring(0, 1) : "";

            if (firstChar.isEmpty()) {
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

    /**
     * Validate input for number of repeats
     * @param config the current config
     * @param scanner input scanner
     * @return the changed config or current config if nothing is changed
     */
    public PerformanceMeasurementConfig changeNumRepeatsRequest(PerformanceMeasurementConfig config, Scanner scanner) {
        while (true) {
            System.out.println("Change Number of repeats");
            System.out.println("Enter an integer or press enter to use the default:");

            String userInput = scanner.nextLine();
            String firstChar = !userInput.isEmpty() ? userInput.substring(0, 1) : "";

            if (firstChar.isEmpty()) {
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

    /**
     * Ask if the results should be saved and handle all file related requests
     * @param performanceResultList results to be saved
     * @throws IOException
     */
    public void saveResultsToFileRequest(List<PerformanceResult> performanceResultList) throws IOException {

        String userInput;
        do {
            System.out.println("Do you want to save the results to a file? (Y)es / (N)o");
            userInput = scanner.nextLine();
            if (!userInput.isEmpty()) userInput = userInput.substring(0, 1);
        } while (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n"));

        if (userInput.equalsIgnoreCase("y")) {
            // Display default and ask for a filepath
            System.out.format("Default filepath: ./%s%n", ExportHandler.getDefaultFilepath());

            System.out.println("Enter a filepath or press Enter to use the default:");
            String filepath = scanner.nextLine().trim();

            if (filepath.isEmpty()) {
                filepath = null;
            }

            ExportHandler.exportToCSV(performanceResultList, filepath);
        }
    }

    /**
     * Print line for remaining runs for the first time
     * @param totalNumberOfRuns initial number of runs
     */
    public void initRemainingRuns(int totalNumberOfRuns) {
        remainingRuns = totalNumberOfRuns;
        System.out.format("%s %d", remainingRunsText, remainingRuns);
    }

    /**
     * Override the remaining runs line with an updatet version
     */
    public void updateRemainingRuns() {
        this.updateRemainingRuns(1);
    }
    public void updateRemainingRuns(int numSubtract) {
        remainingRuns -= numSubtract;
        System.out.format("\r%s %d          ", remainingRunsText, remainingRuns);

        if(remainingRuns == 0) System.out.format("%n%n");
    }
}
