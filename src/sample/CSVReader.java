package sample;

import java.io.*;
import java.util.ArrayList;

public class CSVReader {


    private ArrayList<String[]> listOfLists = new ArrayList<>();


    CSVReader(File csvFile, String csvSplitBy)
    {
        System.out.println("[CSVReader] CSV-fil läses in: "+ csvFile.getPath());
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile),"UTF-8")  );  // Filen ska vara i UTF-8 format.

            String row;
            String[] fragments; // Allt hanteras som strängar. fragments är en array av uppdelade rader.

            while ((row = br.readLine()) != null) {

                fragments = row.split(csvSplitBy);

                //System.out.println("[CSVReader] Fragment:"+fragments[0] +"  "+ fragments[1]+"  "+fragments[2]+"  "+fragments[3]);

                listOfLists.add(fragments);
        }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException i [CSVReader}"+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException in CSV-Reader orsak: "+e.getMessage());
            e.printStackTrace()
            ;
        }
    }

    public ArrayList<String[]> getListOfLists() {
        System.out.println("[CSVReader] returnerad Array av csv-rader: "+listOfLists.size()+" rader");
        return listOfLists;    // Allt läggs till en lista där man måste kolla vad som kommer tillbaks.
    }

}




