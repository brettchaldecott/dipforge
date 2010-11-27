/*
 * CoadunationTest: The test class for the test runner.
 * Copyright (C) 2009  Rift IT Contracting
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * TestRunner.java
 */

// package path
package com.rift.coad.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * The implementation of a simple test runner
 *
 * @author brett chaldecott
 */
public class TestRunner {

    /**
     * This static method is called to execute the tests and return the results
     * as a string.
     * @param tests The tests to execute
     * @return The string containing the result
     */
    public static String execute(Class ... tests) {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(tests);
        if (result.wasSuccessful()) {
            return "SUCCESS";
        }
        StringBuffer resultStr = new StringBuffer();
        for (Failure failure : result.getFailures()) {
            resultStr.append(failure.getTestHeader()).append("\n");
            if (failure.getException() != null) {
                resultStr.append(failure.getMessage()).append("\n").
                        append(failure.getTrace()).append("\n");
            }
        }
        return resultStr.toString();
    }
}
