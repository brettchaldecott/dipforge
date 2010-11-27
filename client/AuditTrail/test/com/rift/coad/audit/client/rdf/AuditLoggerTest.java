/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.audit.client.rdf;

import com.rift.coad.rdf.objmapping.base.Name;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class AuditLoggerTest extends TestCase {
    
    public AuditLoggerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getLogger method, of class AuditLogger.
     */
    public void testLogger() {
        System.out.println("testLogger");
        AuditLogger result = AuditLogger.getLogger("test");
        result.create("fred %s %s", "test1","test2").addData(new Name("fred")).
                setCorrelationId("test1").setExternalId("test2").complete();
    }

}
