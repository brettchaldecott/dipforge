/*
 * <Add library description here>
 * Copyright (C) 2006  Rift IT Contracting
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
 * CoadunationContext.java
 *
 * The implementation of the Coadunation client context. Used to lookup RMI
 * information.
 *
 * Revision: $ID
 */

// package path
package com.rift.coad.client.naming;

// java imports
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.CompoundName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import org.omg.CORBA.ORB;

// logging import
import org.apache.log4j.Logger;

import com.rift.coad.lib.interceptor.credentials.Login;

/**
 * The implementation of the Coadunation client context. Used to lookup RMI
 * information.
 *
 * @author Brett Chaldecott
 */
public class CoadunationContext implements Context {
    
    /**
     * This object is responsible for parsing the string value passed in. Valid
     * names must be formated as "this/is/valid".
     *
     * @author Brett Chaldecott
     */
    public class NamingParser implements NameParser,Serializable {
        
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
    
    // The classes static variables
    private static Properties syntax = new Properties();
    static {
        syntax.setProperty("jndi.syntax.direction","left_to_right");
        syntax.setProperty("jndi.syntax.separator","/");
        syntax.setProperty("jndi.syntax.ignorecase","false");
        syntax.setProperty("jndi.syntax.escape","\\");
    }
    protected static Logger log =
            Logger.getLogger(CoadunationContext.class.getName());
    
    // class private member variables
    private ORB orb = null;
    private Hashtable env = null;
    private Context primaryContext = null;
    private Name prefix = null;
    
    /** 
     * Creates a new instance of CoadunationContext 
     *
     * @param env The environment.
     * @param orb The orb reference.
     */
    public CoadunationContext(ORB orb,Hashtable env) {
        this.orb = orb;
        this.env = env;
        try {
            prefix = new NamingParser().parse("");
        } catch (Exception ex) {
            log.error("Failed to parse the name : " + ex.getMessage(),ex);
        }
    }
    
    /** 
     * Creates a new instance of CoadunationContext 
     */
    public CoadunationContext(Hashtable env,Name prefix) {
        this.env = env;
        this.prefix = prefix;
    }
    
