import com.sun.speech.freetts.VoiceManager;
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

    public void init(Scene scene) {
        this.button = (Button) scene.lookup("#button");
        this.textField = (TextField) scene.lookup("#textField");
        this.listView = (ListView<String>) scene.lookup("#listView");
        this.webView = (WebView) scene.lookup("#webView");
        this.speak = (Button) scene.lookup("#speak");
    }

    public void display(Scene scene) {
        DictionaryApplication context = this;
        init(scene);
        textFieldAction();
        listViewAction();
        buttonAction();

    }

    public void textFieldAction() {
        textField.setOnAction(event -> {
            DictionaryCommandline.searcherResult.clear();
            this.listView.getItems().clear();
            DictionaryCommandline.dictionarySearcher(textField.getText());
            this.listView.getItems().addAll(DictionaryCommandline.searcherResult);
        });
    }

    public void listViewAction() {
        this.listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        textField.setText(newValue.trim());
                    }
                }
        );
    }

    public void buttonAction() {
        DictionaryApplication context = this;
        button.setOnAction(event -> {
            context.webView.getEngine().loadContent(Dictionary.dic.get(textField.getText()), "text/html");
        });
        speak.setOnAction(event -> {
            Voice voice = new Voice("kevin16");
            voice.say(textField.getText());
        });
    }
}

class Voice {
    private String name;
    private com.sun.speech.freetts.Voice voice;

    public Voice(String name) {
        this.name = name;
        this.voice = VoiceManager.getInstance().getVoice(this.name);
        this.voice.allocate();
    }

    public void say(String st) {
        this.voice.speak(st);
        this.voice.deallocate();
    }
}
