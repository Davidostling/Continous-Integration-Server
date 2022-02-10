package group22.ci;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.stream.Collectors;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.json.*;

/**
 * Skeleton of a ContinuousIntegrationServer which acts as webhook
 * See the Jetty documentation for API documentation of those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {
    @Override
    public void handle(String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);
        if (request.getMethod() == "POST") {
            System.out.println("POST");
            try {
                String received = request.getReader().lines().collect(Collectors.joining());
                JSONObject dataJSON = new JSONObject(received);
                String ref = dataJSON.getString("ref");
                JSONArray commmitarray = dataJSON.getJSONArray("commits");
                JSONObject data = commmitarray.getJSONObject(0);
                String id = data.getString("id");
                String date = data.getString("timestamp");
                String name = data.getJSONObject("author").getString("name");
                String mail = data.getJSONObject("author").getString("email");
                String url = dataJSON.getJSONObject("repository").getString("html_url");

                //Constructs the payload
                PayLoad p = new PayLoad(id, ref, date, name, mail,  url);


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
	
	/*
	* Function that runs both the maven compile command
	*
	* @param path			a String representation of the path for where to run maven
	* @return MavenResult 	a MavenResult instance that holds the results of running maven
	*/
	public static MavenResult mavenCompile(String mavenPath) throws IOException{
		MavenResult temp;
		
		try{
			// The path to the location of the maven project
			String path = mavenPath;
			
			
			// Run the maven compile command and store output
            ArrayList output = runCommand("cmd.exe /c mvn -f " + path + "pom.xml compile");
			
			//Store exit status
			int exitStatus = Integer.parseInt(output.get(output.size() - 1).toString());
			
			// Create a MavenResult instance depending on the results of running maven compile
			if(exitStatus == 0){
				temp = new MavenResult(true, "Project compiled successfully");
				return temp;
			}
			if(exitStatus == 1){
				temp = new MavenResult(false, "Project failed to compile due to an compilation error");
				return temp;
			}
			
			for (int i = 0; i < output.size(); i++){
                        System.out.println(output.get(i));
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
		
		try{
			// The path to the location of the maven project
			String path = mavenPath;
			
			
            // Run the maven test command and store output
            ArrayList output = runCommand("cmd.exe /c mvn -f " + path + "pom.xml test");
			
			//Store exit status
			int exitStatus = Integer.parseInt(output.get(output.size() - 1).toString());
			
			// Create a MavenResult instance depending on the results of running maven test
			if(exitStatus == 0){
				temp = new MavenResult(true, "All tests have passed been cleared successfully");
				return temp;
			}
			if(exitStatus == 1){
				temp = new MavenResult(false, "At least one test has failed");
				return temp;
			}
			
			
			for (int i = 0; i < output.size(); i++){
                        System.out.println(output.get(i));
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
	static public String[] runCommand(String cmd) throws IOException{
		
        // Create a list for storing output.
        ArrayList output = new ArrayList();
		
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
			output.add(proc.exitValue());
				
            br.close();
		
            return output;
	}
	
	

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8022);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }

}
