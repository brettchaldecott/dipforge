/*
 * TestDaemons: This implements the test daemons.
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
 * TestManager.java
 */

package com.rift.coad.change;

import com.rift.coad.test.TestRunner;
import java.rmi.RemoteException;

/**
 * This object is responsible for testing the change manager
 *
 * @author brett chaldecott
 */
public class TestManager implements TestManagerMBean {

    /**
     * The default constructor
     */
    public TestManager() {
    }
    
    
    /**
     * This method returns the version information for this test object.
     * 
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    public String getVersion() throws RemoteException {
        return "1.0";
    }


    /**
     * This method returns the string for this test manager.
     *
     * @return The string identifying this test manager
     * @throws java.rmi.RemoteException
     */
    public String getName() throws RemoteException {
        return this.getClass().getName();
    }

    
    /**
     * This method returns a description of the test manager.
     *
     * @return A string containing a description of the test manager.
     * @throws java.rmi.RemoteException
     */
    public String getDescription() throws RemoteException {
        return "The change manager test";
    }


    /**
     * This method is responsible for executing the test.
     *
     * @return The string containing the result of the test.
     * @throws com.rift.coad.change.TestException
     * @throws java.rmi.RemoteException
     */
    public String executeTests() throws TestException, RemoteException {
        return TestRunner.execute(ChangeTest.class);
    }

}
