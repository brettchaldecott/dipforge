/*
 * HsqlDbEngineDaemon: The hsql db engine daemon
 * Copyright (C) 2010  Rift IT Contracting
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
 * HsqlDBEngineImpl.java
 *
 * The implementation of the HsqlDBEngine daemon.
 *
 * $Revision: 1.1.2.1 $
 */
package com.rift.coad.daemon.hsqldb;

// hsqldb imports
import com.rift.coad.lib.configuration.Configuration;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import org.hsqldb.Server;
import org.hsqldb.server.ServerConstants;
import org.hsqldb.DatabaseManager;
import org.hsqldb.persist.HsqlProperties;

/**
 * This object wrap the servers.
 *
 * @author brett chaldecott
 */
public class HsqlServerWrapper {

    // class constants
    private final static int BASE_INDEX = 0;
    private final static String DB_HOSTNAME = "db_hostname";
    private final static String DB_PORT = "db_port";
    private final static String DB_PATH = "db_path";
    private final static String DB_BASE = "db_base";
    private final static String DB_SILENT = "db_silent";
    private final static boolean DEFAULT_DB_SILENT = true;
    private final static String DB_TRACE = "db_trace";
    private final static boolean DEFAULT_DB_TRACE = false;
    private final static String DB_TLS = "db_tls";
    private final static boolean DEFAULT_DB_TLS = false;
    // private member variables
    private String hostname;
    private String base;
    private long startPort;
    private int count = 0;
    private HsqlProperties properties = new HsqlProperties();
    private Server server;
    private boolean silent;
    private boolean trace;
    private boolean tls;

    /**
     * This constructor takes the base and starting port
     *
     * @param base The base.
     * @param startPort The start port.
     */
    public HsqlServerWrapper(Configuration config) throws Exception {
        this.hostname = config.getString(
                DB_HOSTNAME, InetAddress.getLocalHost().
                getCanonicalHostName());

        this.startPort = config.getLong(
                DB_PORT, ServerConstants.SC_DEFAULT_HSQL_SERVER_PORT);
        this.base = config.getString(DB_BASE);

        this.silent = config.getBoolean(DB_SILENT, DEFAULT_DB_SILENT);
        this.trace = config.getBoolean(DB_TRACE, DEFAULT_DB_TRACE);
        this.tls = config.getBoolean(DB_TLS, DEFAULT_DB_TLS);

        // setup the initial properties
        this.setupServer();


    }

    /**
     * This method is responsible for adding the servers.
     *
     * @param name The name of the database.
     * @exception HsqlDBEngineException
     */
    public void addDB(String name) throws HsqlDBEngineException {
        try {
            //properties.setProperty(ServerConstants.SC_KEY_DATABASE + "." + count,
            //        path.toURI().toURL().toString() + ";sql.enforce_strict_size=true;");
            //properties.setProperty(ServerConstants.SC_KEY_DBNAME + "." + count,
            //        name);
            String[] arguments = name.split("=");
            if (arguments.length == 2 && arguments[1].equals("backwards")) {
                File path = new File(base, arguments[0]);
                server.setDatabasePath(count, path.toURI().toURL().toString() +
                        ";sql.enforce_strict_size=false;sql.enforce_size=false;");
                server.setDatabaseName(count, arguments[0]);
            } else {
                File path = new File(base, arguments[0]);
                server.setDatabasePath(count, path.toURI().toURL().toString());
                server.setDatabaseName(count, arguments[0]);
            }
            count++;
        } catch (Exception ex) {
            throw new HsqlDBEngineException("Failed to add the db because : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * start the server.
     */
    public void start() {
        try {
            server.setProperties(properties);
            server.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method stops the server
     */
    public void stop() {
        server.shutdown();
    }

    /**
     * This method returns the data name.
     *
     * @param index The index position.
     * @param asconfigured if this is configured.
     * @return The name of the database.
     * @throws com.rift.coad.daemon.hsqldb.HsqlDBEngineException
     */
    public String getDatabaseName(int index, boolean asconfigured) throws HsqlDBEngineException {
        int dbIndex = index;
        return server.getDatabaseName(dbIndex, trace);
    }

    /**
     * This method returns the path.
     *
     * @param index The index number.
     * @param asconfigured TRUE or FALSE.
     * @return The path to the database.
     * @throws com.rift.coad.daemon.hsqldb.HsqlDBEngineException
     */
    public String getDatabasePath(int index, boolean asconfigured) throws HsqlDBEngineException {
        int dbIndex = index;
        return server.getDatabasePath(dbIndex, trace);
    }

    /**
     * This method returns the database type.
     *
     * @param index The index of the database.
     * @return The string containing the database type.
     */
    public String getDatabaseType(int index) {
        int dbIndex = index;
        return server.getDatabaseType(dbIndex);
    }

    /**
     * This method is called to a new server and properties
     */
    private void setupServer() throws HsqlDBEngineException {
        try {
            server = new Server();
            properties = new HsqlProperties();
            properties.setProperty("server.address", this.hostname);
            properties.setProperty("server.port", new Long(this.startPort).toString());
            properties.setProperty("sql.enforce_strict_size", false);
            properties.setProperty("sql.enforce_size", false);
            server.setSilent(this.silent);
            server.setTrace(this.trace);
            server.setTls(this.tls);
            server.setNoSystemExit(true);
            server.setRestartOnShutdown(true);
            server.setDaemon(true);

        } catch (Exception ex) {
            throw new HsqlDBEngineException("Failed to instanciate a server : " + ex.getMessage(), ex);
        }
    }
}
