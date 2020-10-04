import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class DictionaryApplication extends Application {

    public static void runApplication(String path, String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BoxMessage.optionDisplay();
    }
}
class BoxMessage {
    static Stage window;
    public static void resultDisplay(String title, String message) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);


        Scene scene = new Scene(layout, 700, 500);
        window.setScene(scene);
        window.showAndWait();
    }
    public static void optionDisplay(){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Dictionary options");

        Label label = new Label();
        label.setText("Choose your option");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("English - Vietnamese", "Vietnamese - English");
        choiceBox.setOnAction(event -> {
            if(choiceBox.getValue().equals("English - Vietnamese")) {
                try {
                    DictionaryManagement.insertFromFile("E:\\My documents\\JavaProject\\src\\E_V.html");
                    window.close();
                    mainDisplay("English - Vietnamese Dictionary");
                } catch (IOException e) {
                    resultDisplay("Error", "Cant open file!!!");
                }
            }
            if(choiceBox.getValue().equals("Vietnamese - English")){
                try {
                    DictionaryManagement.insertFromFile("E:\\My documents\\JavaProject\\src\\V_E.html");
                    window.close();
                    mainDisplay("Vietnamese - English Dictionary");
                } catch (IOException e) {
                    resultDisplay("Error", "Cant open file!!!");
                }
            }
        });
        Button closeButton = new Button("close");
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(label, choiceBox, closeButton);

        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.showAndWait();
    }
    private static boolean isInt(String word_target) {
        if(!Dictionary.dic.containsKey(word_target)){
            BoxMessage.resultDisplay("Result","Cant find: " + word_target);
            return false;
        }
        BoxMessage.resultDisplay("Result", Dictionary.dic.get(word_target).replaceAll("\\<.*?>",""));
        return true;
    }
    public static void mainDisplay(String title){
        window = new Stage();
        window.setTitle(title);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label label = new Label("Enter your word");
        GridPane.setConstraints(label, 0, 0);

        TextField textField = new TextField();
        textField.setPromptText("Sign in");
        GridPane.setConstraints(textField, 0, 1);

        Button translateButton = new Button("Translate");
        translateButton.setOnAction(event -> {
            window.close();
            isInt(textField.getText());
        });
        GridPane.setConstraints(translateButton, 1,  1);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> window.close());
        GridPane.setConstraints(closeButton, 0, 2);

        Button returnButton = new Button("Return");
        returnButton.setOnAction(event -> {
            window.close();
            optionDisplay();
        });
        GridPane.setConstraints(returnButton, 0, 3);

        grid.getChildren().addAll(label, textField, translateButton, closeButton, returnButton);
        Scene scene = new Scene(grid, 500, 500);
        window.setScene(scene);

        window.show();
    }
}

