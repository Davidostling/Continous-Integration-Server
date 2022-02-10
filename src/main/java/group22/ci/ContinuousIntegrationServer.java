package group22.ci;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
		// 3rd run maven(PathTOMavenProject);

        response.getWriter().println("CI job done");
    }
	
	/*
	* Function that runs both the maven compile and test commands
	*
	* @param path		a String representation of the path for where to run maven
	*/
	public static void maven(String mavenPath) throws IOException{
		try{
			// The path to the location of the maven project
			String path = mavenPath;
			
			
			// Run the maven compile command and store output
            ArrayList output = runCommand("cmd.exe /c mvn -f " + path + "pom.xml compile");
			
			// Print exit status
			System.out.println("Exit status compile: " + output.get(output.size() - 1));
			
			
            // Run the maven test command and store output
            output = runCommand("cmd.exe /c mvn -f " + path + "pom.xml test");
			
			// Print exit status
			System.out.println("Exit status test: " + output.get(output.size() - 1));
			
            // Print the test results
			if(output.size() > 9){
				System.out.println(output.get(output.size() - 9));
			}
			
        }   
            catch (IOException e) { 
                System.err.println(e); 
            }
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
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }

}
