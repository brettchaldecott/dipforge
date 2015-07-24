/*
 * Email Server: The email server interface
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
 * ImapUtilsTest.java
 */

package com.rift.coad.daemon.email.server.imap;

import junit.framework.*;
import java.util.Date;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import javax.mail.Flags;
import com.sun.mail.imap.protocol.FLAGS;
import com.rift.coad.daemon.email.server.ServerException;

/**
 * This object performs a test on the imap utils.
 *
 * @author brett chaldecott
 */
public class ImapUtilsTest extends TestCase {
    
    public ImapUtilsTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of parseDate method, of class com.rift.coad.daemon.email.server.imap.ImapUtils.
     */
    public void testParseDate() throws Exception {
        System.out.println("parseDate");
        
        String date = "10-Jan-2007";
        
        Date result = ImapUtils.parseDate(date);
        String dateResult = new SimpleDateFormat("dd-MMM-yyyy").format(result);
        assertEquals(dateResult, date);
    }

    /**
     * Test of parseQuotedString method, of class com.rift.coad.daemon.email.server.imap.ImapUtils.
     */
    public void testParseQuotedString() throws Exception {
        System.out.println("parseQuotedString");
        
        
        StringTokenizer token = new StringTokenizer("\"bob was here\""," ");
        
        String expResult = "bob was here";
        String result = ImapUtils.parseQuotedString(token);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getFlag method, of class com.rift.coad.daemon.email.server.imap.ImapUtils.
     */
    public void testGetFlag() throws Exception {
        System.out.println("getFlag");
        
        Flags.Flag flag = Flags.Flag.SEEN;
        
        String expResult = "\\Seen";
        String result = ImapUtils.getFlag(flag);
        assertEquals(expResult, result);
        
    }
    
}
