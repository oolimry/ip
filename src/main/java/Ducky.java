import java.util.Scanner; 
import java.util.ArrayList;
import java.util.HashMap;

public class Ducky {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        System.out.println("Hi! My name is Ducky!");
        System.out.println("I add tasks wow");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            HashMap<String, String> parsedInput = parseInputs(userInput);

            String mainCommand = parsedInput.get("command");
            String commandValue = parsedInput.get("commandValue");

            if (userInput.equals("bye")) {
                break;
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
            }

            else if (mainCommand.equals("todo")) {
                ToDoTask newTask = new ToDoTask(commandValue);
                tasks.add(newTask);

                System.out.println("Added todo: " + newTask);
            }

            else if (mainCommand.equals("deadline")) {
                String deadline = parsedInput.get("by");

                if (deadline == null) {
                    System.out.println("Please follow the format");
                    System.out.println("deadline <taskname> /by <time>");
                    continue;
                }

                DeadlineTask newTask = new DeadlineTask(commandValue, deadline);
                tasks.add(newTask);

                System.out.println("Added deadline: " + newTask);
            }

            else if (mainCommand.equals("event")) {
                String from = parsedInput.get("from");
                String to = parsedInput.get("to");

                if (from == null || to == null) {
                    System.out.println("Please follow the format");
                    System.out.println("event <taskname> /from <time> /to <time>");
                    continue;
                }

                EventTask newTask = new EventTask(commandValue, from, to);
                tasks.add(newTask);

                System.out.println("Added event: " + newTask);
            }


            else {
                System.out.println("Command not found!");
            }
        }
    }

    // returns an ArrayList containing the format of the pair (param name, thing)
    // ArrayList[0].first always gives you the main command name
    public static HashMap<String, String> parseInputs(String input) {
        HashMap<String, String> result = new HashMap<String, String>();

        String[] segments = input.split("/");

        for(String segment : segments){
            String[] tokens = segment.split("\\s+");
            String param = tokens[0];
            String value = "";
            for (int i = 1; i < tokens.length; i++) {
                if (i != 1)
                    value += " "; 
                value += tokens[i];
            }

            if (segment == segments[0]) {
                result.put("command", param);
                result.put("commandValue", value);
            }
            
            else {
                result.put(param, value);
            }
        }


        return result;
    } 
}
