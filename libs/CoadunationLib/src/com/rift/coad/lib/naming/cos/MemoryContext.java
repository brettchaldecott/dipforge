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
 * MemoryContext.java
 *
 * This is an in memory version of a context object.
 */

// package path
package com.rift.coad.lib.naming.cos;

// imports
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NameNotFoundException;

/**
 * This is an in memory version of a context object.
 *
 * @author Brett Chaldecott
 */
public class MemoryContext implements Context {
    
    // private member variables
    private Hashtable env = null;
    private Hashtable contextEnv = new Hashtable();
    private Name nameSpace = null;
    
    /** 
     * Creates a new instance of MemoryContext
     *
     * @param env The environment in which this context is running.
     */
    public MemoryContext(Hashtable env,Name nameSpace) {
        this.env = (Hashtable)env.clone();
        this.nameSpace = nameSpace;
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
        Object original = null;
        synchronized(env) {
            if (env.containsKey(propName)) {
                original = env.get(propName);
            }
            env.put(propName,propVal);
        }
        return original;
    }
    
    
    /**
     * Binds a name to an object.
     *
     * @param name The name of the object to bind.
     * @param obj The object to bind.
     * @exception NamingException
     */
    public void bind(Name name, Object obj) throws NamingException {
        if (name.size() <= 0) {
            throw new InvalidNameException("Invalid name has been passed");
        }
        String key = name.get(0);
        MemoryContext subContext = null;
        synchronized(contextEnv) {
            boolean contains = contextEnv.containsKey(key);
            if ((name.size() == 1) && contains) {
                throw new NameAlreadyBoundException("The name [" + key 
                        + "] is already bound");
            } else if (name.size() == 1 ) {
                contextEnv.put(key,obj);
                return;
            } else if (contains) {
                subContext = (MemoryContext)contextEnv.get(key);
            } else {
                subContext = new MemoryContext(env,composeName(nameSpace,
                        new NamingParser().parse(key)));
                contextEnv.put(key,subContext);
            }
        }
        subContext.bind(name.getSuffix(1),obj);
    }
    
    
    /**
     * Binds a name to an object.
     */
    public void bind(String name, Object obj) throws NamingException {
        bind(new NamingParser().parse(name),obj);
    }
    
    
    /**
     * Closes this context.
     */
    public void close()  throws NamingException {
        
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
        Name newName = (Name)prefix.clone();
        newName.addAll(name);
        return newName;
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
        return composeName(new NamingParser().parse(name),
                new NamingParser().parse(prefix)).toString();
    }
    
    
    /**
     * Creates and binds a new context to this context.
     *
     * @return The newly created context.
     * @param name The name of the new sub context.
     * @exception NamingException
     */
    public Context createSubcontext(Name name) throws NamingException {
        if (name.size() <= 0) {
            throw new InvalidNameException("Invalid name has been passed");
        }
        String key = name.get(0);
        MemoryContext subContext = null;
        synchronized(contextEnv) {
            if (contextEnv.containsKey(key)) {
                subContext = (MemoryContext)contextEnv.get(key);
            } else {
                subContext = new MemoryContext(env,composeName(
                        new NamingParser().parse(key),nameSpace));
                contextEnv.put(key,subContext);
            }
        }
        if (name.size() > 1) {
            return subContext.createSubcontext(name.getSuffix(1));
        }
        return subContext;
    }
    
    
    /**
     * Creates and binds a new context.
     *
     * @return The newly create sub context.
     * @exception name The name of the new sub context.
     * @exception NamingException
     */
    public Context createSubcontext(String name) throws NamingException {
        return createSubcontext(new NamingParser().parse(name));
    }
    
    
    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name The name of the sub context to remove.
     * @exception NamingException
     */
    public void destroySubcontext(Name name) throws NamingException {
        if (name.size() <= 0) {
            throw new InvalidNameException("Invalid name has been passed");
        }
        String key = name.get(0);
        MemoryContext subContext = null;
        synchronized(contextEnv) {
            boolean contains = contextEnv.containsKey(key);
            if ((name.size() == 1) && contains) {
                contextEnv.remove(key);
                return;
            } else if (contains) {
                subContext = (MemoryContext)contextEnv.get(key);
            } else {
                throw new NameNotFoundException("The name [" + key 
                        + "] not found");
            }
        }
        if (name.size() > 1) {
            subContext.destroySubcontext(name.getSuffix(1));
        }
    }
    
    
    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name The name of the context to destroy.
     */
    public void destroySubcontext(String name) throws NamingException {
        destroySubcontext(new NamingParser().parse(name));
    }
    
    
    /**
     * Retrieves the environment in effect for this context.
     *
     * @return The reference to the hash table.
     * @exception NamingException
     */
    public Hashtable getEnvironment() throws NamingException {
        return env;
    }
    
    
    /**
     * Retrieves the full name of this context within its own namespace.
     */
    public String getNameInNamespace() throws NamingException {
        return nameSpace.toString();
    }
    
    
    /**
     * Retrieves the parser associated with the named context.
     *
     * @return The reference to the name parser.
     * @param name The name to return the parser for.
     * @exception NamingException
     */
    public NameParser getNameParser(Name name) throws NamingException {
        return new NamingParser();
    }
    
    
    /**
     * Retrieves the parser associated with the named context.
     */
    public NameParser getNameParser(String name) throws NamingException {
        return new NamingParser();
    }
    
    
    /**
     * Enumerates the names bound in the named context, along with the class 
     * names of objects bound to them.
     */
    public NamingEnumeration list(Name name) throws NamingException {
        return new MemoryNamingEnumeration(name.getAll());
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
        return new MemoryNamingEnumeration(new NamingParser().parse(name).
                getAll());
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
        if (name.size() <= 0) {
            synchronized(contextEnv) {
                return new MemoryNamingEnumeration(contextEnv.elements());
            }
        }
        String key = name.get(0);
        MemoryContext subContext = null;
        synchronized(contextEnv) {
            if (contextEnv.containsKey(key)) {
                subContext = (MemoryContext)contextEnv.get(key);
            } else {
                throw new NameNotFoundException("The name [" + key 
                        + "] not found");
            }
        }
        return subContext.listBindings(name.getSuffix(1));
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
        return listBindings(new NamingParser().parse(name));
    }
    
    
    /**
     * Retrieves the named object.
     *
     * @return The named object.
     * @param name The name to retrieve the object for.
     * @exception NamingException
     */
    public Object lookup(Name name) throws NamingException {
        if (name.size() <= 0) {
            throw new InvalidNameException("Invalid name has been passed");
        }
        String key = name.get(0);
        MemoryContext subContext = null;
        synchronized(contextEnv) {
            boolean contains = contextEnv.containsKey(key);
            if ((name.size() == 1) && contains) {
                return contextEnv.get(key);
            } else if (contains) {
                subContext = (MemoryContext)contextEnv.get(key);
            } else {
                throw new NameNotFoundException("The name [" + key 
                        + "] not found");
            }
        }
        return subContext.lookup(name.getSuffix(1));
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
        Name currentName = name;
        while(true) {
            Object result = lookup(currentName);
            if (!(result instanceof Name)) {
                return result;
            }
            currentName = (Name)result;
        }
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
        return lookupLink(new NamingParser().parse(name));
    }
    
    
    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name The name to rebind.
     * @param obj The object to rebind.
     * @exception NamingException
     */
    public void rebind(Name name, Object obj) throws NamingException {
        if (name.size() <= 0) {
            throw new InvalidNameException("Invalid name has been passed");
        }
        String key = name.get(0);
        MemoryContext subContext = null;
        synchronized(contextEnv) {
            boolean contains = contextEnv.containsKey(key);
            if (name.size() == 1 ) {
                contextEnv.put(key,obj);
                return;
            } else if (contains) {
                subContext = (MemoryContext)contextEnv.get(key);
            } else {
                subContext = new MemoryContext(env,composeName(
                        new NamingParser().parse(key),nameSpace));
                contextEnv.put(key,subContext);
            }
        }
        subContext.rebind(name.getSuffix(1),obj);
    }
    
    
    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name The name to rebind.
     * @param obj The object to rebind.
     * @exception NamingException
     */
    public void rebind(String name, Object obj) throws NamingException {
        rebind(new NamingParser().parse(name),obj);
    }
    
    
    /**
     * Removes an environment property from the environment of this context.
     *
     * @return The original value.
     * @param propName The name of the entry to remove from the environment.
     * @exception NamingException
     */
    public Object removeFromEnvironment(String propName) throws NamingException {
        Object originalValue = null;
        synchronized(env) {
            if (env.containsKey(propName)) {
                originalValue = env.get(propName);
                env.remove(propName);
            }
        }
        return originalValue;
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
        Object value = lookup(oldName);
        unbind(oldName);
        bind(newName,value);
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
        rename(new NamingParser().parse(oldName),new NamingParser().
                parse(newName));
    }
    
    
    /**
     * Unbinds the named object.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(Name name) throws NamingException {
        if (name.size() <= 0) {
            throw new InvalidNameException("Invalid name has been passed");
        }
        String key = name.get(0);
        MemoryContext subContext = null;
        synchronized(contextEnv) {
            boolean contains = contextEnv.containsKey(key);
            if (contains && (name.size() == 1 )) {
                contextEnv.remove(key);
                return;
            } else if (contains) {
                subContext = (MemoryContext)contextEnv.get(key);
            } else {
                throw new NameNotFoundException("The name [" + name.toString() 
                        + "] was not found.");
            }
        }
        subContext.unbind(name.getSuffix(1));
    }
    
    
    /**
     * Unbinds the named objec.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(String name) throws NamingException {
        unbind(new NamingParser().parse(name));
    }
}
