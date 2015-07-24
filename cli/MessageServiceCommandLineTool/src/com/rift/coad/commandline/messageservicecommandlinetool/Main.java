/*
 * MessageServiceCommandLine: The message service command line tool.
 * Copyright (C) 2007 2015 Burntjam
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
package com.rift.coad.commandline.messageservicecommandlinetool;


// java imports
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

// log4j
import org.apache.log4j.BasicConfigurator;

// message service imports
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageError;
import com.rift.coad.daemon.messageservice.MessageServiceManagerMBean;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.RPCMessage;
import com.rift.coad.daemon.messageservice.TextMessage;


/**
 * This object is responsible for suppling a command line tool to manage the
 * message service.
 *
 * @author Brett Chaldecott
 */
public class Main {
    
    // private member variables
    private String url = null;
    private String host = null;
    private String username = null;
    private String password = null;
    
    
    /** 
     * The constructor of the main class.
     */
    public Main() {
        
    }
    
    
    /**
     * This method breaks the arguments down and uses them to perform the
     * necessary task.
     */
    public void commandLine(String[] args) {
        try {
            String queue = "";
            int choice = -1;
            for (int i = 0; i < args.length; i ++) {
                if (args[i].equals("-h")) {
                    help();
                    return;
                } else if (args[i].equals("-u")) {
                    i++;
                    url = args[i];
                } else if (args[i].equals("-U")) {
                    i++;
                    username = args[i];
                } else if (args[i].equals("-P")) {
                    i++;
                    password = args[i];
                } else if (args[i].equals("-s")) {
                    i ++;
                    host = args[i];
                    if (host.indexOf(":") == -1) {
                        host += ":2000";
                    }
                } else if (args[i].equals("-l")) {
                    choice = 1;
                } else if (args[i].equals("-lq")) {
                    choice = 2;
                    if (++i >= args.length) {
                        throw new Exception(
                                "Please supply a queue name when using the lq " +
                                "option.");
                    }
                    queue = args[i];
                } else if (args[i].equals("-pq")) {
                    choice = 3;
                    if (++i >= args.length) {
                        throw new Exception(
                                "Please supply a queue name when using the pq " +
                                "option.");
                    }
                    queue = args[i];
                }
            }
            // check the results of the command line parsing.
            if ((url == null) || (username == null) || (password == null) ||
                    (host == null) || (choice == -1)) {
                System.out.println("Invalid data supplied please use the " +
                        "following arguments");
                help();
                return;
            }
            
            MessageServiceManagerMBean ms = null;
            List entries = null;
            switch (choice) {
                case 0:
                    help();
                    break;
                case 1:
                    ms = createConnection();
                    entries = ms.listNamedQueues();
                    System.out.println("List of named queues [" + entries.size() + "]");
                    for (Iterator iter = entries.iterator(); iter.hasNext();) {
                        System.out.println("\t" + (String)iter.next());
                    }
                    
                    break;
                case 2:
                    ms = createConnection();
                    entries = ms.listMessagesForNamedQueue(queue);
                    System.out.println("Queue entries for : " + queue);
                    for (Iterator iter = entries.iterator(); iter.hasNext();) {
                        Message message = (Message)iter.next();
                        System.out.println("\tMessage:" + message.getMessageId());
                        if (message instanceof RPCMessage){
                            System.out.println("Type: RPC");
                            System.out.println("\tBody:" + 
                                    ((RPCMessage)message).getMethodBodyXML());
                        } else {
                            System.out.println("Type: Text");
                            System.out.println("\tBody:" + 
                                    ((TextMessage)message).getTextBody());
                        }
                        if (message.getMessageType() == Message.POINT_TO_POINT){
                            System.out.println("\tRouting: Point to Point");
                        } else if (message.getMessageType() == 
                                Message.POINT_TO_SERVICE) {
                            System.out.println("\ttRouting: Point to Service");
                        } else if (message.getMessageType() == 
                                Message.POINT_TO_MULTI_SERVICE) {
                            System.out.println("\ttRouting: Point to Multi " +
                                    "Service");
                        }
                        List errors = message.getErrors();
                        int errorCount = 1;
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd hh:mm:ss");
                        for (Iterator errorIter = errors.iterator(); 
                        errorIter.hasNext();) {
                            MessageError error = (MessageError)errorIter.next();
                            System.out.println(dateFormat.format(
                                    error.getErrorDate()) + " [" +
                                    error.getLevel() +"] error :"+ 
                                    error.getMSG());
                        }
                    }
                    break;
                case 3:
                    ms = createConnection();
                    ms.purgeNamedQueue(queue);
                    break;
                default:
                    System.out.println("Invalid arguments");
                    help();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to manage the message service : " +
                    ex.getMessage());
            help();
        }
    }
    
    
    /**
     * This method prints out the help.
     */
    public void help() {
        System.out.println("Help");
        System.out.println("\t-u\tJNDI url of message service.");
        System.out.println("\t-s\tThe server the coadunation instance runs on.");
        System.out.println("\t\t\thostname:port");
        System.out.println("\t-U\tUsername for connection");
        System.out.println("\t-P\tPassword for the connection.");
        System.out.println("\t-l\tList of the named queues in uses");
        System.out.println("\t-lq\tList entries for the named queue");
        System.out.println("\t-pq\tPurge the entries from the queue");
    }
    
    
    /**
     * This method connects to the Timer Daemon and then returns the Object.
     *
     * @return This method returns a connection to the Timer Daemon.
     */
    public MessageServiceManagerMBean createConnection()
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
            MessageServiceManagerMBean beanInterface =
                    (MessageServiceManagerMBean)
                    PortableRemoteObject.narrow(obj,
                    MessageServiceManagerMBean.class);
            
            if (beanInterface == null) {
                throw new Exception("narrow failed.");
            } else {
                return beanInterface;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.out.println("Failed to connect to the message service : " + 
                    ex.getMessage());
            System.exit(-1);
            return null;
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MessageServiceException {
        BasicConfigurator.configure();
        Main mainProgram = new Main();
        mainProgram.commandLine(args);
    }
    
    
    
}
