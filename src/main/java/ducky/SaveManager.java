package ducky;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all reading and writing to storage
 */
public class SaveManager {
    private Path directoryPath;
    private Path filePath;

    /**
     * Constructor for the SaveManager
     */
    public SaveManager() {
        String home = System.getProperty("user.home");
        directoryPath = Paths.get(home, "cs2103t", "ducky");
        filePath = directoryPath.resolve("data.txt");

        try {

            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
                System.out.println("Directory created: " + directoryPath);
            }

        } catch (IOException e) {

            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();

        }
    }

    /**
     * Deletes all tasks and saves it
     */
    public void clearAllTasks() {
        System.out.println("Warning: Clearing all tasks");
        saveAllTasks(new TaskList());
    }

    /**
     * Saves tasks to local storage based on the TaskList provided
     * Overrides the file in the local storage
     *
     * @param tasks the list of tasks to save
     */
    public void saveAllTasks(TaskList tasks) {
        try {
            String content = "";

            for (int i = 0; i < tasks.size(); i += 1) {
                Task task = tasks.get(i);
                content += task.toSaveFormat() + "\n";
            }

            Files.writeString(filePath, content, 
                    StandardOpenOption.CREATE, 
                    StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {

            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();

        }
    }

    /**
     * Reads the local storage for tasks
     * Reads each line as a command to construct the task stored
     *
     * @return an ArrayList of Commands
     */
    public ArrayList<Command> readAllLines() {
        ArrayList<Command> commands = new ArrayList<Command>();

        try {
            List<String> lines = Files.readAllLines(filePath);        
            lines.forEach((line) -> {
                Command newCommand = new Command(line);
                String commandType = newCommand.get("commandType");
                assert (commandType.equals("todo") || commandType.equals("deadline") || commandType.equals("event") );
                commands.add(new Command(line));
            });
        } catch (IOException e) {

            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();

        }
        
        return commands;
    }
}