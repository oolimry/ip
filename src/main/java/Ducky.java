import java.util.Scanner; 

public class Ducky {
    public static void main(String[] args) {
        System.out.println("Hi! My name is Ducky!");
        System.out.println("I can repeat whatever you said :)");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                break;
            }

            else {
                System.out.println(userInput);
            }
        }
    }
}
