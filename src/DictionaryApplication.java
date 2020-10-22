import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class DictionaryApplication extends Application {
    private static final String FXML_PATH = "src/Dictionary_FX.fxml";
    private static final String CSS_PATH = "myStyle.css";
    private static final String VOICE_TYPE = "kevin16";
    @FXML
    private TextField textField;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView webView;

    public static void runApplication(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new FXMLLoader().load(new FileInputStream(FXML_PATH)));
        primaryStage.setScene(scene);
        primaryStage.setTitle("English - Vietnamese Dictionary");
        primaryStage.show();
        DBConnection.readAllData();
        display(scene);

    }

    public void init(Scene scene) {
        this.textField = (TextField) scene.lookup("#textField");
        this.listView = (ListView<String>) scene.lookup("#listView");
        this.webView = (WebView) scene.lookup("#webView");
    }

    public void display(Scene scene) throws IOException {
        scene.getStylesheets().add(CSS_PATH);
        init(scene);
        textFieldAction();
        listViewAction();
        speakAction();
        searchAction();
    }

    public void textFieldAction() {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.listView.getItems().clear();
            this.listView.getItems().addAll(Objects.requireNonNull(DictionaryCommandline.dictionarySearcher(textField.getText().toLowerCase())));
        });
    }


    public void listViewAction() {
        this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> textField.setText(newValue.trim()));
    }

    @FXML
    public void searchAction() throws IOException {
        DictionaryApplication context = this;
        if (Dictionary.dic.containsKey(textField.getText())) {
            context.webView.getEngine().loadContent(Dictionary.dic.get(textField.getText()), "text/html");
        } else {
            context.webView.getEngine().loadContent(Translator.translate("en", "vi", textField.getText()));
        }
    }

    @FXML
    public void speakAction() {
        Voice voice = new Voice(VOICE_TYPE);
        voice.say(textField.getText());
    }

    @FXML
    public void deleteAction() {
        AlertBox.display1("Delete tool", "Enter a word you want to delete: ");
    }

    @FXML
    public void modifyAction() {
        AlertBox.display2("Modify tool", "Enter target word", "Modify with explain word");
    }

    @FXML
    public void addAction() {
        AlertBox.display2("Add tool", "Enter target word", "Enter explain word");
    }

    @FXML
    public void exitAction() {
        Platform.exit();
    }
}

class AlertBox {
    public static void display1(String title, String command) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label label = new Label();
        label.setText(command);

        TextField textField = new TextField("hello");
        textField.setOnAction(event -> {
            if (Dictionary.dic.containsKey(textField.getText())) {
                Dictionary.dic.remove(textField.getText());
                try {
                    DBConnection.deleteData(textField.getText());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                display3("Successful");
            } else {
                display3("Can't find " + textField.getText());
            }
        });

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, textField);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void display2(String title, String command1, String command2) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label label1 = new Label();
        label1.setText(command1);

        Label label2 = new Label();
        label2.setText(command2);

        TextField textField1 = new TextField("hello");
        TextField textField2 = new TextField("xin chao");
        Button button = new Button("Enter");
        button.setOnAction(event -> {
            if (title.equals("Modify tool")) {
                try {
                    modifyWord(textField1.getText(), textField2.getText());
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            } else if (title.equals("Add tool")) {
                try {
                    addWord(textField1.getText(), textField2.getText());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label1, textField1, label2, textField2, button);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void addWord(String word_target, String word_explain) throws SQLException, ClassNotFoundException {
        if (Dictionary.dic.containsKey(word_target)) {
            display3(word_target + " was existed");
        } else {
            word_explain = "<html>" + word_explain + "</html>";
            Dictionary.dic.put(word_target, word_explain);
            DBConnection.insertData(word_target, word_explain);
            display3("Successful");
        }
    }

    public static void modifyWord(String word_target, String word_explain) throws SQLException, ClassNotFoundException {
        boolean check = false;
        for (String word : Dictionary.dic.keySet()) {
            if (word.equals(word_target)) {
                Dictionary.dic.put(word_target, "<html>" + word_explain + "</html>");
                check = true;
                DBConnection.updateData(word_target, "<html>" + word_explain + "</html>");
                display3("Successful");
            }
        }
        if (!check) {
            display3(word_target + " is not existed");
        }
    }

    public static void display3(String text) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Result");

        Label label = new Label();
        label.setText(text);
        VBox vBox = new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 150, 75);
        window.setScene(scene);
        window.showAndWait();
    }

}
