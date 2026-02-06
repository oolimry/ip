package ducky;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Ducky {

    private final TaskList tasks = new TaskList();
    private final SaveManager saveManager = new SaveManager();
    private final UI ui = new UI();

    /**
     * Constructor for Ducky.
     * Upon constructing, runs the main loop
     */
    public Ducky() {
        ArrayList<Command> loadedTaskCommands = saveManager.readAllLines();

        ui.printWelcomeMessage();

        if (!loadedTaskCommands.isEmpty()){
           ui.printMessage("The following tasks were saved the last time: ");

            loadedTaskCommands.forEach(this::runCommand);

            ui.printMessage("Tasks Loaded!\n");
        }
        /*
        while (true) {
            String userInput = ui.getInput();

            if (userInput.equals("bye")) {
                return;
            }

            Command command = new Command(userInput);

            runCommand(command);
        }
        */
    }

    /**
     * Takes in a Command and runs it
     *
     * @param command the command to run
     */
    private void runCommand (Command command) {
        String mainCommand = command.get("commandType");
        String commandValue = command.get("commandValue");

        switch (mainCommand) {

        case "list" -> {
            for (int i = 0; i < tasks.size(); i++) {
                ui.printMessage(
                        String.valueOf(i + 1) + ": " + tasks.get(i).toString()
                );
            }
        }

        case "mark" -> {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                tasks.get(taskNumber - 1).markAsDone();

                ui.printMessage("Quack! I've marked task " + taskNumber + " \""
                        + tasks.get(taskNumber - 1).getDescription() + "\" as done");
            } catch (NumberFormatException e) {
                ui.printMessage(commandValue + " is not number.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            } catch (IndexOutOfBoundsException e) {
                ui.printMessage(commandValue + " is out of bounds.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }

            saveManager.saveAllTasks(tasks);
        }

        case "unmark" -> {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                tasks.get(taskNumber - 1).ummarkAsDone();

                ui.printMessage("Quack. I've set task " + taskNumber + " \""
                        + tasks.get(taskNumber - 1).getDescription() + "\" as not done");
            } catch (NumberFormatException e) {
                ui.printMessage(commandValue + " is not number.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            } catch (IndexOutOfBoundsException e) {
                ui.printMessage(commandValue + " is out of bounds.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }

            saveManager.saveAllTasks(tasks);
        }

        case "delete" -> {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                Task taskRemoved = tasks.remove(taskNumber - 1);

                ui.printMessage("Quack! I've deleted task " + taskNumber + " \""
                        + taskRemoved.getDescription() + "\"");
            } catch (NumberFormatException e) {
                ui.printMessage(commandValue + " is not number.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            } catch (IndexOutOfBoundsException e) {
                ui.printMessage(commandValue + " is out of bounds.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }

            saveManager.saveAllTasks(tasks);
        }

        case "todo" -> {
            ToDoTask newTask = new ToDoTask(commandValue);
            tasks.add(newTask);

            if (command.get("marked") != null) {
                newTask.isDone = true;
            }

            ui.printMessage("Added todo: " + newTask);

            saveManager.saveAllTasks(tasks);
        }

        case "deadline" -> {
            try {
                if (!command.containsKey("by")) {
                    ui.printMessage("Please follow the format");
                    ui.printMessage("deadline <taskname> /by <time>");
                    return;
                }

                LocalDate deadline = LocalDate.parse(command.get("by"));

                DeadlineTask newTask = new DeadlineTask(commandValue, deadline);

                if (command.get("marked") != null) {
                    newTask.isDone = true;
                }

                tasks.add(newTask);

                ui.printMessage("Added deadline: " + newTask);

                saveManager.saveAllTasks(tasks);
            } catch (DateTimeParseException e) {
                ui.printMessage("Date Format Error! Please enter deadline in the format of " +
                        Constants.SAVE_DATE_FORMAT);
            }
        }

        case "event" -> {


            if (!command.containsKey("from") || !command.containsKey("to")) {
                ui.printMessage("Please follow the format");
                ui.printMessage("event <taskname> /from <time> /to <time>");
                return;
            }

            LocalDate from = LocalDate.parse(command.get("from"));
            LocalDate to = LocalDate.parse(command.get("to"));

            EventTask newTask = new EventTask(commandValue, from, to);

            if (command.get("marked") != null) {
                newTask.isDone = true;
            }

            tasks.add(newTask);

            ui.printMessage("Added event: " + newTask);

            saveManager.saveAllTasks(tasks);
        }

        case "find" -> {
            String criteria = command.get("commandValue");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);

                if (!task.getDescription().contains(criteria)) {
                    continue;
                }

                ui.printMessage(
                        String.valueOf(i + 1) + ": " + task.toString()
                );
            }
        }

        default -> ui.printMessage("Command not found!");
        }
    }

    public static void main(String[] args) {
        Ducky ducky = new Ducky();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Ducky heard: " + input;
    }
}
