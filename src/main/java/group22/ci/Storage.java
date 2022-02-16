package group22.ci;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class that stores all past builds
 * An index file holds links to all builds' files
 */
public class Storage {


    /**
     * Creates index file that holds links to all builds' files
     */
    public static void createIndexFile() throws IOException{
        File history = new File("BuildHistory");
        // Check that buildlog exist
        if (!history.exists()) {
            history.mkdir();
            File index = new File("./BuildHistory/index.html");
            index.createNewFile();
        }
    }
    /**
     * Stores a new build
     */
    public static void addToStorage(PayLoad p, String compileRes, MavenResult testRes) throws IOException {
        createIndexFile();
        String path = "./BuildHistory/" + p.id + ".html";
        File build = new File(path);
        build.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write("<p> Commit ID: " + p.id +" </p>\n");
        bw.write("<p> Compile result: " + compileRes +" </p>\n");
        bw.write("<p> Test result: " + testRes.getMessage() +" </p>\n");
        bw.write("<p> Test details: " + testRes.getDetails() +" </p>\n");
        bw.write("<p> Name: " + p.name +" </p>\n");
        bw.write("<p> Date: " + p.date +" </p>");
        bw.close(); //kanske flush
        updateIndex(p.id, path);
    }

    /**
     * Updates the index file
     */
    public static void updateIndex(String id, String path) throws IOException{
         BufferedWriter bw = new BufferedWriter(new FileWriter("./BuildHistory/index.html", true));
         bw.write("<p> <a href=\"." + path + "\"> Build for " + id + " </a> </p>\n");
         bw.close();
    }
}


