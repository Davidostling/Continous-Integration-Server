package group22.ci;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jgit.api.Git;

public class PayLoadHandler {
    ConcurrentLinkedQueue<PayLoad> queue;

    public PayLoadHandler() throws Exception{
        queue = new ConcurrentLinkedQueue<PayLoad>();
        processQueue();
    }
    public void processQueue() throws Exception{
        try {
            //something needs to be able to stop this loop
            while(true){
                PayLoad p = queue.poll();
                if(p == null){
                    Thread.sleep(1500);
                } else {
                    // prepare a new folder for the cloned repository
                    File localPath = File.createTempFile("repo", "");
                    if(!localPath.delete()) {
                      throw new IOException("Could not delete temporary file " + localPath);
                    }
                    // then clone
                    System.out.println("Cloning from " + p.url + " to " + localPath);
                    Git repo = Git.cloneRepository().setURI(p.url)
                        .setBranchesToClone(Arrays.asList(p.ref))
                        .setBranch(p.ref)
                        .setDirectory(localPath)
                        .call();
                    repo.close();
                    String compileRes = ContinuousIntegrationServer.mavenCompile(localPath).message;
                    String testRes = ContinuousIntegrationServer.mavenTest(localPath).message;

                    //mail the result
                    //store the build
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
