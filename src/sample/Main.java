package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Programmet ligger i :" + new File("").getAbsolutePath());// Kolla var programmet ligger i filsystemet..
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();

        //Skapa databas och tabell för länstillhörighet. Hämtas från Res/Lanstillhorighet.csv
        LansTable lanstable = new LansTable();  // Tabeller skapas från csv i  om de inte redan finns.
        lanstable.Initialize(); // Skapar tabell om den inte redan finns.
        lanstable.FillTableFromFile(lanstable.ReadDataFile()); // Läser in csv-fil i tabell med tabelldata från csv-fil.

        //Skapa tabell för artvariabler, hämtas från Res/speciesVariables.csv
        SpeciesTable speciestable = new SpeciesTable();
        speciestable.Initialize();
        speciestable.FillTableFromFile(speciestable.ReadDataFile());

        //Läs in en geoJSON-styrfil.
        File dir = new File("path/to/files/");

 /*       for (File file : dir.listFiles()) {
            Scanner s = new Scanner(file);
            s.close();*/
    }

    JsonFileDecoder styrFil = new JsonFileDecoder(new File("Res/Akerkant.json"));
}









    public static void main(String[] args) {
        launch(args);
    }

}

