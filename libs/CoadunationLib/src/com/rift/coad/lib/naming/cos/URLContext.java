/*
 * CoadunationLib: The coaduntion implementation library.
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
 * URLContext.java
 *
 * This object is responsible for implementing the JNDI context within
 * Coadunation.
 */

// package path
package com.rift.coad.lib.naming.cos;

// logging import
import org.apache.log4j.Logger;

// imports
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * This object is responsible for implementing the JNDI context within
 * Coadunation.
 *
 * @author Brett Chaldecott
 */
public class URLContext implements Context {
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(URLContext.class.getName());
    
    // class private member variables
    private Hashtable env = null;
    private MasterContext masterContext = null;
    private Name prefix = null;
    
    /**
     * Creates a new instance of URLContext 
     *
     * @param env The environment of the cos context.
     */
    public URLContext(Hashtable env) throws NamingException {
        try {
            this.env = env;
            masterContext = CosNamingContextManager.getInstance().
                    getMasterContext();
            prefix = new NamingParser().parse("");
        } catch (Exception ex) {
            throw new NamingException("Failed to create a new URL context");
        }
    }
    
    /**
     * Creates a new instance of URLContext 
     * 
     * @param name The prefix name of this context.
     * @param env The environment of the cos context.
     * @exception NamingException
     */
    public URLContext(Name name, Hashtable env) throws NamingException {
        try {
            this.env = env;
            masterContext = CosNamingContextManager.getInstance().
                    getMasterContext();
            this.prefix = name;
        } catch (Exception ex) {
            throw new NamingException("Failed to create a new URL context");
        }
    }
    
    
    /**
     * 
     * Creates a new instance of URLContext 
     * 
     * 
     * @param env The environment of the cos context.
     */
    public URLContext(Hashtable env,MasterContext masterContext,Name prefix) 
            throws NamingException {
        this.env = env;
        this.masterContext = masterContext;
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
        return masterContext.addToEnvironment(propName,propVal);
    }
    
    
    /**
     * Binds a name to an object.
     *
     * @param name The name of the object to bind.
     * @param obj The object to bind.
     * @exception NamingException
     */
    public void bind(Name name, Object obj) throws NamingException {
        Name composedName = masterContext.composeName(name,prefix);
        masterContext.bind(composedName,obj);
    }
    
    
    /**
     * Binds a name to an object.
     */
    public void bind(String name, Object obj) throws NamingException {
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        masterContext.bind(composedName,obj);
    }
    
    
    /**
     * Closes this context.
     */
    public void close()  throws NamingException {
        masterContext.close();
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
        return masterContext.composeName(name,prefix);
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
        return masterContext.composeName(name,prefix);
    }
    
    
    /**
     * Creates and binds a new context to this context.
     *
     * @return The newly created context.
     * @param name The name of the new sub context.
     * @exception NamingException
     */
    public Context createSubcontext(Name name) throws NamingException {
        Name composedName = masterContext.composeName(name,prefix);
        masterContext.createSubcontext(composedName);
        return new URLContext(env,masterContext,composedName);
    }
    
    
    /**
     * Creates and binds a new context.
     *
     * @return The newly create sub context.
     * @exception name The name of the new sub context.
     * @exception NamingException
     */
    public Context createSubcontext(String name) throws NamingException {
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        masterContext.createSubcontext(composedName);
        return new URLContext(env,masterContext,composedName);
    }
    
    
    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name The name of the sub context to remove.
     * @exception NamingException
     */
    public void destroySubcontext(Name name) throws NamingException {
        Name composedName = masterContext.composeName(name,prefix);
        masterContext.destroySubcontext(composedName);
    }
    
    
    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name The name of the context to destroy.
     */
    public void destroySubcontext(String name) throws NamingException {
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        masterContext.destroySubcontext(composedName);
    }
    
    
    /**
     * Retrieves the environment in effect for this context.
     *
     * @return The reference to the hash table.
     * @exception NamingException
     */
    public Hashtable getEnvironment() throws NamingException {
        return masterContext.getEnvironment();
    }
    
    
    /**
     * Retrieves the full name of this context within its own namespace.
     */
    public String getNameInNamespace() throws NamingException {
        return masterContext.getNameInNamespace();
    }
    
    
    /**
     * Retrieves the parser associated with the named context.
     *
     * @return The reference to the name parser.
     * @param name The name to return the parser for.
     * @exception NamingException
     */
    public NameParser getNameParser(Name name) throws NamingException {
        return masterContext.getNameParser(name);
    }
    
    
    /**
     * Retrieves the parser associated with the named context.
     */
    public NameParser getNameParser(String name) throws NamingException {
        return masterContext.getNameParser(name);
    }
    
    
    /**
     * Enumerates the names bound in the named context, along with the class 
     * names of objects bound to them.
     */
    public NamingEnumeration list(Name name) throws NamingException {
        Name composedName = masterContext.composeName(name,prefix);
        return masterContext.list(composedName);
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
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        return masterContext.list(composedName);
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
        Name composedName = masterContext.composeName(name,prefix);
        return masterContext.listBindings(composedName);
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
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        return masterContext.listBindings(composedName);
    }
    
    
    /**
     * Retrieves the named object.
     *
     * @return The named object.
     * @param name The name to retrieve the object for.
     * @exception NamingException
     */
    public Object lookup(Name name) throws NamingException {
        Name composedName = masterContext.composeName(name,prefix);
        Object result = masterContext.lookup(composedName);
        if (result instanceof Context) {
            return new URLContext(env,masterContext,composedName);
        }
        return result;
    }
    
    
    /**
     * Retrieves the named object.
     *
     * @return The object to retrieve by name.
     * @param name The name of the object to retrieve.
     * @exception NamingException
     */
    public Object lookup(String name) throws NamingException {
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        Object result = masterContext.lookup(composedName);
        if (result instanceof Context) {
            return new URLContext(env,masterContext,composedName);
        }
        return result;
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
        Name composedName = masterContext.composeName(name,prefix);
        Object result = masterContext.lookupLink(composedName);
        if (result instanceof Context) {
            return new URLContext(env,masterContext,composedName);
        }
        return result;
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
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        Object result = masterContext.lookupLink(composedName);
        if (result instanceof Context) {
            return new URLContext(env,masterContext,composedName);
        }
        return result;
    }
    
    
    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name The name to rebind.
     * @param obj The object to rebind.
     * @exception NamingException
     */
    public void rebind(Name name, Object obj) throws NamingException {
        Name composedName = masterContext.composeName(name,prefix);
        masterContext.rebind(composedName,obj);
    }
    
    
    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name The name to rebind.
     * @param obj The object to rebind.
     * @exception NamingException
     */
    public void rebind(String name, Object obj) throws NamingException {
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        masterContext.rebind(composedName,obj);
    }
    
    
    /**
     * Removes an environment property from the environment of this context.
     *
     * @param propName The name of the entry to remove from the environment.
     * @exception NamingException
     */
    public Object removeFromEnvironment(String propName) throws NamingException {
        return masterContext.removeFromEnvironment(propName);
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
        Name oldComposedName = masterContext.composeName(oldName,prefix);
        Name newComposedName = masterContext.composeName(newName,prefix);
        masterContext.rename(oldComposedName,newComposedName);
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
        Name oldComposedName = masterContext.composeName(
                new NamingParser().parse(oldName),prefix);
        Name newComposedName = masterContext.composeName(
                new NamingParser().parse(newName),prefix);
        masterContext.rename(oldComposedName,newComposedName);
    }
    
    
    /**
     * Unbinds the named object.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(Name name) throws NamingException {
        Name composedName = masterContext.composeName(name,prefix);
        masterContext.unbind(composedName);
    }
    
    
    /**
     * Unbinds the named objec.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(String name) throws NamingException {
        Name composedName = masterContext.composeName(
                new NamingParser().parse(name),prefix);
        masterContext.unbind(composedName);
    }
          
    
}
