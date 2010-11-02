/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  Rift IT Contracting
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
 * Main.java
 */

package com.rift.coad.commandline.jythondaemoncommandlinetool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import com.rift.coad.daemon.jython.JythonDaemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.util.PropertyResourceBundle;

// logging import
import org.apache.log4j.BasicConfigurator;


/**
 *
 * @author Glynn Chaldecott
 *
 * This class is a command line tool for the Jython daemon and this will give a
 * user basic Jython functionality through the command line.
 */
public class Main {
    public BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public String[] args = null;
    public String url = null;
    public String host = null;
    public String username = null;
    public String password = null;
    
    /** Creates a new instance of Main */
    public Main(String[] args) {
        this.args = args;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Main mainProgram = new Main(args);
        mainProgram.commandLine();
    }
    
    
    /**
     * This method breaks the arguments down and uses them to perform the
     * necessary task.
     */
    public void commandLine() {
        try {
            JythonDaemon sb = null;
            int choice = -1;
            String location = "";
            String name = "";
            String propertyFile = "";
            String jClass = "";
            String returnValue = "";
            byte[] fileBytes = null;
            HashMap arguments = new HashMap();
            for (int i = 0; i < args.length; i ++) {
                if (args[i].equals("-h")) {
                    choice = 2;
                    break;
                } else if ((choice == -1) && (args[i].equals("-e"))) {
                    choice = 1;
                    i++;
                    name = args[i];
                } else if ((choice == -1) && (args[i].equals("-r"))) {
                    choice = 0;
                } else if ((choice == -1 || choice == 0) && 
                        (args[i].equals("-l"))) {
                    i ++;
                    String spacer = "";
                    location = "";
                    boolean flag = true;
                    while (flag) {
                        location += spacer + args[i];
                        spacer = " ";
                        i ++;
                        if (i == args.length) {
                            flag = false;
                        } else if (args[i].charAt(0) == '-') {
                            flag = false;
                        }
                    }
                    i --;
                    File file = new File(location);
                    int size = (int)file.length();
                    FileInputStream fis = new FileInputStream(file);
                    byte[] bytes = new byte[size];
                    fis.read(bytes,0,size);
                    fileBytes = bytes;
                    name = file.getName();
                } else if ((choice == -1 || choice == 1) && 
                        (args[i].equals("-p"))) {
                    i ++;
                    String spacer = "";
                    propertyFile = "";
                    boolean flag = true;
                    while (flag) {
                        propertyFile += spacer + args[i];
                        spacer = " ";
                        i ++;
                        if (i == args.length) {
                            flag = false;
                        } else if (args[i].charAt(0) == '-') {
                            flag = false;
                        }
                    }
                    i --;
                    File file = new File(propertyFile);
                    FileInputStream fis = new FileInputStream(file);
                    PropertyResourceBundle prb = new
                            PropertyResourceBundle(fis);
                    arguments = new HashMap();
                    Enumeration keys = prb.getKeys();
                    while (keys.hasMoreElements()) {
                        String key = (String) keys.nextElement();
                        Object value = prb.handleGetObject(key);
                        arguments.put(key,value);
                    }
                } else if ((choice == -1 || choice == 1) && 
                        (args[i].equals("-t"))) {
                    i ++;
                    jClass = args[i];
                }  else if ((choice == -1 || choice == 1) && 
                        (args[i].equals("-v"))) {
                    i ++;
                    returnValue = args[i];
                } else if (args[i].equals("-s")) {
                    i ++;
                    host = args[i];
                    if (host.indexOf(":") == -1) {
                        host += ":2000";
                    }
                } else if (args[i].equals("-u")) {
                    i ++;
                    url = args[i];
                } else if (args[i].equals("-U")) {
                    i ++;
                    username = args[i];
                } else if (args[i].equals("-P")) {
                    i ++;
                    password = args[i];
                }
            }
            
            // validate the arguments
            if ((url == null) || (host == null) || (username == null) || 
                    (password == null))
            {
                System.out.println("The <-u jndi url> <-s host:port> " +
                        "<-U username> <-P password> must be supplied.");
                help();
                return;
            }
            
            switch (choice) {
                case 0:
                    if (!location.equals("") && !name.equals("")) {
                        sb = createConnection();
                        sb.registerScript(fileBytes,name);
                        System.out.println("The script has been " +
                                "registered.");
                    } else {
                        System.out.println("Must supply a location of a script " +
                                "when registering a script.");
                        help();
                    }
                    break;
                case 1:
                    if (!name.equals("") && !returnValue.equals("") && 
                            !jClass.equals("")) {
                        sb = createConnection();
                        Object returnedValue = null;
                        if (arguments.size() == 0) {
                            returnedValue = sb.runScript(name,returnValue,
                                    Class.forName(jClass));
                        } else {
                            returnedValue = sb.runScript(name,returnValue,
                                    Class.forName(jClass),arguments);
                        }
                        System.out.println(returnedValue.toString());
                    } else {
                        System.out.println("Must supply a " +
                                "<-v return variable> and <-t return type> .");
                        help();
                    }
                    break;
                case 2:
                default:
                    System.out.println("There was a problem with the " +
                            "statement");
                    help();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Command line failed because : " + 
                    ex.getMessage());
            help();
        }
    }
    
    public void help() {
        System.out.println("Help");
        System.out.println("\t-r \tRegister a script.");
        System.out.println("\t-e \tExecute a script with a given name.");
        System.out.println("\t-l \tLocation of script to register.");
        System.out.println("\t-p \tThis is to be followed by the full path of" +
                " a property file which supplies values for the script that " +
                "is to be run.");
        System.out.println("\t-t \tThis is a java class type for the result.");
        System.out.println("\t-v \tThis is to be followed by the name of a " +
                "variable that is to be returned from the script.");
        System.out.println("\t-s \tThis is to be followed by the host and " +
                "port on which the Jython Daemon is running and is " +
                "required.");
        System.out.println("\t-u \tThis is to be followed by the JNDI for " +
                "the Jython Daemon and is required.");
        System.out.println("\t-U \tThis must be followed by the username for " +
                "the connection and is required.");
        System.out.println("\t-P \tThis must be followed by the password for " +
                "the connection and is required.");
    }
    
    /**
     * This method connects to the Timer Daemon and then returns the Object.
     *
     * @return This method returns a connection to the Timer Daemon.
     */
    public JythonDaemon createConnection()
    throws Exception {
        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.rift.coad.client.naming." +
                    "CoadunationInitialContextFactory");
            env.put(Context.PROVIDER_URL,host);
            env.put("com.rift.coad.username",username);
            env.put("com.rift.coad.password",password);
            Context ctx = new InitialContext(env);
            
            Object obj = ctx.lookup(url);
            JythonDaemon beanInterface =
                    (JythonDaemon)
                    PortableRemoteObject.narrow(obj,
                    com.rift.coad.daemon.jython.JythonDaemon.class);
            
            if (beanInterface == null) {
                throw new Exception("narrow failed.");
            } else {
                return beanInterface;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.out.println("Failed to connect to the jython daemon. " +
                    "Check the JDNI reference and host information.");
            System.exit(-1);
            return null;
        }
    }
    
}
