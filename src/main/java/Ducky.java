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

            if (userInput.equals("bye")) {
                break;
            }

            else if (userInput.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(
                        String.valueOf(i+1) + ": " + tasks.get(i).getDescription()
                    );
                }
            }
            else {
                tasks.add(new Task(userInput));

                System.out.println("Added task: " + userInput);
            }
        }
    }
}
