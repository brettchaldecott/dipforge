/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  2015 Burntjam
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
 * DateHelper.java
 */

// package imports
package com.rift.coad.rdf.semantic.util;

// java imports
import java.util.Date;
import java.text.SimpleDateFormat;

// coadunation imports
import com.rift.coad.rdf.semantic.Constants;

/**
 * This object is responsible for converting dates to and from strings and for
 * handling date values.
 *
 * @author brett chaldecott
 */
public class DateHelper {
    // private member variables
    private Date value;
    
    /**
     * This constructor sets the date value for the helper.
     *
     * @param value The value to generate the string representation from.
     */
    public DateHelper(Date value) {
        this.value = value;
    }

    /**
     * This method is responsible for parsing the string value.
     *
     * @param value The value to that has to be converted to string
     */
    public static Date parse(String value) throws UtilException {
        try {
            return new SimpleDateFormat(Constants.DATE_FORMAT).parse(value);
        } catch (Exception ex) {
            throw new UtilException("Failed to parse the date : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method converts the date format to a string.
     *
     * @return The formated string value.
     */
    public String toString() {
        return new SimpleDateFormat(Constants.DATE_FORMAT).format(value);
    }
}
