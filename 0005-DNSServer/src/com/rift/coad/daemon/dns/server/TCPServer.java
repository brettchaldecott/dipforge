/*
 * DNSServer: The dns server implementation.
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
 * TCPServer.java
 */

// package path
package com.rift.coad.daemon.dns.server;


// java imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Vector;
import java.util.Date;

// log imports
import org.apache.log4j.Logger;

// dns imports
import org.xbill.DNS.Message;

// coadunation import
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This object binds and manages the specified address and port configuration.
 *
 * @author brett
 */
public class TCPServer extends Thread {
    
    /**
     * This method is called to process the request.
     */
    public class TCPRequestProcesser implements ServerRequest {
        
        // private member variables
        private Socket s = null;
        
        /**
         * The constructor of the tcp request processor.
         */
        public TCPRequestProcesser(Socket s) {
            this.s = s;
        }
        
        
        /**
         * This method performs the processing using the socet supplied to its
         * constructor
         */
        public void processRequest() {
            try {
                while (hasRequest()) {
                    log.debug("Processing the request");
                    int inLength;
                    DataInputStream dataIn;
                    DataOutputStream dataOut;
                    byte [] in;
                    
                    InputStream is = s.getInputStream();
                    dataIn = new DataInputStream(is);
                    inLength = dataIn.readUnsignedShort();
                    in = new byte[inLength];
                    dataIn.readFully(in);
                    
                    Message query;
                    byte [] response = null;
                    try {
                        query = new Message(in);
                        log.debug("Get the response");
                        response = handler.generateReply(query, in, in.length, s);
                        if (response == null) {
                            break;
                        }
                    } catch (IOException e) {
                        log.debug("Generate an error response");
                        response = handler.formErrMessage(in);
                    }
                    dataOut = new DataOutputStream(s.getOutputStream());
                    dataOut.writeShort(response.length);
                    dataOut.write(response);
                    log.debug("Sent the response");
                }
            } catch (Exception  ex) {
                log.error("Failed to answer the request correctly because : " +
                        ex.getMessage(),ex);
            } finally {
                try {
                    s.close();
                } catch (IOException e) {}
            }
            log.debug("Processing complete");
        }
        
        
        
        /**
         * This method will return true if there is another request for this socket
         * from a bound TCP IP client.
         *
         * @return TRUE if there is another request.
         */
        private boolean hasRequest() {
            long stopTime = new Date().getTime() + requestTimeOut;
            try {
                while (new Date().getTime() < stopTime) {
                    // check for input
                    if (s.isClosed() || s.isInputShutdown()) {
                        return false; 
                    } 
                    if (s.getInputStream().available() > 0) {
                        return true;
                    }
                    
                    // sleep for 100 milli seconds
                    Thread.sleep(100);
                }
            } catch (Exception ex) {
                log.error("Failed to check for another request : " + 
                        ex.getMessage());
            }
            return false;
        }
    }
    
    // class constants
    private final static long DEFAULT_REQUEST_TIMEOUT = 1000;
    private final static String REQUEST_TIMEOUT = "request_timeout";
    
    
    // log object
    private static Logger log = Logger.getLogger(TCPServer.class);
    
    // private member variables.
    private ServerRequestHandler handler = null;
    private InetAddress addr = null;
    private int port = 0;
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private String status = null;
    private long requestTimeOut = DEFAULT_REQUEST_TIMEOUT;
    
    /**
     * Creates a new instance of TCPServer.
     *
     * @param handler The reference to the handler object.
     * @param addr The addres to bind to.
     * @param port The port to bind to.
     */
    public TCPServer(ServerRequestHandler handler, InetAddress addr, int port) {
        this.handler = handler;
        this.addr = addr;
        this.port = port;
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    TCPServer.class);
            requestTimeOut = config.getLong(REQUEST_TIMEOUT,
                    DEFAULT_REQUEST_TIMEOUT);
        } catch (Exception ex) {
            log.error("Failed to get configuration information : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to run.
     */
    public void run() {
        ServerSocket sock = null;
        try {
            // start the socket server and set its tim out to 100 milleseconds
            sock = new ServerSocket(port, 128, addr);
            sock.setSoTimeout(100);
            status = "Accepting Connection on [" + port + "][" +
                    addr.toString() + "]";
            
            // process requests
            while (!state.isTerminated()) {
                try {
                    Socket s = sock.accept();
                    // add the request to the pool for processing
                    ServerRequestManager.getInstance().addRequest(
                            new TCPRequestProcesser(s));
                } catch (SocketTimeoutException ex) {
                    // ignore
                } catch (Throwable ex) {
                    log.error("Failed to process result :" + ex.getMessage(),ex);
                }
            }
            
            status = "Shutting down the connection on [" + port + "][" +
                    addr.toString() + "]";
            // close the socket server down
            status = "Terminated the connection on [" + port + "][" +
                    addr.toString() + "]";
        } catch (Exception ex) {
            log.error("Failed to bind to the socket [" + port + "][" +
                    addr.toString() + "] because : " + ex.getMessage(),ex);
            status = "Failed to bind to the socket [" + port + "][" +
                    addr.toString() + "] because : " + ex.getMessage();
        } finally {
            if (sock != null) {
                try {
                    sock.close();
                } catch (Exception ex) {
                    log.error("Failed to close the socket [" + port + "][" +
                            addr.toString() + "] because : " + ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method is called to terminate the processing of this thread.
     */
    public void terminate() {
        state.terminate(true);
        try {
            this.join();
        } catch (Exception ex) {
            // ignore
        }
    }
    
    
    /**
     * This method returns the status of this tcp server.
     */
    public String getStatus() {
        return status;
    }
    
}
