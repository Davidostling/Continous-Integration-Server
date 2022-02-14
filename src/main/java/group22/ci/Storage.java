package group22.ci;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {

    public static void addToStorage(PayLoad p, String compileRes, MavenResult testRes) throws JSONException {

        File history = new File("storage.json");

        // Check that logfile exist
        if (!history.exists()) {
            // throw någon exeption typ
        }
        else {
            //Creating an object of type JSONObject
            JSONObject jsonObject = new JSONObject();
            //Inserting relevant values into jsonobject
            jsonObject.put("ID", p.id);
            jsonObject.put("Status", compileRes);
            jsonObject.put("URL", p.url);
            jsonObject.put("TestRes", testRes.result);
            jsonObject.put("Date", p.date);

            try {
                FileWriter file = new FileWriter("storage.json");
                file.write(jsonObject.toString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("JSON file created: " + jsonObject);
        }
    }
}


