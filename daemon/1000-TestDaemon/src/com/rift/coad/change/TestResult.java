/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.change;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The beginnings of the test program for the results.
 *
 * @author brett chaldecott
 */
public class TestResult {

    // class singletons
    private static TestResult singleton = null;

    // class member variables
    private Set<String> tests = new HashSet<String>();
    private int result = 0;

    /**
     * The default constructor
     */
    private TestResult() {
    }


    /**
     * This method returns an instance of the test result object.
     *
     * @return An instance of the test result object.
     */
    public synchronized static TestResult getInstance() {
        if (singleton == null) {
            singleton = new TestResult();
        }
        return singleton;
    }


    /**
     * This metho resets the test results.
     */
    public void reset() {
        result = 0;
        tests.clear();
    }


    /**
     * This method notifies thes test result listener
     */
    public synchronized void complete(String testName) {
        result++;
        tests.add(testName);
        notify();
    }


    /**
     * This method is called to wait for timeout.
     *
     * @param timeout The time out.
     */
    public synchronized int waitForComplete(long timeout, int expectedResult, int numberTestObj) throws Exception {
        long currentTime = new Date().getTime();
        long startTime = currentTime;

        while (result < expectedResult) {
            long sleepTime = timeout - (currentTime - startTime);
            if (sleepTime <= 0) {
                return result;
            }
            wait(timeout - (currentTime - startTime));
            currentTime = new Date().getTime();
        }
        if (this.tests.size() != numberTestObj) {
            throw new Exception("Excpected [" + numberTestObj + "] but got [" +
                    tests.size() + "] : " + this.tests.toString());
        }
        return result;
    }
}
