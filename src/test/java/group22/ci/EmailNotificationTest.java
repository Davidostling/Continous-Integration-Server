package group22.ci;

import static org.junit.Assert.*;
import org.junit.Test;

import javax.mail.MessagingException;

/**
 * Unit tests for testing the notification
 */
public class EmailNotificationTest {
    /**
     * Tests a notification without problem
     * @throws MessagingException
     */
    @Test
    public void successful_notify() throws MessagingException
    {
        boolean test_result = EmailNotification.SendNotification("zehao323@gmail.com", "test1", "test2",
                "test3", "testbranch");
        assertTrue(test_result);
    }

    /**
     * Tests a notification with content problem
     * @throws MessagingException
     */
    @Test
    public void fail_notify() throws MessagingException
    {
        boolean test_result = EmailNotification.SendNotification("@gmail.com", "test1", "test2",
                "test3", "testbranch");
        assertFalse(test_result);
    }
}