package view;

import ducky.Ducky;
import ducky.input.InputPattern;
import ducky.input.InputPredictor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Label autocompleteList;

    private Ducky ducky;
    private InputPredictor inputPredictor;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image duckyImage = new Image(this.getClass().getResourceAsStream("/images/ducky.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            onUserInputUpdated(newValue);
        });
    }

    /**
     * Injects the Ducky instance
     */
    public void setDucky(Ducky d) {
        ducky = d;
        inputPredictor = new InputPredictor(ducky::getTaskListSize);
        String firstMessage = ducky.getFirstMessage();
        dialogContainer.getChildren().add(
                DialogBox.getDuckyDialog(firstMessage, duckyImage)
        );
    }

    /**
     * Runs whenever the string in the input box is changed
     * Updates the prediction
     *
     * @param stringInUserInput the string in the input box
     */
    private void onUserInputUpdated(String stringInUserInput) {
        autocompleteList.setVisible(true);
        ArrayList<String> predictions = inputPredictor.getNextPossibleSegments(stringInUserInput);

        String[] segments = stringInUserInput.split(" ");

        int spacePaddingLength = segments.length - 1;
        for (int i = 0; i < segments.length - 1; i++) {
            spacePaddingLength += segments[i].length();
        }
        if (!stringInUserInput.isEmpty() && stringInUserInput.charAt(stringInUserInput.length() - 1) == ' ') {
            spacePaddingLength += segments[segments.length-1].length();
        }
        if (spacePaddingLength < 0){
            spacePaddingLength = 0;
        }
        String spacePadding = " ".repeat(spacePaddingLength);

        String textToDisplay = "";
        if (predictions.isEmpty()) {
            textToDisplay = spacePadding + "No Known Command";
        }
        else if (predictions.contains(InputPattern.MATCHES_COMPLETELY)) {
            textToDisplay = spacePadding + "Command is Complete";
        }
        else{
            textToDisplay = "";

            for (int i = 0; i < predictions.size(); i++){
                textToDisplay += spacePadding;
                textToDisplay += predictions.get(i);
                if (i != predictions.size() - 1) {
                    textToDisplay += "\n";
                }
            }
        }

        autocompleteList.setText(textToDisplay);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleEnterPressed() {
        String input = userInput.getText();

        String response = ducky.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDuckyDialog(response, duckyImage)
        );
        userInput.clear();

        if(input.equals("bye")) {
            System.exit(0);
        }

        autocompleteList.setVisible(false);
    }
}
