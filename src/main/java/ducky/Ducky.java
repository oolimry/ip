package ducky;

import java.util.Scanner; 
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Ducky {

    private TaskList tasks = new TaskList();
    private SaveManager saveManager = new SaveManager();
    private UI ui = new UI();

    /**
     * Constructor for Ducky.
     * Upon constructing, runs the main loop
     */
    public Ducky() {
        ArrayList<Command> loadedTaskCommands = saveManager.readAllLines();

        ui.printWelcomeMessage();

        if (loadedTaskCommands.size() > 0){
           ui.printMessage("The following tasks were saved the last time: ");

            loadedTaskCommands.forEach((command) -> {
                runCommand(command);
            });

            ui.printMessage("Tasks Loaded!\n");
        }

        while (true) {
            String userInput = ui.getInput();

            if (userInput.equals("bye")) {
                return;
            }

            Command command = new Command(userInput);

            runCommand(command);
        }
    }

    /**
     * Takes in a Command and runs it
     *
     * @param command
     */
    private void runCommand (Command command) {
        String mainCommand = command.get("commandType");
        String commandValue = command.get("commandValue");

        if (mainCommand.equals("list")) {
            for (int i = 0; i < tasks.size(); i++) {
                ui.printMessage(
                        String.valueOf(i+1) + ": " + tasks.get(i).toString()
                );
            }
        }

        else if (mainCommand.equals("mark")) {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                tasks.get(taskNumber - 1).markAsDone();

                ui.printMessage("Quack! I've marked task " + taskNumber + " \""
                        + tasks.get(taskNumber - 1).getDescription() + "\" as done");
            }
            catch (NumberFormatException e) {
                ui.printMessage(commandValue + " is not number.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }
            catch (IndexOutOfBoundsException e) {
                ui.printMessage(commandValue + " is out of bounds.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }

            saveManager.saveAllTasks(tasks);
        }

        else if (mainCommand.equals("unmark")) {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                tasks.get(taskNumber - 1).ummarkAsDone();

                ui.printMessage("Quack. I've set task " + taskNumber + " \""
                        + tasks.get(taskNumber - 1).getDescription() + "\" as not done");
            }
            catch (NumberFormatException e) {
                ui.printMessage(commandValue + " is not number.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }
            catch (IndexOutOfBoundsException e) {
                ui.printMessage(commandValue + " is out of bounds.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }

            saveManager.saveAllTasks(tasks);
        }

        else if (mainCommand.equals("delete")) {
            try {
                int taskNumber = Integer.parseInt(commandValue);

                Task taskRemoved = tasks.remove(taskNumber - 1);

                ui.printMessage("Quack! I've deleted task " + taskNumber + " \""
                        + taskRemoved.getDescription() + "\"");
            }
            catch (NumberFormatException e) {
                ui.printMessage(commandValue + " is not number.\n" +
                        "Please input a task index between 1 and " + String.valueOf(tasks.size()));
            }
            catch (IndexOutOfBoundsException e) {
                ui.printMessage(commandValue + " is out of bounds.\n" +
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

            ui.printMessage("Added todo: " + newTask);

            saveManager.saveAllTasks(tasks);
        }

        else if (mainCommand.equals("deadline")) {
            try {
                LocalDate deadline = LocalDate.parse(command.get("by"));

                if (deadline == null) {
                    ui.printMessage("Please follow the format");
                    ui.printMessage("deadline <taskname> /by <time>");
                    return;
                }

                DeadlineTask newTask = new DeadlineTask(commandValue, deadline);

                if (command.get("marked") != null) {
                    newTask.isDone = true;
                }

                tasks.add(newTask);

                ui.printMessage("Added deadline: " + newTask);

                saveManager.saveAllTasks(tasks);
            }
            catch (DateTimeParseException e) {
                ui.printMessage("Date Format Error! Please enter deadline in the format of " +
                        Constants.SAVE_DATE_FORMAT);
            }
        }

        else if (mainCommand.equals("event")) {
            LocalDate from = LocalDate.parse(command.get("from"));
            LocalDate to = LocalDate.parse(command.get("to"));


            if (from == null || to == null) {
                ui.printMessage("Please follow the format");
                ui.printMessage("event <taskname> /from <time> /to <time>");
                return;
            }

            EventTask newTask = new EventTask(commandValue, from, to);

            if (command.get("marked") != null) {
                newTask.isDone = true;
            }

            tasks.add(newTask);

            ui.printMessage("Added event: " + newTask);

            saveManager.saveAllTasks(tasks);
        }


        else {
            ui.printMessage("Command not found!");
        }
    }

    public static void main(String[] args) {
        Ducky ducky = new Ducky();
    }


}
