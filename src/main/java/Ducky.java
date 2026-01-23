import java.util.Scanner; 
import java.util.HashMap;

public class Ducky {
    

    public static void main(String[] args) {
        SaveManager saveManager = new SaveManager();
        TaskList tasks = new TaskList();

        System.out.println("Hi! My name is Ducky!");
        System.out.println("I add tasks wow");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            Command command = new Command(userInput);

            String mainCommand = command.get("commandType");
            String commandValue = command.get("commandValue");

            if (userInput.equals("bye")) {
                return;
            }

            else if (mainCommand.equals("list")) {
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

                System.out.println("Added todo: " + newTask);

                saveManager.saveAllTasks(tasks);
            }

            else if (mainCommand.equals("deadline")) {
                String deadline = command.get("by");

                if (deadline == null) {
                    System.out.println("Please follow the format");
                    System.out.println("deadline <taskname> /by <time>");
                    continue;
                }

                DeadlineTask newTask = new DeadlineTask(commandValue, deadline);
                tasks.add(newTask);

                System.out.println("Added deadline: " + newTask);

                saveManager.saveAllTasks(tasks);
            }

            else if (mainCommand.equals("event")) {
                String from = command.get("from");
                String to = command.get("to");

                if (from == null || to == null) {
                    System.out.println("Please follow the format");
                    System.out.println("event <taskname> /from <time> /to <time>");
                    continue;
                }

                EventTask newTask = new EventTask(commandValue, from, to);
                tasks.add(newTask);

                System.out.println("Added event: " + newTask);

                saveManager.saveAllTasks(tasks);
            }


            else {
                System.out.println("Command not found!");
            }
        }
    }
}
