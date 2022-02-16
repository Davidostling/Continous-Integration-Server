package group22.ci;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.*;

/**
 * A ContinuousIntegrationServer which acts as a webhook.
 * Gets information about the build when a push is made.
 */
public class ContinuousIntegrationServer extends AbstractHandler {
    PayLoadHandler ph;
	
    @Override
	/*
	* Function for the handle of the server
	*/
    public void handle(String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);
		//when a push is made to any branch the method will be POST
        if (request.getMethod() == "POST") {
            System.out.println("POST");
            try {
				// saving the payload with the relevant information about the build
                String received = request.getReader().lines().collect(Collectors.joining());
                JSONObject dataJSON = new JSONObject(received);
				// the branch for the commit
                String ref = dataJSON.getString("ref");
                JSONArray commmitarray = dataJSON.getJSONArray("commits");
                JSONObject data = commmitarray.getJSONObject(0);
				// id of the commit
                String id = data.getString("id");
				// date of the commit
                String date = data.getString("timestamp");
				// name of the commiter
                String name = data.getJSONObject("author").getString("name");
				// mail of the commiter
                String mail = data.getJSONObject("author").getString("email");
				// url for the repository
                String url = dataJSON.getJSONObject("repository").getString("html_url");

                //Constructs the payload
                PayLoad p = new PayLoad(id, ref, date, name, mail,  url);
                ph.queue.add(p);
                System.out.println(p);
            } catch (JSONException e) {
                System.out.println("error");
                e.printStackTrace();
            }


        } else if(request.getMethod() == "GET"){
            System.out.println("GET");
        }
        response.getWriter().println("CI job done");
    }

    public void setPayLoadHandler() throws Exception{
		//creates the payloadhandler which creates a queue for all payloads to be handled
        ph = new PayLoadHandler();
    }
	
	/*
	* Function that runs both the maven compile command
	*
	* @param path			a String representation of the path for where to run maven
	* @return MavenResult 	a MavenResult instance that holds the results of running maven
	*/
	public static MavenResult mavenCompile(String mavenPath) throws IOException{
		MavenResult temp;
		
		String timePattern = ".+Total time:.+";
		String datePattern = ".+Finished at:.+";
		String message = "";
		String details = "";
		
		try{
			// The path to the location of the maven project
			String path = mavenPath;
			
			// Run the maven compile command and store output
            ArrayList<String> output = runCommand("cmd.exe /c mvn -f " + path + "pom.xml compile");

			//Store exit status
			int exitStatus = Integer.parseInt(output.get(output.size() - 1));
			
			// Itterate over the output
			for (int i = 0; i < output.size(); i++){
				// Store the current line as a string
				String line = output.get(i).toString();
				
				// check for the lines holding the runtime and date of compilation
				if(line.matches(timePattern) || line.matches(datePattern)){
					message = message + " || " + line.substring(7);
				}
	
			}

			// Create a MavenResult instance depending on the results of running maven compile
			if(exitStatus == 0){
				temp = new MavenResult(true, "Project compiled successfully" + message);
				temp.setDetails(details);
				return temp;
			}
			if(exitStatus == 1){
				temp = new MavenResult(false, "Project failed to compile due to an compilation error" + message);
				temp.setDetails(details);
				return temp;
			}

        }
        catch (IOException e) { 
            System.err.println(e); 
        }
		
		// Something has gone wrong
		temp = new MavenResult(false, "ERROR");
		return temp;
	}
	
	/*
	* Function that runs both the maven test command
	*
	* @param path			a String representation of the path for where to run maven
	* @return MavenResult 	a MavenResult instance that holds the results of running maven
	*/
	public static MavenResult mavenTest(String mavenPath) throws IOException{
		MavenResult temp;
		
		String timePattern = ".+Total time:.+";
		String datePattern = ".+Finished at:.+";
		String testPattern = ".+Running.+";
		String failPattern = ".ERROR. Failures:.+";
		String message = "";
		String details = "";
		
		try{
			// The path to the location of the maven project
			String path = mavenPath;
			
            // Run the maven test command and store output
            ArrayList<String> output = runCommand("cmd.exe /c mvn -f " + path + "pom.xml test");
			
			//Store exit status
			int exitStatus = Integer.parseInt(output.get(output.size() - 1).toString());
			
			// Itterate over the output
			for (int i = 0; i < output.size(); i++){
				// Store the current line as a string
				String line = output.get(i).toString();
				
				// check for the lines holding the runtime and date of compilation
				if(line.matches(timePattern) || line.matches(datePattern)){
					message = message + " || " + line.substring(7);
				}
				
				if(line.matches(testPattern) || line.matches(failPattern)){
					// Store next line
					String line2 = output.get(i + 1).toString();
					
					// Add test message to details
					details = details + System.lineSeparator() + line + System.lineSeparator() + line2;
				}
	
			}
			
			// Create a MavenResult instance depending on the results of running maven test
			if(exitStatus == 0){
				temp = new MavenResult(true, "All tests have passed been cleared successfully" + message);
				temp.setDetails(details);
				return temp;
			}
			if(exitStatus == 1){
				temp = new MavenResult(false, "At least one test has failed" + message);
				temp.setDetails(details);
				return temp;
			}
        }
            catch (IOException e) { 
                System.err.println(e); 
            }
			
		// Something has gone wrong
		temp = new MavenResult(false, "ERROR");
		return temp;
	}

	/*
	* Function that runs a command determined by the given parameter
	*
	* @param cmd		a String representation of the command to run
	* @return ArrayList	an ArrayList representing the resulting output of running the command
	*/
	static public ArrayList<String> runCommand(String cmd) throws IOException{
        // Create a list for storing output.
        ArrayList<String> output = new ArrayList<>();
        // Execute a command and get its process handle
        Process proc = Runtime.getRuntime().exec(cmd);
        // Get the handle for the processes InputStream
        InputStream input = proc.getInputStream();
        // Create a BufferedReader and set it to read from from inputstream
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String temp;
		// As long as the current next line in BufferedReader is not null
		// Add to output list
        while ((temp = br.readLine()) != null) 
            output.add(temp);
            // Wait for process to terminate and catch any Exceptions.
            try { 
                proc.waitFor(); 
                }
            catch (InterruptedException e) {
                System.err.println("Process was interrupted"); 
                }
			// Add the exit value to the ArrayList
			output.add(String.valueOf(proc.exitValue()));
            br.close();
            return output;
	}
	
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8022);
        ContinuousIntegrationServer ci = new ContinuousIntegrationServer();
        ci.setPayLoadHandler();
        server.setHandler(ci);
        server.start();
        ci.ph.processQueue();
        server.join();
    }

}
