/*
 * EmailServer: The email server implementation.
 * Copyright (C) 2008  Rift IT Contracting
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
 * ImapServer.java
 */

// package path
package com.rift.coad.daemon.email.server;

// java imports
import java.io.InputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


// log imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * The IMAP server responsible for managing the imap port.
 *
 * @author brett chaldecott
 */
public class TCPServer extends Thread {
    
    // class constants
    private final static String TCP_TIMEOUT = "email_tcp_timeout";
    private final static long DEFAULT_TCP_TIMEOUT = 100;
    
    // private member variables
    private Logger log = Logger.getLogger(TCPServer.class);
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private int port = 0;
    private String address = null;
    private Class request = null;
    private long tcpTimeout = DEFAULT_TCP_TIMEOUT;
    private String status = null;
    
    /**
     * Creates a new instance of ImapServer
     *
     * @param port The port server will connect to.
     * @param address The address the server will bind to.
     * @param request The request class that will handle the in bound requests.
     */
    public TCPServer(int port, String address, Class request) 
    throws ServerException {
        this.port = port;
        this.address = address;
        this.request = request;
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    TCPServer.class);
            tcpTimeout = config.getLong(TCP_TIMEOUT,DEFAULT_TCP_TIMEOUT);
        } catch (Exception ex) {
            log.error("Failed to setup a tcp server [" + port + "][" + address
                    + "] because : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to setup a tcp server [" + port + "][" + address
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process requests.
     */
    public void run() {
        ServerSocket sock = null;
        try {
            // start the socket server and set its tim out to 100 milleseconds
            sock = new ServerSocket(port, 128, InetAddress.getByName(address));
            sock.setSoTimeout(100);
            status = "Accepting Connection on [" + port + "][" +
                    address + "]";
            
            // process requests
            while (!state.isTerminated()) {
                try {
                    Socket s = sock.accept();
                    // add the request to the pool for processing
                    ServerRequestManager.getInstance().addRequest(
                            (ServerRequest)
                            request.getConstructor(new Class[] {Socket.class}).
                            newInstance(new Object[] {s}));
                } catch (SocketTimeoutException ex) {
                    // ignore
                } catch (Throwable ex) {
                    log.error("Failed to process result :" + ex.getMessage(),ex);
                }
            }
            
            status = "Shutting down the connection on [" + port + "][" +
                    address + "]";
            // close the socket server down
            status = "Terminated the connection on [" + port + "][" +
                    address + "]";
        } catch (Throwable ex) {
            log.error("Failed to bind to the socket [" + port + "][" +
                    address + "] because : " + ex.getMessage(),ex);
            status = "Failed to bind to the socket [" + port + "][" +
                    address + "] because : " + ex.getMessage();
        } finally {
            if (sock != null) {
                try {
                    sock.close();
                } catch (Exception ex) {
                    log.error("Failed to close the socket [" + port + "][" +
                            address + "] because : " + ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method is called to terminate the processing of the IMap socket
     * client.
     */
    public void terminate() {
        state.terminate(true);
        try {
            this.join();
        } catch (Exception ex) {
            log.error("Failed to wait for thread to shut down because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the status of this tcp server.
     */
    public String getStatus() {
        return status;
    }
}
