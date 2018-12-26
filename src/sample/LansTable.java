package sample;

import java.io.File;
import java.sql.*;
import java.util.List;

public class LansTable extends TableSetup{


    // Koppla upp mot databasen. En ny skapas automatiskt om ingen finns.

    LansTable() throws SQLException {
        con = getConnection("jdbc:sqlite:incoming.db");
        System.out.println("[LansTable]Databas uppkopplad "+ con.getMetaData().getURL());
    }

    // Skapa en tom tabell om ingen finns

    public void Initialize() throws SQLException, ClassNotFoundException {
        // Skriv ut vilken databas som används. I framtiden kan det vara olika för olika tabeller.
        DatabaseMetaData mtdt = con.getMetaData();
        System.out.println("URL in use: " + mtdt.getURL());
        System.out.println("User name: " + mtdt.getUserName());
        System.out.println("DBMS name: " + mtdt.getDatabaseProductName());
        System.out.println("DBMS version: " + mtdt.getDatabaseProductVersion());
        System.out.println("Driver name: " + mtdt.getDriverName());
        System.out.println("Driver version: " + mtdt.getDriverVersion());
        System.out.println("supp. SQL Keywords: " + mtdt.getSQLKeywords());


            String sql = ("CREATE TABLE IF NOT EXISTS Lanstillhorighet (\n"  // String tablename = 'Lanstillhorighet'
                    + "	TraktId integer PRIMARY KEY,\n"                      // Integer col1 =
                    + "	Lan text NOT NULL,\n"
                    + "	FullName text NOT NULL,\n"
                    + "	Lanskod integer NOT NULL\n"
                    + ");");
            createTable(sql);

        }

    public ResultSet getResult() throws SQLException{

        Statement state = con.createStatement();
        return state.executeQuery("SELECT TraktId as 'Trakt', lan as 'län' FROM Lanstillhorighet");

    }

    // läs in en förberedd csv-fil med data
    public List<String[]> ReadDataFile(){
        String lansPath = "Res/Lanstillhorighet.csv";
        CSVReader csvLan = new CSVReader(new File(lansPath), ";");
        return csvLan.getListOfLists();
    }

    // fyll tabellen med data från csv-filen

    public void FillTableFromFile(List<String[]> listOfRows) throws SQLException {
        ResultSet rs = getResult();
        System.out.println("[LansTable]Resultset 'res.next()' har värdet:"+rs.next());
        if (!rs.next()) { // Om data inte finns inlagt sedan tidigare hämtas det från fil.
            con.setAutoCommit(false);

            int count = 0; // Separat räknare för att skapa lagoma bitar för inserts i tabellen.
            int batchSize = 999;

            PreparedStatement prep1 = con.prepareStatement("INSERT INTO 'Lanstillhorighet' values(?,?,?,?);");

            for (String[] temp : listOfRows) {
                // Loopa igenom csv-filen och fyll tabellen.

                prep1.setInt(1, Integer.parseInt(temp[0]));
                prep1.setString(2, temp[1]);
                prep1.setString(3, temp[2]);
                prep1.setInt(4, Integer.parseInt(temp[3]));
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

        }System.out.println("[LansTable] Lämnar FillTableFromFile..");
    }

}

