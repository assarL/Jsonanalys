package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;


public class ControlController {

    public javafx.scene.control.Menu fileMenu;
    @FXML
    private Button ImportToDatabaseButton;
    @FXML
    private Button SetTableEditableButton;
    @FXML
    private Button ShowMapButton;
    @FXML
    private Button ShowJsonButton;
    @FXML
    private javafx.scene.control.MenuItem menuItemOpenFile;
    @FXML
    private TextFlow logField;

    private FileChooser fileChoice;
    private File selectedFile;
    private Stage stage;

    private Text logText = new Text("[ControlController] Väntar på filval ");



    public void initialize() {
        System.out.println("[ControlController] initialize i Controller anropad");

        SetTableEditableButton.setDisable(true);
        ImportToDatabaseButton.setDisable(true);
        ShowMapButton.setDisable(true);
        ShowJsonButton.setDisable(true);
        fileChoice = new FileChooser();
        fileChoice.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChoice.setInitialDirectory(new File("C:\\"));
        stage = new Stage();
        menuItemOpenFile.setOnAction(event -> {
            selectedFile = fileChoice.showOpenDialog(stage);  // Fil väljs..

            logText.setText("[ControlController]Fil öppnas: " + selectedFile.toString());
            JsonFileDecoder jsonDecoded = new JsonFileDecoder(selectedFile); // Vald fil kodas till json-objekt.
        });/*kod för att öppna fil*/

        logField.getChildren().add(logText);

    }

}
/*
     Text text1 = new Text("Big italic red text");
     text1.setFill(Color.RED);
     text1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 40));
     Text text2 = new Text(" little bold blue text");
     text2.setFill(Color.BLUE);
     text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
     TextFlow textFlow = new TextFlow(text1, text2);
     */