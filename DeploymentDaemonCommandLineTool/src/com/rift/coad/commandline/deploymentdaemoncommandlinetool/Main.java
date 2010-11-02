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

package com.rift.coad.commandline.deploymentdaemoncommandlinetool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import com.rift.coad.daemon.deployment.DeploymentDaemon;
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
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author Glynn Chaldecott
 *
 * This class is a command line tool for the Deployment Daemon giving a user
 * the ability to do basic operations via the command line.
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
        BasicConfigurator.configure();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            DeploymentDaemon sb = null;
            int choice = -1;
            String location = "";
            String name = "";
            String path = "";
            byte[] fileBytes = null;
            HashMap arguments = new HashMap();
            for (int i = 0; i < args.length; i ++) {
                if (args[i].equals("-d")) {
                    choice = 0;
                } else if (args[i].equals("-f")) {
                    choice = 1;
                } else if (args[i].equals("-h")) {
                    choice = 2;
                } else if (args[i].equals("-l")) {
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
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    int count = 0;
                    byte[] bytes = new byte[1024];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while ((count = bis.read(bytes)) != -1) {
                        baos.write(bytes,0,count);
                    }
                    fileBytes = baos.toByteArray();
                } else if (args[i].equals("-n")) {
                    i ++;
                    name = args[i];
                } else if (args[i].equals("-p")) {
                    i ++;
                    String spacer = "";
                    path = "";
                    boolean flag = true;
                    while (flag) {
                        path += spacer + args[i];
                        spacer = " ";
                        i ++;
                        if (i == args.length) {
                            flag = false;
                        } else if (args[i].charAt(0) == '-') {
                            flag = false;
                        }
                    }
                    i --;
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
                        sb.daemonDeployer(fileBytes,name);
                        System.out.println("The daemon has been deployed.");
                    } else {
                        System.out.println("Statment missing elements.");
                        help();
                    }
                    break;
                case 1:
                    if (!url.equals("") && !host.equals("")) {
                        sb = createConnection();
                        sb.copyFile(fileBytes,name,path);
                        System.out.println("The file has been uploaded.");
                    } else {
                        System.out.println("Statment missing elements.");
                        help();
                    }
                    break;
                case 2:
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
        System.out.println("\t-d \tIs used in order to deploy a Daemon.");
        System.out.println("\t-f \tIs used to upload a file.");
        System.out.println("\t-l \tIs followed by the location of the daemon " +
                "or file to be uploaded.");
        System.out.println("\t-n \tThis is followed by the name of the daemon" +
                " or file.");
        System.out.println("\t-p \tThis is followed by the path to which the " +
                "file is to be stored.");
        System.out.println("\t-s \tThis is to be followed by the host and " +
                "port on which the Deployment Daemon is running and is " +
                "required.");
        System.out.println("\t-u \tThis is to be followed by the JNDI for " +
                "the Deployment Daemon and is required.");
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
    public DeploymentDaemon createConnection()
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
            DeploymentDaemon beanInterface =
                    (DeploymentDaemon)
                    PortableRemoteObject.narrow(obj,
                    com.rift.coad.daemon.deployment.DeploymentDaemon.class);
            
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
