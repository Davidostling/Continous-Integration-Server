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
		// 3rd run maven();

        response.getWriter().println("CI job done");
    }
	
	/*
	* Function that runs the maven test command
	*/
	public static void maven() throws IOException{
		try{
            // Run the maven test command and store output
            String output[] = runCommand("cmd.exe /c mvn test");
			
            // Print the output to screen.
			for (int i = 0; i < output.length; i++){
                System.out.println(output[i]);
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
	* @return String	a String representing the resulting output of running the command
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
				
			// Print the exit value
			System.out.println(proc.exitValue());
				
            br.close();
			
            // Convert the list to a String and return
		
            return (String[])output.toArray(new String[0]);
	}
	
	

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }

}
