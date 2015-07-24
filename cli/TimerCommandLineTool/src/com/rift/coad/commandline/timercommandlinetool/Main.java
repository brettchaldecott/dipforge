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

// package path
package com.rift.coad.commandline.timercommandlinetool;

// java imports
import com.rift.coad.daemon.timer.TimerEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import com.rift.coad.daemon.timer.Timer;

// log4j
import org.apache.log4j.BasicConfigurator;


/**
 *
 * @author Glynn Chaldecott
 *
 * This class is a command line tool for the Timer Daemon and gives a user basic
 * functionality through the command line.
 */
public class Main {
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
    public static void main(String[] args) {
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
            Timer sb = null;
            int choice = -1;
            List service = new ArrayList();
            String jndi = "";
            Serializable event = null;
            boolean recure = false;
            int minute = -1;
            int hour = -1;
            int day = -1;
            int month = -1;
            int id = 0;
            
            for (int i = 0; i < args.length; i ++) {
                if (args[i].equals("-r")) {
                    choice = 0;
                } else if (args[i].equals("-l")) {
                    choice = 1;
                } else if (args[i].equals("-d")) {
                    choice = 2;
                } else if (args[i].equals("-m")) {
                    i ++;
                    month = Integer.parseInt(args[i]);
                } else if (args[i].equals("-da")) {
                    i ++;
                    day = Integer.parseInt(args[i]);
                } else if (args[i].equals("-ho")) {
                    i ++;
                    hour = Integer.parseInt(args[i]);
                } else if (args[i].equals("-mi")) {
                    i ++;
                    minute = Integer.parseInt(args[i]);
                } else if (args[i].equals("-id")) {
                    i ++;
                    id = Integer.parseInt(args[i]);
                } else if (args[i].equals("-j")) {
                    i ++;
                    jndi = args[i];
                } else if (args[i].equals("-e")) {
                    i ++;
                    String temp = "";
                    boolean flag = true;
                    while (flag) {
                        String temp2 = args[i];
                        if (temp2.charAt(0) == '-') {
                            flag = false;
                            i --;
                        } else {
                            temp += temp2;
                            i ++;
                        }
                    }
                    event = (Serializable) temp;
                } else if (args[i].equals("-re")) {
                    i ++;
                    String temp = args[i];
                    if (temp.toUpperCase().equals("FALSE") ||
                            temp.toUpperCase().equals("F")) {
                        recure = false;
                    } else if (temp.toUpperCase().equals("TRUE") ||
                            temp.toUpperCase().equals("T")) {
                        recure = true;
                    }
                } else if (args[i].equals("-h")) {
                    choice = 3;
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
                case 0: if (!url.equals("") && !host.equals("")) {
                    sb = createConnection();
                    sb.register(jndi,month,day,hour,minute,event,recure);
                    System.out.println("The event has been registered.");
                } else {
                    System.out.println("Statment missing elements.");
                    help();
                }
                break;
                case 1: if (!url.equals("") && !host.equals("")) {
                    sb = createConnection();
                    TimerEvent[] temp = sb.listEvents();
                    for (int i = 0; i < temp.length; i ++) {
                        System.out.println(temp[i].getId() + " "
                                + temp[i].getJndi() + " "
                                + temp[i].getMonth() + "-"
                                + temp[i].getDay() + "-"
                                + temp[i].getHour() + "-"
                                + temp[i].getMinute() + " "
                                + temp[i].getEvent());
                    }
                } else {
                    System.out.println("Statment missing elements.");
                    help();
                }
                break;
                case 2: if (!url.equals("") && !host.equals("")) {
                    sb = createConnection();
                    sb.deleteEvent(id);
                    System.out.println("The event has been deleted.");
                } else {
                    System.out.println("Statment missing elements.");
                    help();
                }
                break;
                case 3: help();
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
        System.out.println("\t-r \tThis is to be used to Register an event.");
        System.out.println("\t-l \tThis is to be used to retrieve a list of " +
                "events.");
        System.out.println("\t-d \tThis is to be used to delete an event.");
        System.out.println("\t-m \tThis is to be followed by the month.");
        System.out.println("\t-da \tThis is to be followed by the day.");
        System.out.println("\t-ho \tThis is to be followed by the hour.");
        System.out.println("\t-mi \tThis is to be followed by the minute.");
        System.out.println("\t-id \tThis is to be followed by the event id.");
        System.out.println("\t-j \tThis is to be followed by the JNDI for " +
                "the service. This can only be called once in a statment.");
        System.out.println("\t-s \tThis is to be followed by the host and " +
                "port on which the Timer Daemon is running and is required.");
        System.out.println("\t-u \tThis is to be followed by the JNDI for " +
                "the Timer Daemon and is required.");
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
    public com.rift.coad.daemon.timer.Timer createConnection()
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
            com.rift.coad.daemon.timer.Timer beanInterface =
                    (com.rift.coad.daemon.timer.Timer)
                    PortableRemoteObject.narrow(obj,
                    com.rift.coad.daemon.timer.Timer.class);
            
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
