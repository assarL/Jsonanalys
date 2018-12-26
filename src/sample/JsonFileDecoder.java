package sample;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class JsonFileDecoder {


    JSONObject jsonObject;

    private boolean isStyrfil = true;

    public JsonFileDecoder(File file) {


        try {
            JSONParser jsonParser = new JSONParser(); // Skapa en parser
            FileReader filereader = new FileReader(file);        // JSON-filen som ska l�sas in
            BufferedReader bufferedReader = new BufferedReader(filereader);
            bufferedReader.mark(1); // Så här långt in i bufferten ska läsas.
            String firstLine = bufferedReader.readLine().toLowerCase(); // Första raden läses in och kapas bort.

            System.out.println("[JsonFileDecoder]Första raden i filen:" + "'"+firstLine+"'");

            if (firstLine.startsWith("version")) {
                System.out.println("[JsonFileDecoder] Filen innehåller en versionstagg-> det är styrfil. Första raden lyder: " + "'"+firstLine+"'");
                isStyrfil = true;

            } else { // Om det inte är en styrfil sä är det en exportfil...
                System.out.println("[JsonFileDecoder] Filen innehåller ingen versionstagg -> det är alltså en exportfil");
                bufferedReader.reset();  // Gå tillbaka till början av bufferten och läs in den intakt. Detta gäller om inget versionsummer finns och filen följaktligen är en styrfil.
                isStyrfil = false;
            }

            jsonObject = (JSONObject) jsonParser.parse(bufferedReader); // Filen läses in
            System.out.println(jsonObject.toString());
        }
     catch (ParseException e) {
        System.out.println("[GeoJsonDecoder]Parseexception uppt�cktes i geometries. Detta kan bero p� att vissa v�rden saknar citationstecken." +
                "Om det är en styrfil kan det bero på att versionsnumret inte ignoreras");
        System.out.println("[JsonFileDecoder]Parseexception uppt�cktes i geometries. Detta kan bero p� att vissa v�rden saknar citationstecken.");
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }

    public boolean isStyrfil() {
        return isStyrfil;
    }
    public JSONObject getJsonObject() {
        return jsonObject;
    }

}
