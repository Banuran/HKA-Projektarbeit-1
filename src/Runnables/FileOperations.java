package Runnables;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperations implements Runnable {

    private static final String subdirectoryName = "file_operations";
    private final Runnable runCalc = new Factorization();

    @Override
    public void run() {

        runCalc.run();

        File subdirectory = new File(subdirectoryName);
        if (!subdirectory.exists() || !subdirectory.isDirectory()) {
            throw new RuntimeException("Subdirectory '" + subdirectoryName + "' does not exist.");
        }

        String filePath = Paths.get(subdirectoryName, "meas_" + Thread.currentThread().threadId() + ".csv").toString();

        // Create a file and write some characters
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String randomString = generateRandomString(65536);
            writer.write(randomString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Delete the file
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder(length);
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            builder.append(characters.charAt(index));
        }
        return builder.toString();
    }

    public static void createSubdirectory() {
        File directory = new File(subdirectoryName);

        if (directory.exists() && directory.isDirectory()) {
            return;
        }

        // create the directory
        boolean success = directory.mkdir();
        if (!success) {
            throw new RuntimeException("Failed creating directory '" + subdirectoryName + "'.");
        }
    }

    public static void deleteSubdirectory() {
        File directory = new File(subdirectoryName);

        // Check if the directory exists and is empty
        if (!directory.exists() || !directory.isDirectory()) {
            throw new RuntimeException("Directory '" + subdirectoryName + "' does not exist or is not a directory.");
        }

        if (directory.list().length > 0) {
            throw new RuntimeException("Directory '" + subdirectoryName + "' is not empty.");
        }

        // Attempt to delete the directory
        if (directory.delete()) {
            return;
        } else {
            throw new RuntimeException("Failed to delete directory '" + subdirectoryName + "'.");
        }
    }

}
