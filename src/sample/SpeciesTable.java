package sample;

import java.io.File;
import java.sql.*;
import java.util.List;

public class SpeciesTable extends TableSetup  {


    // Koppla upp mot databasen. En ny skapas automatiskt om ingen finns.

    public SpeciesTable() throws SQLException {
        con = getConnection("jdbc:sqlite:incoming.db");
        System.out.println("[SpeciesTable]Databas uppkopplad " + con.getMetaData().getURL());
    }

    public void Initialize() throws SQLException, ClassNotFoundException {
        // Skriv ut vilken databas som används. I framtiden kan det vara olika för olika tabeller.
        DatabaseMetaData mtdt = con.getMetaData();
        System.out.println("URL in use: " + mtdt.getURL());
        System.out.println("User name: " + mtdt.getUserName());
        System.out.println("DBMS name: " + mtdt.getDatabaseProductName());

        String sql = ("CREATE TABLE IF NOT EXISTS Artlista (\n"
                + "	ArtVariabel text PRIMARY KEY,\n"     // Variabel som innehåller artdata
                + "	CreateDate text NOT NULL\n"        //
                + ");");
        createTable(sql);
    }

    public ResultSet getResult() throws SQLException{

        Statement state = con.createStatement();
        return state.executeQuery("SELECT ArtVariabel as 'Name', CreateDate FROM Artlista");

    }

    // läs in en förberedd csv-fil med data
    public List<String[]> ReadDataFile(){
        String speciesPath = "Res/speciesVariables.csv";
        CSVReader csvLan = new CSVReader(new File(speciesPath), ";");
        return csvLan.getListOfLists();
    }

    // fyll tabellen med data från csv-filen

    public void FillTableFromFile(List<String[]> listOfRows) throws SQLException {
        ResultSet rs = getResult();
        System.out.println("[SpeciesTable]Resultset 'res.next()' har värdet:" + rs.next());
        if (!rs.next()) { // Om data inte finns inlagt sedan tidigare hämtas det från fil.
            con.setAutoCommit(false);

            int count = 0; // Separat räknare för att skapa lagoma bitar för inserts i tabellen.
            int batchSize = 999;

            PreparedStatement prep1 = con.prepareStatement("INSERT INTO 'Artlista' values(?,?);");

            for (String[] temp : listOfRows) {
                // Loopa igenom csv-filen och fyll tabellen.

                prep1.setString(1, temp[0]);
                prep1.setString(2, temp[1]);
                prep1.addBatch();

                if (++count % batchSize == 0) {  // Exekverar inserts när "batchsize" antal rader lästs in.
                    prep1.executeBatch(); //
                }
            }// Slut på loop
            prep1.executeBatch();
            con.commit();
            prep1.close();
            con.setAutoCommit(true);
            con.close();// Stäng av databaskontakten
        }System.out.println("[SpeciesTable] Lämnar FillTableFromFile..");
    }
}
    // Skapa en tom tabell om ingen finns

