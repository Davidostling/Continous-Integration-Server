package group22.ci;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

public class PayLoadHandler {
    ConcurrentLinkedQueue<PayLoad> queue;

    public PayLoadHandler() throws Exception {
        queue = new ConcurrentLinkedQueue<PayLoad>();
    }

    public void processQueue() throws Exception {
        try {
            // something needs to be able to stop this loop
            while (true) {
                PayLoad p = queue.poll();
                if (p == null) {
                    Thread.sleep(1500);
                } else {
                    // prepare a new folder for the cloned repository
                    String path = "repo";
                    FileUtils.deleteDirectory(new File(path));
                    // then clone
                    System.out.println("Cloning from " + p.url + " to " + path);
                    Git repo = Git.cloneRepository().setURI(p.url)
                            .setBranchesToClone(Arrays.asList(p.ref))
                            .setBranch(p.ref)
                            .setDirectory(new File(path))
                            .call();
                    repo.close();
                    String compileRes = ContinuousIntegrationServer.mavenCompile(path + "/").message;
                    MavenResult testRes = ContinuousIntegrationServer.mavenTest(path + "/");
                    System.out.println("==========");
                    System.out.println(compileRes);
                    System.out.println(testRes.message);
                    System.out.println("==========");

                    // mail the result
                    EmailNotification.SendNotification(p.mail, compileRes, testRes.message, testRes.details, p.ref.substring(11));

                    // store the build
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
