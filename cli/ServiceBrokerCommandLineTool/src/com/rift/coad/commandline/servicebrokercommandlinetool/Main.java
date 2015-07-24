/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  2015 Burntjam
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


package com.rift.coad.commandline.servicebrokercommandlinetool;

// java imports
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.io.*;

// log4j
import org.apache.log4j.BasicConfigurator;


// coadunation imports
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.daemon.servicebroker.ServiceBrokerException;

/**
 *
 * @author Glynn Chaldecott
 *
 * This class is a command line tool for the Service Broker Daemon and gives a
 * user basic functionality via the command line.
 */
public class Main {
    public BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    public String[] args = null;
    public String url = "";
    public String host = "";
    public String username = "";
    public String password = "";
    
    /** Creates a new instance of Main */
    public Main() {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ServiceBrokerException {
        BasicConfigurator.configure();
        Main mainProgram = new Main();
        mainProgram.setArgs(args);
        mainProgram.commandLine();
    }
    
    /**
     * This method simple sets the public args variable.
     *
     * @param args This is the args passed into the main method.
     */
    public void setArgs(String[] args) {
        this.args = args;
    }
    
    /**
     * This method breaks the arguments down and uses them to perform the
     * necessary task.
     */
    public void commandLine() {
        try {
            ServiceBroker sb = null;
            int choice = -1;
            List service = new ArrayList();
            String jndi = "";
            for (int i = 0; i < args.length; i ++) {
                if (args[i].equals("-r")) {
                    choice = 0;
                } else if (args[i].equals("-o")) {
                    choice = 1;
                } else if (args[i].equals("-m")) {
                    choice = 2;
                } else if (args[i].equals("-d")) {
                    choice = 3;
                } else if (args[i].equals("-S")) {
                    i ++;
                    service.add(args[i]);
                } else if (args[i].equals("-j")) {
                    i ++;
                    jndi = args[i];
                } else if (args[i].equals("-h")) {
                    choice = 4;
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
            switch (choice) {
                case 0:
                    if (!url.equals("") && !host.equals("")) {
                        sb = createConnection();
                        sb.registerService(jndi,service);
                        System.out.println("The service has been " +
                                "registered.");
                    } else {
                        System.out.println("Statment missing elements.");
                        help();
                    }
                    break;
                case 1:
                    if (!url.equals("") && !host.equals("")) {
                        sb = createConnection();
                        String temp = sb.getServiceProvider(service);
                        System.out.println(temp);
                    } else {
                        System.out.println("Statment missing elements.");
                        help();
                    }
                    break;
                case 2:
                    if (!url.equals("") && !host.equals("")) {
                        sb = createConnection();
                        List temp2 = sb.getServiceProviders(service);
                        for (int i = 0; i < temp2.size(); i ++) {
                            System.out.println((String) temp2.get(i));
                        }
                    } else {
                        System.out.println("Statment missing elements.");
                        help();
                    }
                    break;
                case 3:
                    if (!url.equals("") && !host.equals("")) {
                        sb = createConnection();
                        sb.removeServiceProviders(jndi,service);
                        System.out.println("The service has been deleted.");
                    } else {
                        System.out.println("Statment missing elements.");
                        help();
                    }
                    break;
                case 4:
                    help();
                    break;
                default: 
                    System.out.println("There was a problem with the " +
                        "statement");
                    help();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void help() {
        System.out.println("Help");
        System.out.println("\t-r \tThis is to be user to register a service.");
        System.out.println("\t-o \tThis is to be used to retrieve a single " +
                "JNDI");
        System.out.println("\t-m \tThis is to be used to retrieve multiple " +
                "JNDI's");
        System.out.println("\t-d \tThis is to be used to delete a service");
        System.out.println("\t-S \tThis is to be followed by the name of the " +
                "service. This can be used multiple times in a single " +
                "statement.");
        System.out.println("\t-j \tThis is to be followed by the JNDI for " +
                "the service. This can only be called once in a statment.");
        System.out.println("\t-s \tThis is to be followed by the host and " +
                "port on which the Service Broker Daemon is running and is " +
                "required.");
        System.out.println("\t-u \tThis is to be followed by the JNDI for " +
                "the Service Broker Daemon and is required.");
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
    public com.rift.coad.daemon.servicebroker.ServiceBroker createConnection()
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
            com.rift.coad.daemon.servicebroker.ServiceBroker beanInterface =
                    (com.rift.coad.daemon.servicebroker.ServiceBroker)
                    PortableRemoteObject.narrow(obj,
                    com.rift.coad.daemon.servicebroker.ServiceBroker.class);
            
            if (beanInterface == null) {
                throw new Exception("narrow failed.");
            } else {
                return beanInterface;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
            throw new Exception(ex);
        }
    }
    
}
