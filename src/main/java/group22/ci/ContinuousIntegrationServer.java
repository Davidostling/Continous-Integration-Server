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

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
        
        response.getWriter().println("CI job done");


    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8022);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }

}
