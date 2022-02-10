package group22.ci;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class BuildTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void successful_build()
    {
        String path = "mvn_test_projects/successful/demo";
        ContinuousIntegrationServer.mavenCompile(path);
        ContinuousIntegrationServer.mavenTest(path);
        assertTrue( true );
    }

    @Test
    public void fail_compilation()
    {
        String path = "mvn_test_projects/failcompile/demo";
        ContinuousIntegrationServer.mavenCompile(path);
        ContinuousIntegrationServer.mavenTest(path);
        assertTrue( true );
    }

    @Test
    public void fail_tests()
    {
        String path = "mvn_test_projects/failTest/demo";
        ContinuousIntegrationServer.mavenCompile(path);
        ContinuousIntegrationServer.mavenTest(path);
        assertTrue( true );
    }
}
