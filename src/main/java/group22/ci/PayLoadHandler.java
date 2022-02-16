package group22.ci;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A class to handle all incoming payloads
 */
public class PayLoadHandler {
    ConcurrentLinkedQueue<PayLoad> queue;

    public PayLoadHandler() throws Exception {
        //creates the queue for all payloads
        queue = new ConcurrentLinkedQueue<PayLoad>();
    }

    /**
     * Function the processes the queue with all payloads
     * @throws Exception
     */
    public void processQueue() throws Exception {
        try {
            while (true) {
                // gets next payload
                PayLoad p = queue.poll();
                // sleep if the payload was empty
                if (p == null) {
                    Thread.sleep(1500);
                } else {
                    // prepare a new folder for the cloned repository
                    String path = "repo";
                    // delete old directory with past build
                    FileUtils.deleteDirectory(new File(path));
                    // then clone
                    System.out.println("Cloning from " + p.url + " to " + path);
                    Git repo = Git.cloneRepository().setURI(p.url)
                            .setBranchesToClone(Arrays.asList(p.ref))
                            .setBranch(p.ref)
                            .setDirectory(new File(path))
                            .call();
                    repo.close();
                    String compileRes = ContinuousIntegrationServer.mavenCompile(path + "/").getMessage();
                    MavenResult testRes = ContinuousIntegrationServer.mavenTest(path + "/");
                    System.out.println("==========");
                    System.out.println(compileRes);
                    System.out.println(testRes.message);
                    System.out.println("==========");

                    // mail the result
                    EmailNotification.SendNotification(p.mail, compileRes, testRes.message, testRes.details, p.ref.substring(11));

                    // store the build
                    Storage.addToStorage(p, compileRes, testRes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
