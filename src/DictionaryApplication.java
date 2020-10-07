import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class DictionaryApplication extends Application {
    private static final String FXML_PATH = "E:\\My documents\\JavaProject\\src\\Dictionary_FX.fxml";
    @FXML
    private Button button;
    @FXML
    private TextField textField;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView webView;
    @FXML
    private Button speak;

    public static void runApplication(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new FXMLLoader().load(new FileInputStream(FXML_PATH)));
        primaryStage.setScene(scene);
        primaryStage.setTitle("English - Vietnamese Dictionary");
        primaryStage.show();
        DictionaryManagement.insertFromFile();
        display(scene);

    }

    public void display(Scene scene) {
        DictionaryApplication context = this;
        this.button = (Button) scene.lookup("#button");
        this.textField = (TextField) scene.lookup("#textField");
        this.listView = (ListView<String>) scene.lookup("#listView");
        this.webView = (WebView) scene.lookup("#webView");

        textField.setOnAction(event -> {
            DictionaryCommandline.searcherResult.clear();
            this.listView.getItems().clear();
            DictionaryCommandline.dictionarySearcher(textField.getText());
            this.listView.getItems().addAll(DictionaryCommandline.searcherResult);
        });
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        textField.setText(newValue.trim());
                    }
                }
        );
        button.setOnAction(event -> {
            context.webView.getEngine().loadContent(Dictionary.dic.get(textField.getText()), "text/html");
        });
    }
}
