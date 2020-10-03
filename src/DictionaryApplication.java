import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class DictionaryApplication extends Application {
    private Stage window;

    public static void runApplication(String path, String[] args) throws IOException {
        DictionaryManagement.insertFromFile(path);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("English - Vietnamese dictionary");
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
        translateButton.setOnAction(event -> isInt(textField.getText()));
        GridPane.setConstraints(translateButton, 1,  1);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> window.close());
        GridPane.setConstraints(closeButton, 0, 2);

        grid.getChildren().addAll(label, textField, translateButton, closeButton);
        Scene scene = new Scene(grid, 500, 500);
        window.setScene(scene);

        window.show();
    }

    private boolean isInt(String word_target) {
        if(!Dictionary.dic.containsKey(word_target)){
            BoxMessage.display("Result","Cant find: " + word_target);
            return false;
        }
        BoxMessage.display("Result", Dictionary.dic.get(word_target).replaceAll("\\<.*?>",""));
        return true;
    }
}
class BoxMessage {
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);


        Scene scene = new Scene(layout, 700, 500);

        window.setScene(scene);
        window.showAndWait();
    }
}

