/*
 * EMailServer: The email server implementation.
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
 * ImapUtils.java
 */

// package path
package com.rift.coad.daemon.email.server.imap;

// java utils
import java.util.Date;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;

// log4j imports
import org.apache.log4j.Logger;

// java mail api
import javax.mail.Flags;
import com.sun.mail.imap.protocol.FLAGS;

// email imports
import com.rift.coad.daemon.email.server.ServerException;


/**
 * This class contains a common set of utils for handling IMAP requests.
 * @author brett chaldecott
 */
public class ImapUtils {
    
    // 
    private static Logger log = Logger.getLogger(ImapUtils.class);
    
    /**
     * Creates a new instance of ImapUtils
     */
    private ImapUtils() {
    }
    
    
    /**
     * This method parses the date passed in.
     *
     * @return The data object.
     * @param date The date string to parse.
     */
    public static Date parseDate(String date) throws ServerException {
        try {
            try {
                return new SimpleDateFormat(
                        "EEE, d MMM yyyy HH:mm:ss Z").parse(date);
            } catch (java.text.ParseException ex) {
                // ignore
            }
            try {
                return new SimpleDateFormat(
                        "dd-MMM-yyyy HH:mm:ss Z").parse(date);
            } catch (java.text.ParseException ex) {
                // ignore
            }
            try {
                return new SimpleDateFormat("dd-MMM-yyyy").
                        parse(date);
            } catch (java.text.ParseException ex) {
                log.error("Failed to parse the date ["+ date +"]" 
                        + ex.getMessage(),ex);
                throw new ServerException(
                        "Failed to parse the date ["+ date +"]" 
                        + ex.getMessage(),ex);
            }
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Invalid date : " + ex.getMessage(),ex);
            throw new ServerException("Invalid date : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the date string.
     *
     * @return The string containing the current date.
     */
    public static String getDateString(Date date) {
        return new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss Z").format(
                date);
    }
    
    
    /**
     * This method is called to parse a quoted string.
     *
     * @return the processed string value.
     * @param token The process to extract the results from.
     */
    public static String parseQuotedString(StringTokenizer token)
    throws ServerException {
        try {
            StringBuffer result = new StringBuffer();
            String tokenValue = token.nextToken();
            boolean inQuotedString = false;
            if ((tokenValue.indexOf("\"") == 0) && (tokenValue.endsWith("\""))){
                return tokenValue.substring(1, tokenValue.length() - 1);
            }
            if (tokenValue.indexOf("\"") == 0) {
                inQuotedString = true;
                tokenValue = tokenValue.substring(1, tokenValue.length());
            }
            result.append(tokenValue);
            while (inQuotedString && token.hasMoreTokens()) {
                tokenValue = token.nextToken();
                if (tokenValue.endsWith("\"") && !tokenValue.endsWith("\\\"")) {
                    tokenValue = tokenValue.substring(0,
                            tokenValue.length() -1);
                    inQuotedString = false;
                } else if (tokenValue.equals("\"")) {
                    inQuotedString = false;
                    result.append(" ");
                    continue;
                }
                result.append(" ").append(tokenValue);
            }
            return result.toString();
        } catch (Exception ex) {
            log.error("Failed to process the quoted string : " +
                    ex.getMessage(),ex);
            throw new ServerException("Failed to process the quoted string : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the mode string value for the integer value.
     */
    public static String getFlag(Flags.Flag flag) throws ServerException {
        if (Flags.Flag.ANSWERED == flag) {
            return "\\Answered";
        } else if (Flags.Flag.DELETED == flag) {
            return "\\Deleted";
        } else if (Flags.Flag.DRAFT == flag) {
            return "\\Draft";
        } else if (Flags.Flag.FLAGGED == flag) {
            return "\\Flagged";
        } else if (Flags.Flag.RECENT == flag) {
            return "\\Recent";
        } else if (Flags.Flag.SEEN == flag) {
            return "\\Seen";
        } else if (Flags.Flag.USER == flag) {
            return "\\User";
        }
        log.error("Unknown flag value [" + flag + "]");
        throw new ServerException(
                "Unknown flag value [" + flag + "]");
    }
    
}
