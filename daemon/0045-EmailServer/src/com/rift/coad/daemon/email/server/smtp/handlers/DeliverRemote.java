/*
 * Email Server: The email server interface
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
 * DeliverRemote.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp.handlers;

// java imports
import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.net.Socket;
import java.net.SocketException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// log4j imports
import org.apache.log4j.Logger;

// the dns imports
import org.xbill.DNS.Record;
import org.xbill.DNS.MXRecord;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

// email server imports
import com.rift.coad.daemon.email.smtp.Handler;
import com.rift.coad.daemon.email.smtp.MessageInfo;
import com.rift.coad.daemon.email.smtp.SMTPException;
import com.rift.coad.daemon.email.smtp.Server;
import com.rift.coad.daemon.email.smtp.Message;
import com.rift.coad.daemon.email.types.Address;
import com.rift.coad.daemon.email.types.Domain;
import com.rift.coad.daemon.email.server.dns.NSTool;


/**
 * This object is responsible for delivering the message remotely.
 *
 * @author brett chaldecott
 */
public class DeliverRemote implements Handler {
    
    // class constants
    private final static String SMTP_GATEWAYS = "smtp_gateways";
    private final static String DEFAULT_SMTP_GATEWAYS = "";
    private final static String SMTP_SERVER_JNDI = "smtp_server_jndi";
    private final static String DEFAULT_SMTP_SERVER_JNDI =
            "java:comp/env/bean/email/SMTPServer";
    private final static String SMTP_TIMEOUT = "smtp_timeout";
    private final static long DEFAULT_SMTP_TIMEOUT = 180000;
    private final static String SMTP_CONNECTION_TIMEOUT =
            "smtp_connection_timeout";
    private final static long DEFAULT_SMTP_CONNECTION_TIMEOUT = 60000;
    private final static String SMTP_SERVER_HOSTNAME = "host";
    
    
    
