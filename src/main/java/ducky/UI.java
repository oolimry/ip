package ducky;

import java.util.Scanner;

public class UI {
    public void printWelcomeMessage() {
        System.out.println("Hi! My name is Ducky!");
        System.out.println("I add tasks wow");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        return userInput;
    }
}
