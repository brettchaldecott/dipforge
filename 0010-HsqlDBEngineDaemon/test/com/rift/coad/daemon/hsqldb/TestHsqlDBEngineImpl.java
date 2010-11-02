/*
 * CoadunationLib: The hsql db engine daemon test.
 * Copyright (C) 2006  Rift IT Contracting
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
 * TestHsqlDBEngineImpl.java
 *
 * JUnit based test
 */

// package path
package com.rift.coad.daemon.hsqldb;

// java imports
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.sql.DriverManager;
import java.sql.Connection;

// junit imports
import junit.framework.*;

/**
 * This is the test client for the hsql db engine.
 *
 * @author Brett Chaldecott
 */
public class TestHsqlDBEngineImpl extends TestCase {
    
    /**
     * The runner thread
     */
    public class DBRunner extends Thread {
        // private member variable
        private HsqlDBEngine instance = null;
        
        /**
         * This method is called to construct the thread.
         */
        public DBRunner(HsqlDBEngine instance) {
            this.instance = instance;
        }
        
        
        /**
         * This method is called to run the database
         */
        public void run() {
            instance.process();
        }
    }
    
    public TestHsqlDBEngineImpl(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    /**
     * This method tests the HsqlDB rmi interface
     */
    public void testHSQLDBEngine() throws Exception {
        HsqlDBEngine instance = new HsqlDBEngine();
        DBRunner runner = new DBRunner(instance);
        runner.start();
        
        System.out.println("Name : " + instance.getDatabaseName(0,true));
        System.out.println("Path : " + instance.getDatabasePath(0,true));
        
        Class.forName("org.hsqldb.jdbcDriver");
        
        Connection connection = DriverManager.getConnection(
                "jdbc:hsqldb:hsql://localhost/coadunation","sa","");
        connection.close();
        instance.terminate();
        
        try {
            connection = DriverManager.getConnection(
                "jdbc:hsqldb:hsql://localhost/coadunation","sa","");
            fail("Could still make a connection to the database");
        } catch (java.sql.SQLException ex) {
            // ignore
        }
    }

}
