/*
 * DNSServer: The dns server implementation.
 * Copyright (C) 2008  2015 Burntjam
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
 * UDPServer.java
 */

// package path
package com.rift.coad.daemon.dns.server;

// java imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Socket;
import java.net.SocketTimeoutException;

// log imports
import org.apache.log4j.Logger;

// dns imports
import org.xbill.DNS.Message;

// coadunation import
import com.rift.coad.lib.thread.ThreadStateMonitor;



/**
 * This object manages the UDP server port. And than hands off requests to the
 * thread pool.
 *
 * @author brett chaldecott
 */
public class UDPServer extends Thread {
    
    
    /**
     * This method is called to process the request.
     */
    public class UDPRequestProcesser implements ServerRequest {
        
        // private member variables
        private DatagramSocket sock = null;
        private byte[] in = null;
        private DatagramPacket indp = null;
        
        /**
         * The constructor of the tcp request processor.
         */
        public UDPRequestProcesser(DatagramSocket sock,byte [] in, 
                DatagramPacket indp) {
            this.sock = sock;
            this.in = in;
            this.indp = indp;
        }
        
        
        public void processRequest() {
            try {
                log.debug("Processing the request");
                Message query;
                DatagramPacket outdp = null;
                byte [] response = null;
                try {
                    query = new Message(in);
                    log.debug("Generate the reply");
                    response = handler.generateReply(query, in,
                            indp.getLength(),
                            null);
                    log.debug("Retrieved the response");
                    if (response == null) {
                        return;
                    }
                } catch (IOException e) {
                    log.info("Print an error message : " + e.getMessage(),e);
                    response = handler.formErrMessage(in);
                }
                if (outdp == null)
                    outdp = new DatagramPacket(response,
                            response.length,
                            indp.getAddress(),
                            indp.getPort());
                else {
                    outdp.setData(response);
                    outdp.setLength(response.length);
                    outdp.setAddress(indp.getAddress());
                    outdp.setPort(indp.getPort());
                }
                log.debug("Send the response");
                sock.send(outdp);
            } catch (Exception ex) {
                log.error("Failed to process the request : " + ex.getMessage());
            }
        }
        
    }
    
    
    // log object
    private static Logger log = Logger.getLogger(UDPServer.class);
    
    // private member variables.
    private ServerRequestHandler handler = null;
    private InetAddress addr = null;
    private int port = 0;
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private String status = null;
    
    
    /**
     * Creates a new instance of UDPServer
     *
     * @param handler The handler reference.
     * @param addr The address reference.
     * @param port The port reference.
     */
    public UDPServer(ServerRequestHandler handler, InetAddress addr, int port) {
        this.handler = handler;
        this.addr = addr;
        this.port = port;
    }
    
    
    /**
     * The run method for the UDP server.
     */
    public void run() {
        DatagramSocket sock = null;
        try {
            sock = new DatagramSocket(port, addr);
            status = "Accepting Connection on [" + port + "][" +
                    addr.toString() + "]";
            sock.setSoTimeout(100);
            final short udpLength = 512;
            while (!this.state.isTerminated()) {
                byte [] in = new byte[udpLength];
                DatagramPacket indp = new DatagramPacket(in, in.length);
                indp.setLength(in.length);
                try {
                    sock.receive(indp);
                } catch (InterruptedIOException ex) {
                    continue;
                }
                // add a request to the request manager for processing by the
                // thread pool.
                ServerRequestManager.getInstance().addRequest(new 
                        UDPRequestProcesser(sock,in,indp));
               
            }
            
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
                    // ignore
                }
            }
        }
    }
    
    
    /**
     * This method terminates the process of the udp server and waits for the
     * thread to shut down.
     */
    public void terminate() {
        state.terminate(true);
        try {
            this.join();
        } catch (Exception ex) {
            // do nothing
        }
    }
    
    /**
     * This method returns the status of the UDP server.
     *
     * @return The string containing the status of the server.
     */
    public String getStatus() {
        return status;
    }
}