    /**
     * Adds a new environment property to the environment of this context.
     *
     * @return The previous value of the property or null.
     * @param propName The property to replace or add.
     * @param propValue The new property value.
     */
    public Object addToEnvironment(String propName, Object propVal) throws
            NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Binds a name to an object.
     *
     * @param name The name of the object to bind.
     * @param obj The object to bind.
     * @exception NamingException
     */
    public void bind(Name name, Object obj) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Binds a name to an object.
     */
    public void bind(String name, Object obj) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Closes this context.
     */
    public void close()  throws NamingException {
        if (primaryContext != null) {
            primaryContext.close();
            primaryContext = null;
        }
    }
    
    
    /**
     * Composes the name of this context with a name relative to this context.
     *
     * @return The compisit name.
     * @param name The name to add to the prefix.
     * @param prefix The prefix of the current context.
     * @exception NamingException
     */
    public Name composeName(Name name, Name prefix) throws NamingException {
        return null;
    }
    
    
    /**
     * Composes the name of this context with a name relative to this context.
     *
     * @return The string version of the composed name.
     * @param name The name to add to the preffix.
     * @param prefix The prefix for this context.
     */
    public String composeName(String name, String prefix) throws
            NamingException {
        return null;
    }
    
    
    /**
     * Creates and binds a new context to this context.
     *
     * @return The newly created context.
     * @param name The name of the new sub context.
     * @exception NamingException
     */
    public Context createSubcontext(Name name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Creates and binds a new context.
     *
     * @return The newly create sub context.
     * @exception name The name of the new sub context.
     * @exception NamingException
     */
    public Context createSubcontext(String name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name The name of the sub context to remove.
     * @exception NamingException
     */
    public void destroySubcontext(Name name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name The name of the context to destroy.
     */
    public void destroySubcontext(String name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Retrieves the environment in effect for this context.
     *
     * @return The reference to the hash table.
     * @exception NamingException
     */
    public Hashtable getEnvironment() throws NamingException {
        return null;
    }
    
    
    /**
     * Retrieves the full name of this context within its own namespace.
     */
    public String getNameInNamespace() throws NamingException {
        return null;
    }
    
    
    /**
     * Retrieves the parser associated with the named context.
     *
     * @return The reference to the name parser.
     * @param name The name to return the parser for.
     * @exception NamingException
     */
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }
    
    
    /**
     * Retrieves the parser associated with the named context.
     */
    public NameParser getNameParser(String name) throws NamingException {
        return null;
    }
    
    
    /**
     * Enumerates the names bound in the named context, along with the class
     * names of objects bound to them.
     */
    public NamingEnumeration list(Name name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Enumerates the names bound in the named context, along with the class
     * names of objects bound to them.
     *
     * @return The list of names bound to this context.
     * @param name The list of names.
     * @exception NamingException
     */
    public NamingEnumeration list(String name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Enumerates the names bound in the named context, along with the objects
     * bound to them.
     *
     * @return The list of bindings for the name
     * @param name The name to perform the search below.
     * @exception NamingException
     */
    public NamingEnumeration listBindings(Name name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Enumerates the names bound in the named context, along with the objects
     * bound to them.
     *
     * @return The list of binding for the name.
     * @param name The name to perform the search for.
     * @exception NamingException
     */
    public NamingEnumeration listBindings(String name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Retrieves the named object.
     *
     * @return The named object.
     * @param name The name to retrieve the object for.
     * @exception NamingException
     */
    public Object lookup(Name name) throws NamingException {
        Context context = getContext();
        String currentName = null;
        try {
            if (this.env.containsKey(CoadunationInitialContextFactory.USERNAME) && 
                    this.env.containsKey(
                    CoadunationInitialContextFactory.PASSWORD)) {
                CoadunationInitialContextFactory.userLogin.set(
                        new Login((String)this.env.get(
                        CoadunationInitialContextFactory.USERNAME), 
                        (String)this.env.get(
                        CoadunationInitialContextFactory.PASSWORD)));
            } else {
                CoadunationInitialContextFactory.userLogin.set(null);
            }
            Name composedName = (Name)prefix.clone();
            composedName.addAll(name);
            for (int index =0; index < (composedName.size() - 1); index++ ) {
                currentName = composedName.get(index);
                context = (Context)context.lookup(currentName);
            }
            currentName = composedName.get(composedName.size() - 1);
            Object result = context.lookup(currentName);
            if (result instanceof com.sun.corba.se.impl.corba.CORBAObjectImpl) {
                com.sun.corba.se.impl.corba.CORBAObjectImpl obj = 
                        (com.sun.corba.se.impl.corba.CORBAObjectImpl)result;
                if (result instanceof Context) {
                    return new CoadunationContext(env,composedName);
                }
            }
            return result;
        } catch (NamingException ex) {
            this.invalidateContext();
            log.error("Failed to lookup the object [" + currentName + "] :"  + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            this.invalidateContext();
            log.error("Failed to lookup the object [" + currentName + "] :"  + 
                    ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to lookup the object [" + currentName + "] :"  + 
                    ex.getMessage());
        }
    }
    
    
    /**
     * Retrieves the named object.
     *
     * @return The object to retrieve by name.
     * @param name The name of the object to retrieve.
     * @exception NamingException
     */
    public Object lookup(String name) throws NamingException {
        return lookup(new NamingParser().parse(name));
    }
    
    
    /**
     * Retrieves the named object, following links except for the terminal
     * atomic component of the name.
     *
     * @return The object to retrieve.
     * @param name The name of the object to lookup.
     * @exception NamingException
     */
    public Object lookupLink(Name name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Retrieves the named object, following links except for the terminal
     * atomic component of the name.
     *
     * @return The results of the lookup link.
     * @param name The name of the object to lookup.
     * @exception NamingException
     */
    public Object lookupLink(String name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name The name to rebind.
     * @param obj The object to rebind.
     * @exception NamingException
     */
    public void rebind(Name name, Object obj) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name The name to rebind.
     * @param obj The object to rebind.
     * @exception NamingException
     */
    public void rebind(String name, Object obj) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Removes an environment property from the environment of this context.
     *
     * @param propName The name of the entry to remove from the environment.
     * @exception NamingException
     */
    public Object removeFromEnvironment(String propName) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Binds a new name to the object bound to an old name, and unbinds the old
     * name.
     *
     * @param oldName The old name to rename.
     * @param newName The name to replace it with.
     * @exception NamingException
     */
    public void rename(Name oldName, Name newName) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Binds a new name to the object bound to an old name, and unbinds the old
     * name.
     *
     * @param oldName The old name to rename.
     * @param newName The name to replace it with.
     * @exception NamingException
     */
    public void rename(String oldName, String newName) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Unbinds the named object.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(Name name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * Unbinds the named objec.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(String name) throws NamingException {
        throw new NamingException("Not implemented");
    }
    
    
    /**
     * This method returns a valid context
     */
    public synchronized Context getContext() throws NamingException {
        if (primaryContext != null) {
            return primaryContext;
        }
        return primaryContext = new InitialContext(env);
    }
    
    
    /**
     * This method invalidates a bad context.
     */
    public synchronized void invalidateContext() {
        primaryContext = null;
    }
}