    // private member variables
    private static Logger log = Logger.getLogger(DeliverRemote.class);
    private Configuration config = null;
    private String[] gateways = null;
    private Server smtpServer = null;
    private long smtpTimeout = 0;
    private long connectionTimeout = 0;
    private String serverHost = null;
    
    
    /**
     * Creates a new instance of DeliverRemote
     *
     * @exception SMTPException
     */
    public DeliverRemote() throws SMTPException {
        try {
            config = ConfigurationFactory.getInstance().
                    getConfig(DeliverRemote.class);
            String gatewayList = config.getString(SMTP_GATEWAYS,
                    DEFAULT_SMTP_GATEWAYS).trim();
            gateways = new String[0];
            if (gatewayList.length() != 0) {
                gateways = gatewayList.split("[,]");
            }
            smtpTimeout = config.getLong(SMTP_TIMEOUT,
                    DEFAULT_SMTP_TIMEOUT);
            connectionTimeout = config.getLong(SMTP_TIMEOUT,
                    DEFAULT_SMTP_CONNECTION_TIMEOUT);
            serverHost = config.getString(SMTP_SERVER_HOSTNAME);
            
        } catch (Throwable ex) {
            log.error("Failed to retrieve the configuration : " +
                    ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to retrieve the configuration : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method processes the message delivery request.
     *
     * @return The message information to return.
     * @param info The information to utilize for this request.
     * @exception SMTPException
     * @exception RemoteException
     */
    public MessageInfo process(MessageInfo info) throws SMTPException,
            RemoteException {
        
        try {
            Message message = this.getServer().getMessage(info.getId());
            sendMessage(message);
            return info;
        } catch (Throwable ex) {
            log.error("Failed to process the message [" + info.getId() + "] : "+
                    ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to process the message [" + info.getId() + "] : "+
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Handles delivery of messages to addresses not handled by this server.
     *
     * @private message The message to process.
     * @exception SMTPException
     */
    private void sendMessage( Message message )
    throws SMTPException {
        
        //Open the connection to the server.
        Socket socket = connect( message );
        
        // check if there is a destination for the message.
        if (socket == null) {
            try {
                message.setRCPTs(message.getFrom());
                message.setData("There is no destination for message [" +
                        message.getId() + "] [" +
                        ((Address)message.getRCPTs().get(0)).getDomain() + "]");
                this.getServer().sendMessage(message);
            } catch (Exception ex) {
                log.error("Failed to resend the message because it is " +
                        "undeliverable");
            }
        }
        
        // Set the timeout so reads do not hang forever.
        try {
            socket.setSoTimeout( 60 * 1000 );
        } catch( SocketException e ) {
            log.error("Unable to set the Socket SO Timeout: "+ e.getMessage());
            throw new SMTPException(
                    "Unable to set the Socket SO Timeout: "+ e.getMessage());
        }
        
        try {
            //Get the input and output streams.
            PrintWriter out =
                    new PrintWriter( socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( socket.getInputStream() ));
            
            //Perform initial commands
            sendIntro( out, in, message );
            
            //Send message data
            sendData( out, in, message );
            
            //Close the connection.
            sendClose(out, in);
        } catch( Throwable ex ) {
            log.error("Failed to send the message : " + ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to send the message : " + ex.getMessage(),ex);
        } finally {
            if( socket != null )  {
                try {
                    socket.close();
                } catch( Throwable ex ) {
                    log.error( "Error closing socket: " + ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * Determines the MX entries for this domain and attempts to open
     * a socket.  If no connections can be opened, a SystemException is thrown.
     *
     * @return The socket connection.
     * @param message The message to retrieve the delivery information from.
     * @exception SMTPException
     */
    private Socket connect( Message message ) throws SMTPException {
        
        String domain = ((Address)message.getRCPTs().get(0)).getDomain();
        
        //Check to see if a default smtp server is configured before performing
        //the DNS lookup.
        if( gateways.length != 0 ) {
            return connect( gateways );
        } else {
            try {
                // Lookup the MX Entries
                Record [] records = NSTool.getInstance().getMXRecords(domain);
                if( records == null ) {
                    log.warn( "No mx information for [" + domain + "] found.");
                    return null;
                }
                
                // Convert the MX Entries to strings and sort them in order
                // of priority.
                String[] mxEntries = new String[records.length];
                short priority = 0;
                short nextPriority = Short.MAX_VALUE;
                int mxIndex = 0;
                while( mxIndex < mxEntries.length ) {
                    for (int i = 0; i < records.length; i++) {
                        MXRecord mx = (MXRecord) records[i];
                        if( mx.getPriority() == priority ) {
                            mxEntries[mxIndex++] = mx.getTarget().toString();
                            if(mxIndex >= mxEntries.length) break;
                        } else if( mx.getPriority() < nextPriority &&
                                mx.getPriority() > priority ) {
                            nextPriority = (short)mx.getPriority();
                        }
                    }
                    priority = nextPriority;
                    nextPriority = Short.MAX_VALUE;
                }
                
                return connect(mxEntries);
            } catch( Throwable e ) {
                throw new SMTPException( "Failed to retrieve valid MX " +
                        "information for [" + domain +"] : " + e.getMessage() );
            }
            
        }
    }
    
    /**
     * Determines the MX entries for this domain and attempts to open
     * a socket.  If no connections can be opened, a SystemException is thrown.
     *
     * @return The socket connection.
     * @param mxEntries The mx entries to process.
     * @exception SMTPException
     */
    private Socket connect( String[] mxEntries ) throws SMTPException {
        for( int index = 0; index < mxEntries.length; index++ ) {
            String[] gateway = mxEntries[index].split(":");
            try {
                if (gateway.length == 1) {
                    return new Socket( NSTool.getInstance().getByName(
                            gateway[0]), 25 );
                } else if (gateway.length == 2 ){
                    return new Socket( NSTool.getInstance().getByName(
                            gateway[0]),
                            Integer.parseInt(gateway[1]) );
                }
            } catch( Throwable ex ) {
                log.debug( "Failed to connect to the gateway [" +
                        gateways[index] + "] because : " + ex.getMessage());
            }
        }
        throw new SMTPException("Failed to connect to the default gateways");
    }
    
    
    /**
     * This method sends all the commands neccessary to prepare the remote server
     * to recieve the data command.
     */
    private void sendIntro( PrintWriter out, BufferedReader in,
            Message message ) throws SMTPException {
        
        //Check to make sure remote server introduced itself with appropriate message.
        String lastCode = null;
        if( !(lastCode = read(in)).startsWith( "220" ) ) {
            throw new SMTPException( "Error talking to remote Server, code="
                    + lastCode );
        }
        
        // First try ehlo
        write( out, "EHLO " + serverHost );
        if( !(lastCode = read(in)).startsWith( "250" ) ) {
            //Send HELO command to remote server.
            write( out, "HELO " + serverHost );
            if( !(lastCode = read(in)).startsWith( "250" ) ) {
                throw new SMTPException( "Error talking to remote Server, code="
                        + lastCode );
            }
        }
        
        //Send MAIL FROM: command
        StringBuffer from = new StringBuffer().append("MAIL FROM:<");
        String sep = "";
        for (int index = 0; index < message.getFrom().size(); index++) {
            from.append(sep).append(message.getFrom().get(index).toString());
            sep = ",";
        }
        from.append(">");
        write( out, from.toString() );
        if( !(lastCode = read(in)).startsWith( "250" ) ) {
            throw new SMTPException( "Error talking to remote Server, code="+
                    lastCode );
        }
        
        //Send RCTP TO: command
        write( out, "RCPT TO:<" + message.getRCPTs().get(0).toString() + ">" );
        if( !(lastCode = read(in)).startsWith( "250" ) ) {
            throw new SMTPException( "Error talking to remote Server, code="+
                    lastCode );
        }
    }
    
    
    /**
     * This method sends the data command and all the message data to the
     * remote server.
     *
     * @param out The stream to write output to.
     * @param in The stream to read input from.
     * @param message The message to write.
     * @exception SMTPException
     */
    private void sendData( PrintWriter out, BufferedReader in,
            Message message ) throws SMTPException {
        
        //Send Data command
        write( out, "DATA" );
        if( !read(in).startsWith( "354" ) ) {
            throw new SMTPException( "Error talking to remote Server" );
        }
        
        // write out the headers
        for (int index = 0; index < message.getHeaders().size(); index++) {
            write(out, message.getHeaders().get(index).toString());
        }
        
        // write out the data block
        write(out, message.getData());
        
        //Send the command end data transmission.
        write(out, "." );
        
        if( !read(in).startsWith( "250" ) ) {
            throw new SMTPException( "Error talking to remote Server" );
        }
    }
    
    
    /**
     * This method is called to close the connection.
     *
     * @param out The out data stream.
     * @param in The in data stream.
     * @exception SMTPException
     */
    private void sendClose(PrintWriter out, BufferedReader in) throws
            SMTPException{
        
        write(out, "QUIT" );
        if( !read(in).startsWith( "221" ) ) {
            throw new SMTPException( "Error talking to remote Server" );
        }
    }
    
    
    /**
     * Returns the response code generated by the server.
     * This method will handle multi-line responses, but will
     * only log the responses, and discard the text, returning
     * only the 3 digit response code.
     *
     * @return 3 digit response string.
     * @param in The buffer to read the results from.
     * @exception SMTPException
     */
    private String read(BufferedReader in) throws SMTPException {
        try {
            String responseCode;
            
            //Read in the first line.  This is the only line
            //we really care about, since the response code
            //must be the same on all lines.
            String inputText = in.readLine();
            if( inputText == null ) {
                inputText = "";
            } else {
                inputText = inputText.trim();
            }
            
            if( log.isDebugEnabled() ) { log.debug( "Read Input: " + inputText ); }
            if( inputText.length() < 3 ) {
                throw new SMTPException(
                        "SMTP Response too short. Aborting Send. Response: "
                        + inputText );
            }
            
            //Strip of the response code.
            responseCode = inputText.substring( 0, 3 );
            
            //Handle Multi-Line Responses.
            while( ( inputText.length() >= 4 ) &&
                    inputText.substring( 3, 4 ).equals( "-" ) ) {
                inputText = in.readLine().trim();
                if( log.isDebugEnabled() ) {
                    log.debug( "Read Input: " + inputText ); }
            }
            
            return responseCode;
        } catch (SMTPException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error( "Error reading from socket : " + ex.getMessage(), ex );
            throw new SMTPException(
                    "Error reading from socket : " + ex.getMessage(), ex);
        }
    }
    
    /**
     * Writes the specified output message to the client.
     *
     * @param out The output to write.
     * @param message The message to write.
     */
    private void write( PrintWriter out, String message ) {
        
        out.print( message + "\r\n" );
        out.flush();
    }
    
    
    /**
     * This method returns a reference to the server.
     *
     * @return The reference to the SMTP server.
     * @exception SMTPException
     */
    private Server getServer() throws SMTPException {
        try {
            if (smtpServer == null) {
                Context context = new InitialContext();
                smtpServer = (Server)context.lookup(
                        config.getString(SMTP_SERVER_JNDI,
                        DEFAULT_SMTP_SERVER_JNDI));
            }
            return smtpServer;
        } catch (Throwable ex) {
            log.error("Failed to make a connection to the smtp server : " +
                    ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to make a connection to the smtp server : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    
}
