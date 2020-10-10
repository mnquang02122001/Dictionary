import javafx.application.Application;
<<<<<<< HEAD
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class DictionaryApplication extends Application {
    private Stage window;
    public static void runApplication(String path, String[] args) throws IOException {
        DictionaryManagement.insertFromFile(path);
=======
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
>>>>>>> 638008393e2e6457c8807a03b2b9bf040404616a
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
<<<<<<< HEAD
        window = primaryStage;
        window.setTitle("English - Vietnamese dictionary");
        FlowPane grid = new FlowPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);
        CheckMenuItem check=new CheckMenuItem();
        check.setSelected(true);
        MenuItem select1=new MenuItem("English - Vietnamese");
        MenuItem select2=new MenuItem("Vietnamese - English");
        ImageView imageViewLang=new ImageView();
        MenuButton selection=new MenuButton("Choose method", imageViewLang, select1, select2);
        select1.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                check.setSelected(true);
            }
        });
        select2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                check.setSelected(false);
            }
        });
        List<String> shownowords=new ArrayList<>();
        shownowords.add("No words found");



        Label label = new Label("Enter your word");

        ListView list=new ListView();
        final ObservableList<List<String>> data= FXCollections.observableArrayList();
        TextField textField = new TextField();
        //textField.setPromptText("Sign in");
        grid.getChildren().addAll(label, textField, list, selection /*translateButton, closeButton*/);
        Scene scene = new Scene(grid, 500, 500);
        window.setScene(scene);
        textField.textProperty().addListener((observableValue, s, t1) -> {
            data.clear();
            data.add(check.isSelected()?DictionaryCommandline._dictionarySearcher(textField.getText()):DictionaryCommandline._swapdictionarySearcher(textField.getText()));
            if(data.get(0).isEmpty()) {
                data.add(new ArrayList<String>(shownowords));
            }
            list.setItems(data);
            list.setCellFactory(ComboBoxListCell.forListView(data));

        window.show();


        });

        /*
        Button translateButton = new Button("Translate");
        translateButton.setOnAction(event -> isInt(textField.getText()));
        FlowPane.setConstraints(translateButton, 1,  1);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> window.close());
        FlowPane.setConstraints(closeButton, 0, 2);
        */





        window.show();
    }
=======
        Scene scene = new Scene(new FXMLLoader().load(new FileInputStream(FXML_PATH)));
        primaryStage.setScene(scene);
        primaryStage.setTitle("English - Vietnamese Dictionary");
        primaryStage.show();
        DictionaryManagement.insertFromFile();
        display(scene);
>>>>>>> 638008393e2e6457c8807a03b2b9bf040404616a

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
