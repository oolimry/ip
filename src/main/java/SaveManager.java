import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {
    Path directoryPath;
    Path filePath;

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

    public ArrayList<Task> readAllTasks() {
        return null;
    }
}