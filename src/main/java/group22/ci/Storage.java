package group22.ci;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {

    public static void addToStorage(PayLoad p, String compileRes, MavenResult testRes) throws JSONException, IOException {

        // File history = new File("C:Buildhis/storage.json");
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File history = new File(tempDirectory.getAbsolutePath() + "/storage.txt");
        // Check that logfile exist
        if (!history.exists()) {
            System.out.println("Here");
            history.createNewFile();

        }
            System.out.println("Here2");
            //Creating an object of type JSONObject
            JSONObject jsonObject = new JSONObject();
            //Inserting relevant values into jsonobject
            jsonObject.put("ID", p.id);
            jsonObject.put("Status", compileRes);
            jsonObject.put("URL", p.url);
            jsonObject.put("TestRes", testRes.result);
            jsonObject.put("Date", p.date);

            try {
                System.out.println("Here3");
                FileWriter file = new FileWriter("./Buildhis/storage.json");
                file.write(jsonObject.toString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("JSON file created: " + jsonObject);
        }
}


