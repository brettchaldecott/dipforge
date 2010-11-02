/*
 * CoadunationLib: The coaduntion implementation library.
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
 * XMLConfigurationException.java
 *
 * DBSourceManager.java
 *
 * This object is responsible for managing the database sources used by the
 * coadunation environments.
 */

// package path
package com.rift.coad.lib.db;

// java import
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import java.util.HashMap;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.naming.ContextManager;
import com.rift.coad.lib.loader.MasterClassLoader;


/**
 * This object is responsible for managing the database sources used by the
 * coadunation environments.
 *
 * @author Brett Chaldecott
 */
public class DBSourceManager {
    
    // class constants
    private final static String DB_SOURCES = "db_sources";
    private final static String DB_SOURCE = "db_source";
    private final static String DB_CONTEXT = "java:comp/env/jdbc";
    private final static String DB_CONTEXT_ID = "db_context";
    
    // the singleton reference.
    private static DBSourceManager singleton = null;
    
    // member variables
    protected Logger log =
        Logger.getLogger(DBSourceManager.class.getName());
    private ContextManager contextManager = null;
    private String jndiBase = null;
    
    /**
     * Creates a new instance of DBSourceManager
     *
     * @exception DBException
     */
    private DBSourceManager() throws DBException {
        try {
            Configuration sourceConfig = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            // init the context
            try {
                jndiBase = sourceConfig.getString(
                        DB_CONTEXT);
                contextManager = new ContextManager(jndiBase);
            } catch (Exception ex) {
                jndiBase = DB_CONTEXT;
                contextManager = new ContextManager(jndiBase);
            }
            
            StringTokenizer dbSources = new 
                    StringTokenizer(sourceConfig.getString(DB_SOURCES),",");
            while(dbSources.hasMoreTokens()) {
                String dbSource = dbSources.nextToken().trim();
                try {
                    Configuration dbSourceConfig = ConfigurationFactory.getInstance().
                            getConfig(Class.forName(dbSource));
                    Set keys = dbSourceConfig.getKeys();
                    for (Iterator iter = keys.iterator(); iter.hasNext();) {
                        Reference ref = new Reference("javax.sql.DataSource",
                                "org.objectweb.jndi.DataSourceFactory",
                                null);
                        ref.add(new StringRefAddr("driverClassName",dbSource));
                        String key = (String)iter.next();
                        if (!key.contains(DB_SOURCE)) {
                            continue;
                        }
                        log.debug("Instanciate db connection for : " 
                                + key);
                        String[] parameters = dbSourceConfig.getString(key).
                                split("[,]");
                        String jndi = null;
                        for (int index = 0; index < parameters.length; index++){
                            log.debug("Handle parameter : " + 
                                    parameters[index].trim());
                            String value = parameters[index].trim();
                            if (!value.contains("=")) {
                                log.error("Invalid parameter in db config :" + value);
                                continue;
                            }
                            String[] parameter = value.split("[=]");
                            String parameterKey = parameter[0].trim();
                            String parameterValue = "";
                            if (parameter.length > 1) {
                                parameterValue = parameter[1].trim();
                            } 
                            
                            if (parameterKey.equalsIgnoreCase("jndi")) {
                                jndi = parameterValue;
                            } else {
                                ref.add(new StringRefAddr(parameterKey,
                                        parameterValue));
                            }
                            
                        }
                        if (jndi == null) {
                            log.error("JNDI not configured for : " + key);
                        } else {
                            contextManager.bind(jndi,ref);
                        }
                    }
                    
                } catch (Exception ex) {
                    log.error("Failed to instanciate the datase source for ["  +
                            dbSource + "] because : " + ex.getMessage(), ex);
                }
            }
        } catch (Exception ex) {
            throw new DBException(
                    "Failed to instanciate the db source manager : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method initializes the database source manager.
     */
    public static synchronized void init() throws DBException {
        if (singleton == null) {
            singleton = new DBSourceManager();
        }
    }
    
    
    /**
     * This method returns an instance of the database source manager.
     *
     * @return The instance of the database source manager.
     * @exception DBException
     */
    public static synchronized DBSourceManager getInstance() throws DBException {
        if (singleton == null) {
            throw new DBException(
                    "The Database Source manager has not been initialized.");
        }
        return singleton;
    }
    
    
    /**
     * This method adds a new database source. This method must not be
     * used by any Daemon instances!!!!!
     *
     * @param driverPath The path to the driver library.
     * @param driverClass The driver class to load.
     * @param jndi The name to register this jdbc connection with the jndi.
     * @param env The environment for the jdbc connetion.
     * @exception DBException
     */
    public void addDBSource(String driverPath, String driverClass, String jndi, 
            HashMap env) throws DBException {
        try {
            MasterClassLoader.getInstance().addLib(driverPath);
            Class.forName(driverClass);
            Reference ref = new Reference("javax.sql.DataSource",
                    "org.apache.commons.dbcp.BasicDatabSourceFactory",
                    null);
            ref.add(new StringRefAddr("driverClassName",driverClass));
            Set keys = env.keySet();
            for (Iterator iter = keys.iterator(); iter.hasNext();) {
                String key = (String)iter.next();
                if (key.equals(DB_SOURCE)) {
                    continue;
                }
                ref.add(new StringRefAddr(key,(String)env.get(key)));
            }
            String jndiRef = jndi;
            if (jndiRef.contains(jndiBase)) {
                jndiRef = jndiRef.substring(jndiRef.indexOf(jndiBase) + 
                        jndiBase.length());
            }
            contextManager.bind(jndiRef,ref);
        } catch (Exception ex) {
            throw new DBException("Failed to add a data source because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds the database source.
     *
     * @param driverClass The driver class to load.
     * @param jndi The name to register this jdbc connection with the jndi.
     * @param env The environment for the jdbc connetion.
     * @exception DBException
     */
    public void addDBSource(String driverClass, String jndi, 
            HashMap env) throws DBException {
        try {
            Class.forName(driverClass);
            Reference ref = new Reference("javax.sql.DataSource",
                    "org.apache.commons.dbcp.BasicDatabSourceFactory",
                    null);
            ref.add(new StringRefAddr("driverClassName",driverClass));
            Set keys = env.keySet();
            for (Iterator iter = keys.iterator(); iter.hasNext();) {
                String key = (String)iter.next();
                if (key.equals(DB_SOURCE)) {
                    continue;
                }
                ref.add(new StringRefAddr(key,(String)env.get(key)));
            }
            String jndiRef = jndi;
            if (jndiRef.contains(jndiBase)) {
                jndiRef = jndiRef.substring(jndiRef.indexOf(jndiBase) + 
                        jndiBase.length());
            }
            contextManager.bind(jndiRef,ref);
        } catch (Exception ex) {
            throw new DBException("Failed to add a data source because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the database source from JNDI. It does not unload
     * the library that requires a restart.
     *
     * @param jndi The path to the jndi variable.
     * @exception DBException
     */
    public void removeDBSource(String jndi) throws DBException {
        try {
            String jndiRef = jndi;
            if (jndiRef.contains(jndiBase)) {
                jndiRef = jndiRef.substring(jndiRef.indexOf(jndiBase) + 
                        jndiBase.length());
            }
            contextManager.unbind(jndiRef);
        } catch (Exception ex) {
            throw new DBException("Failed to remove [" + jndi + "] because : " + 
                    ex.getMessage(),ex);
        }
    }
}
