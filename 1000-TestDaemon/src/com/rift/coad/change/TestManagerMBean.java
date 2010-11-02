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
 * TestManagerMBean.java
 */

// package path
package com.rift.coad.change;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;

/**
 * This object is responsible for defining the tests for the change manager.
 *
 * @author brett chaldecott
 */
public interface TestManagerMBean extends Remote {
    /**
     * This method returns the version information for the type manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of type manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this type manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of type manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this type manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the type manager.
     *
     * @return The string containing the description of the type manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of type manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this type manager implementation.")
    public String getDescription() throws RemoteException;


    /**
     * This executes the tests.
     *
     * @return The string containing the test result.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method executes the test.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this type manager implementation.")
    public String executeTests() throws TestException, RemoteException;
}
