import java.util.Scanner; 
import java.util.ArrayList;

public class Ducky {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        System.out.println("Hi! My name is Ducky!");
        System.out.println("I add tasks wow");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            String[] userInputArgs = userInput.split("\\s+");

            //System.out.println(userInputArgs);

            String mainCommand = userInputArgs[0];

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
                int taskNumber = Integer.parseInt(userInputArgs[1]);

                tasks.get(taskNumber - 1).markAsDone();

                System.out.println("Quack! I've marked task " + taskNumber + " \"" 
                    + tasks.get(taskNumber - 1).getDescription() + "\" as done");
            }

            else if (mainCommand.equals("unmark")) {
                int taskNumber = Integer.parseInt(userInputArgs[1]);

                tasks.get(taskNumber - 1).ummarkAsDone();

                System.out.println("Quack. I've set task " + taskNumber + " \"" 
                    + tasks.get(taskNumber - 1).getDescription() + "\" as not done");
            }

            else {
                tasks.add(new Task(userInput));

                System.out.println("Added task: " + userInput);
            }
        }
    }
}
