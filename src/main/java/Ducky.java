import java.util.Scanner; 
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Ducky {

    public static void main(String[] args) {
        SaveManager saveManager = new SaveManager();
        TaskList tasks = new TaskList();

        ArrayList<Command> loadedTaskCommands = saveManager.readAllLines();

        System.out.println("Hi! My name is Ducky!");
        System.out.println("I add tasks wow");

        if (loadedTaskCommands.size() > 0){
            System.out.println("The following tasks were saved the last time: ");

            loadedTaskCommands.forEach((command) -> {
                runCommand(command, saveManager, tasks);
            });

            System.out.println("Tasks Loaded!\n");
        }


        while (true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                return;
            }

            Command command = new Command(userInput);
           
            runCommand(command, saveManager, tasks);
        }
    }

    private static void runCommand (Command command, SaveManager saveManager, TaskList tasks) {
        String mainCommand = command.get("commandType");
        String commandValue = command.get("commandValue");

        if (mainCommand.equals("list")) {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(
                    String.valueOf(i+1) + ": " + tasks.get(i).toString()
                );
            }
        }

        else if (mainCommand.equals("mark")) {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                tasks.get(taskNumber - 1).markAsDone();

                System.out.println("Quack! I've marked task " + taskNumber + " \"" 
                    + tasks.get(taskNumber - 1).getDescription() + "\" as done");
            }
            catch (NumberFormatException e) {
                System.out.println(commandValue + " is not number.\n" + 
                    "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println(commandValue + " is out of bounds.\n" + 
                    "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }

            saveManager.saveAllTasks(tasks);
        }

        else if (mainCommand.equals("unmark")) {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                tasks.get(taskNumber - 1).ummarkAsDone();

                System.out.println("Quack. I've set task " + taskNumber + " \"" 
                    + tasks.get(taskNumber - 1).getDescription() + "\" as not done");
            }
            catch (NumberFormatException e) {
                System.out.println(commandValue + " is not number.\n" + 
                    "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println(commandValue + " is out of bounds.\n" + 
                    "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }

            saveManager.saveAllTasks(tasks);
        }

        else if (mainCommand.equals("delete")) {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                Task taskRemoved = tasks.remove(taskNumber - 1);
               
                System.out.println("Quack! I've deleted task " + taskNumber + " \"" 
                    + taskRemoved.getDescription() + "\"");
            }
            catch (NumberFormatException e) {
                System.out.println(commandValue + " is not number.\n" + 
                    "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println(commandValue + " is out of bounds.\n" + 
                    "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }

            saveManager.saveAllTasks(tasks);
        }

        else if (mainCommand.equals("todo")) {
            ToDoTask newTask = new ToDoTask(commandValue);
            tasks.add(newTask);

            if (command.get("marked") != null) {
                newTask.isDone = true;
            }

            System.out.println("Added todo: " + newTask);

            saveManager.saveAllTasks(tasks);
        }

        else if (mainCommand.equals("deadline")) {
            try {
                LocalDate deadline = LocalDate.parse(command.get("by"));

                if (deadline == null) {
                    System.out.println("Please follow the format");
                    System.out.println("deadline <taskname> /by <time>");
                    return;
                }

                DeadlineTask newTask = new DeadlineTask(commandValue, deadline);

                if (command.get("marked") != null) {
                    newTask.isDone = true;
                }

                tasks.add(newTask);

                System.out.println("Added deadline: " + newTask);

                saveManager.saveAllTasks(tasks);
            }
            catch (DateTimeParseException e) {
                System.out.println("Date Format Error! Please enter deadline in the format of " +
                        Constants.SAVE_DATE_FORMAT);
            }
        }

        else if (mainCommand.equals("event")) {
            LocalDate from = LocalDate.parse(command.get("from"));
            LocalDate to = LocalDate.parse(command.get("to"));

            
            if (from == null || to == null) {
                System.out.println("Please follow the format");
                System.out.println("event <taskname> /from <time> /to <time>");
                return;
            }

            EventTask newTask = new EventTask(commandValue, from, to);

            if (command.get("marked") != null) {
                newTask.isDone = true;
            }

            tasks.add(newTask);

            System.out.println("Added event: " + newTask);

            saveManager.saveAllTasks(tasks);
        }


        else {
            System.out.println("Command not found!");
        }
    }
}
