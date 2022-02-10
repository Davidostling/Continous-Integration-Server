package group22.ci;

import java.util.concurrent.ConcurrentLinkedQueue;

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
                    //clone repo
                    //run mvn compile
                    //run mvn test
                    //mail the result
                    //store the build
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
