package org.stark.coveragequicklink;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Simple test class for OpenInCoverageReportAction
 */
public class OpenInCoverageReportActionTest {

    @Test
    public void testActionCreation() {
        // Create a test instance of OpenInCoverageReportAction
        OpenInCoverageReportAction action = new OpenInCoverageReportAction();

        // Verify that the action is not null
        assertNotNull("Action should be created successfully", action);

        // For more comprehensive tests, we would need to mock IntelliJ platform APIs
        // This would require a testing framework like IntelliJ Platform Test Framework
        // For demonstration purposes, this simple test is sufficient
    }
}
