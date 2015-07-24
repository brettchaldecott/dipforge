/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * NamingParser.java
 *
 * This object is responsible for parsing the string value passed in. Valid
 * names must be formated as "this/is/valid".
 */

// package path
package com.rift.coad.lib.naming.cos;

// java imports
import java.util.Properties;
import java.io.Serializable;
import javax.naming.CompoundName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;


/**
 * This object is responsible for parsing the string value passed in. Valid
 * names must be formated as "this/is/valid".
 *
 * @author Brett Chaldecott
 */
public class NamingParser implements NameParser,Serializable {
    
    // the synax value
    private static Properties syntax = new Properties();
    
    
    static {
        syntax.setProperty("jndi.syntax.direction","left_to_right");
        syntax.setProperty("jndi.syntax.separator","/");
        syntax.setProperty("jndi.syntax.ignorecase","false");
        syntax.setProperty("jndi.syntax.escape","\\");
    }
    
    /** 
     * Creates a new instance of NamingParser 
     */
    public NamingParser() {
    }
    
    
    /**
     * The method responsible for parsing the string value into a name value.
     *
     * @return The parse name object.
     * @param name The name to parse.
     * @exception NamingException
     */
    public Name parse(String name) throws NamingException {
        return new CompoundName(name,syntax);
    }
}
