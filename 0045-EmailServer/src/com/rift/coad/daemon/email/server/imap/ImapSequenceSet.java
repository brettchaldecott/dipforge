/*
 * EMailServer: The email server implementation.
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
 * ImapSequenceSet.java
 */

// package path
package com.rift.coad.daemon.email.server.imap;

// java import
import java.util.List;
import java.util.ArrayList;

// import server exception
import com.rift.coad.daemon.email.server.ServerException;

/**
 * This object is responsible for parsing the message sequence set string and
 * creating the appropriate in memory sequence set.
 *
 * @author brett chaldecott
 */
public class ImapSequenceSet {
    
    // private member variables
    private List sequenceSet = new ArrayList();
    
    /**
     * Creates a new instance of ImapSequenceSet
     */
    public ImapSequenceSet(String input) throws ServerException {
        String[] commaList = input.split("[,]");
        for (int commaIndex = 0; commaIndex < commaList.length; commaIndex++) {
            if (commaList[commaIndex].equals("")) {
                continue;
            }
            String[] colonList = commaList[commaIndex].split("[:]");
            if (colonList.length == 0) {
                continue;
            } else if (colonList.length == 1) {
                if ((colonList[0].equals("*")) && 
                        (commaIndex + 1 == commaList.length)) {
                    sequenceSet.add("*");
                } else if ((colonList[0].equals("*")) && 
                        (commaIndex + 1 != commaList.length)) {
                    throw new ServerException(
                            "The sequence is not correctly formated");
                } else {
                    sequenceSet.add(new Long(Long.parseLong(colonList[0])));
                }
            } else {
                long begin = Long.parseLong(colonList[0]);
                if ((colonList[1].equals("*")) && 
                        (commaIndex + 1 == commaList.length)) {
                    sequenceSet.add(new Long(begin));
                    sequenceSet.add("*");
                    continue;
                } else if ((colonList[1].equals("*")) && 
                        (commaIndex + 1 != commaList.length)) {
                    throw new ServerException(
                            "The sequence is not correctly formated");
                }
                long end = Long.parseLong(colonList[1]);
                for (long index = begin; index <= end; index++) {
                    sequenceSet.add(new Long(index));
                }
            }
        }
    }
    
    
    /**
     * Return the sequence set
     *
     * @return The sequence set.
     */
    public List getSequenceSet() {
        return sequenceSet;
    }
    
    
    /**
     * This method returns true if this value is in the sequence set.
     */
    public boolean inSequenceSet(long number) {
        if (sequenceSet.contains(new Long(number))) {
            return true;
        } else if ((sequenceSet.size() == 1) && sequenceSet.contains("*")) {
            return true;
        } else if ((sequenceSet.size() == 1) && (!sequenceSet.contains("*"))) {
            return false;
        } else if (!sequenceSet.contains("*")) {
            return false;
        }
        long lastPos = ((Long)sequenceSet.get(sequenceSet.size() - 2)).
                longValue();
        if (lastPos < number) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method sets the end number
     */
    public void setEnd(long number) throws ServerException {
        if ((sequenceSet.size() == 1) && (sequenceSet.contains("*"))) {
            throw new ServerException("No range information supplied");
        } else if ((sequenceSet.size() == 1) || !sequenceSet.contains("*")) {
            // do nothing
            return;
        }
        long lastPos = ((Long)sequenceSet.get(sequenceSet.size() - 2)).
                longValue();
        sequenceSet.remove(sequenceSet.size() - 1);
        for (long index = lastPos + 1; index < number; index++) {
            sequenceSet.add(new Long(index));
        }
        sequenceSet.add(new Long(number));
    }
}
