package ducky;

import java.util.Scanner;

public class UI {
    public void printWelcomeMessage() {
        System.out.println("Hi! My name is Ducky!");
        System.out.println("I add tasks wow");
    }

    private String accumulatedMessages = "";

    public void printMessage(String message) {
        if(!accumulatedMessages.equals("")) {
            accumulatedMessages += "\n";
        }
        accumulatedMessages += message;
        System.out.println(message);
    }

    public void clearAccumulatedMessages() {
        accumulatedMessages = "";
    }

    public String getAccumulatedMessages() {
        return accumulatedMessages;
    }

    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
