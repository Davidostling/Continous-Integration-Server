package group22.ci;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for testing the validation of compilation and tests
 */
public class BuildTest 
{
    /**
     * Tests a project without problems
     * Should compile and pass its tests
     */
    @Test
    public void successful_build()
    {
        String path = "mvn_test_projects/successful/demo";
        boolean comp_result = ContinuousIntegrationServer.mavenCompile(path).result;
        assertTrue(comp_result);
        boolean test_result = ContinuousIntegrationServer.mavenTest(path);
        assertTrue(test_result);
    }

    /**
     * Tests a project with compilation error
     * Should fail to compile
     */
    @Test
    public void fail_compilation()
    {
        String path = "mvn_test_projects/failcompile/demo";
        boolean comp_result = ContinuousIntegrationServer.mavenCompile(path).result;
        assertFalse(comp_result);
    }

    /**
     * Tests a project with a failing test
     * Should not pass all its tests
     */
    @Test
    public void fail_tests()
    {
        String path = "mvn_test_projects/failTest/demo";
        boolean test_result = ContinuousIntegrationServer.mavenTest(path);
        assertFalse(test_result);
    }
}
