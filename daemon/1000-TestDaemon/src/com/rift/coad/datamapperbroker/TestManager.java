/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.datamapperbroker;

import com.rift.coad.test.TestRunner;

/**
 * The implementation of the test runner.
 *
 * @author brett chaldecott
 */
public class TestManager implements TestManagerMBean {

    public TestManager() {
    }


    /**
     * This method returns the string containing the version information.
     *
     * @return This method returns the version information
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the the name of the test manager.
     *
     * @return This method returns the class name.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns the description
     *
     * @return The string containing the description of this object.
     */
    public String getDescription() {
        return "This is the implementation of the test manager.";
    }


    /**
     * This method executes the test
     *
     * @return The string containing the test results.
     * @throws com.rift.coad.datamapperbroker.TestException
     */
    public String executeTests() throws TestException {
        return TestRunner.execute(DataMapperTest.class);
    }

}
