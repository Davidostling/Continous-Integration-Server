package group22.ci;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

/**
 * Unit tests for testing the validation of compilation and tests
 */
public class BuildTest 
{
    /**
     * Tests a project without problems
     * Should compile and pass its tests
     * @throws IOException
     */
    @Test
    public void successful_build() throws IOException
    {
        String path = "mvn_test_projects/successful/demo";
        boolean comp_result = ContinuousIntegrationServer.mavenCompile(path).result;
        assertTrue(comp_result);
        boolean test_result = ContinuousIntegrationServer.mavenTest(path).result;
        assertTrue(test_result);
    }

    /**
     * Tests a project with compilation error
     * Should fail to compile
     * @throws IOException
     */
    @Test
    public void fail_compilation() throws IOException
    {
        String path = "mvn_test_projects/failcompile/demo";
        boolean comp_result = ContinuousIntegrationServer.mavenCompile(path).result;
        assertFalse(comp_result);
    }

    /**
     * Tests a project with a failing test
     * Should not pass all its tests
     * @throws IOException
     */
    @Test
    public void fail_tests() throws IOException
    {
        String path = "mvn_test_projects/failTest/demo";
        boolean test_result = ContinuousIntegrationServer.mavenTest(path).result;
        assertFalse(test_result);
    }
}
