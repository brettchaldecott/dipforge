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
 * This is the master context responsible for managing both the URL in memory
 * contexts and the Cos contexts.
 */

// package path
package com.rift.coad.lib.naming.cos;

// imports
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.Reference;
import javax.naming.OperationNotSupportedException;
import javax.naming.spi.ObjectFactory;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.naming.*;
import com.rift.coad.lib.thread.CoadunationThreadGroup;

/**
 * This is the master context responsible for managing both the URL in memory
 * contexts and the Cos contexts.
 *
 * @author Brett Chaldecott
 */
public class MasterContext implements Context {
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(MasterContext.class.getName());
    
    // class private member variables
    private Hashtable env = null;
    private OrbManager orbManager = null;
    private CosContext cosContext = null;
    private MemoryContext masterMemoryContext = null;
    private Map classLoaderMemoryContexts = new HashMap();
    
    
    /**
     * Creates a new instance of MasterContext
     * 
     * @param env The environment of the cos context.
     *
     * @param env The global has table for this environment.
     * @param threadGroup The thread group for this object.
     * @param orbManager The reference to the orb manager.
     * @param instanceId The id of the coadunation server
     */
    public MasterContext(Hashtable env, CoadunationThreadGroup threadGroup,
            OrbManager orbManager, String instanceId) throws 
            com.rift.coad.lib.naming.NamingException {
        try {
            this.env = (Hashtable)env.clone();
            this.orbManager = orbManager;
            this.cosContext = new CosContext(env,threadGroup,orbManager,
                    instanceId);
            this.masterMemoryContext = new MemoryContext(env,new NamingParser().
                    parse(""));
        } catch (javax.naming.NamingException ex) {
            log.error("Failed to instanciate the Master Context because : " + 
                    ex.getMessage(),ex);
            throw new com.rift.coad.lib.naming.NamingException("Failed to " +
                    "instanciate the Master Context because : " + 
                    ex.getMessage(),ex);
        }
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
        synchronized(env) {
            Object origValue = null;
            if (env.containsKey(propName)) {
                origValue = env.get(propName);
            }
            env.put(propName,propVal);
            return origValue;
        }
    }
    
    
    /**
     * Binds a name to an object.
     *
     * @param name The name of the object to bind.
     * @param obj The object to bind.
     * @exception NamingException
     */
    public void bind(Name name, Object obj) throws NamingException {
        Context context = getContext(name);
        context.bind(name,obj);
    }
    
    
    /**
     * Binds a name to an object.
     *
     * @param name The name to bind.
     * @param obj The object value to bind to the name.
     * @exception NamingException
     */
    public void bind(String name, Object obj) throws NamingException {
        Context context = getContext(name);
        context.bind(name,obj);
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
        return composeName(new NamingParser().parse(name),new NamingParser().
                parse(prefix)).toString();
    }
    
    
    /**
     * Creates and binds a new context to this context.
     *
     * @return The newly created context.
     * @param name The name of the new sub context.
     * @exception NamingException
     */
    public Context createSubcontext(Name name) throws NamingException {
        Context context = getContext(name);
        return context.createSubcontext(name);
    }
    
    
    /**
     * Creates and binds a new context.
     *
     * @return The newly create sub context.
     * @exception name The name of the new sub context.
     * @exception NamingException
     */
    public Context createSubcontext(String name) throws NamingException {
        Context context = getContext(name);
        return context.createSubcontext(name);
    }
    
    
    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name The name of the sub context to remove.
     * @exception NamingException
     */
    public void destroySubcontext(Name name) throws NamingException {
        Context context = getContext(name);
        context.destroySubcontext(name);
    }
    
    
    /**
     * Destroys the named context and removes it from the namespace.
     *
     * @param name The name of the context to destroy.
     */
    public void destroySubcontext(String name) throws NamingException {
        Context context = getContext(name);
        context.destroySubcontext(name);
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
        return new NamingParser().parse("").toString();
    }
    
    
    /**
     * Retrieves the parser associated with the named context.
     *
     * @return The reference to the name parser.
     * @param name The name to return the parser for.
     * @exception NamingException
     */
    public NameParser getNameParser(Name name) throws NamingException {
        Context context = getContext(name);
        return context.getNameParser(name);
    }
    
    
    /**
     * Retrieves the parser associated with the named context.
     */
    public NameParser getNameParser(String name) throws NamingException {
        Context context = getContext(name);
        return context.getNameParser(name);
    }
    
    
    /**
     * Enumerates the names bound in the named context, along with the class 
     * names of objects bound to them.
     */
    public NamingEnumeration list(Name name) throws NamingException {
        Context context = getContext(name);
        return context.list(name);
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
        Context context = getContext(name);
        return context.list(name);
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
        Context context = getContext(name);
        return context.listBindings(name);
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
        Context context = getContext(name);
        return context.listBindings(name);
    }
    
    
    /**
     * Retrieves the named object.
     *
     * @return The named object.
     * @param name The name to retrieve the object for.
     * @exception NamingException
     */
    public Object lookup(Name name) throws NamingException {
        Context context = getContext(name);
        try {
            return processResult(context.lookup(name),name);
        } catch (javax.naming.NameNotFoundException ex) {
            if (context != masterMemoryContext) {
                return processResult(masterMemoryContext.lookup(name),name);
            }
            throw ex;
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
        Context context = getContext(name);
        try {
            return processResult(context.lookup(name),name);
        } catch (javax.naming.NameNotFoundException ex) {
            if (context != masterMemoryContext) {
                return processResult(masterMemoryContext.lookup(name),name);
            }
            throw ex;
        }
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
        Context context = getContext(name);
        try {
            return processResult(context.lookupLink(name),name);
        } catch (javax.naming.NameNotFoundException ex) {
            if (context != masterMemoryContext) {
                return processResult(masterMemoryContext.lookupLink(name),name);
            }
            throw ex;
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
        Context context = getContext(name);
        try {
            return processResult(context.lookupLink(name),name);
        } catch (javax.naming.NameNotFoundException ex) {
            if (context != masterMemoryContext) {
                return processResult(masterMemoryContext.lookupLink(name),name);
            }
            throw ex;
        }
    }
    
    
    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name The name to rebind.
     * @param obj The object to rebind.
     * @exception NamingException
     */
    public void rebind(Name name, Object obj) throws NamingException {
        Context context = getContext(name);
        context.rebind(name,obj);
    }
    
    
    /**
     * Binds a name to an object, overwriting any existing binding.
     *
     * @param name The name to rebind.
     * @param obj The object to rebind.
     * @exception NamingException
     */
    public void rebind(String name, Object obj) throws NamingException {
        Context context = getContext(name);
        context.rebind(name,obj);
    }
    
    
    /**
     * Removes an environment property from the environment of this context.
     *
     * @param propName The name of the entry to remove from the environment.
     * @exception NamingException
     */
    public Object removeFromEnvironment(String propName) throws NamingException {
        synchronized(env) {
            Object original = null;
            if (env.containsKey(propName)) {
                original = env.get(propName);
                env.remove(propName);
            }
            return original;
        }
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
        Context oldContext = getContext(oldName);
        Context newContext = getContext(newName);
        Object ref = oldContext.lookup(oldName);
        newContext.bind(newName,ref);
        oldContext.unbind(oldName);
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
        Context oldContext = getContext(oldName);
        Context newContext = getContext(newName);
        Object ref = oldContext.lookup(oldName);
        newContext.bind(newName,ref);
        oldContext.unbind(oldName);
    }
    
    
    /**
     * Unbinds the named object.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(Name name) throws NamingException {
        Context context = getContext(name);
        context.unbind(name);
    }
    
    
    /**
     * Unbinds the named objec.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(String name) throws NamingException {
        Context context = getContext(name);
        context.unbind(name);
    }
    
    
    /**
     * This method is called to init the context for a class loader.
     *
     * @exception NamingException
     */
    public void initContext() throws com.rift.coad.lib.naming.NamingException {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            synchronized(classLoaderMemoryContexts) {
                classLoaderMemoryContexts.put(loader,new MemoryContext(env,
                        new NamingParser().parse("")));
            }
        } catch (Exception ex) {
            log.error("Failed to init the context for a class loader : " +
                    ex.getMessage(),ex);
            throw new com.rift.coad.lib.naming.NamingException(
                    "Failed to init the context for a class loader : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to release the context for class loader.
     */
    public void releaseContext() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        synchronized(classLoaderMemoryContexts) {
            classLoaderMemoryContexts.remove(loader);
        }
    }
    
    
    /**
     * This method terminates the processing of the MasterContext by terminating
     * the processing of its sub contexts
     */
    public void terminate() {
        cosContext.terminate();
    }
    
    
    /**
     * This method returns the context object responsible for managing the
     * given name.
     */
    private Context getContext(String name) throws NamingException {
        return getContext(new NamingParser().parse(name));
    }
    
    
    /**
     * This method returns the context object responsible for managing the
     * given name.
     */
    private Context getContext(Name name) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        // check if the naming being supplied is a JNDI standard url or if 
        // it should go into the COS naming service
        if ((!name.get(0).equals(NamingConstants.JAVA_JNDI_PREFIX)) &&
                (!name.get(0).equals(NamingConstants.SIMPLE_JAVA_JNDI_PREFIX)))
        {
            return cosContext;
        }
        // look for a local context to support the lookup
        synchronized(classLoaderMemoryContexts) {
            if (classLoaderMemoryContexts.containsKey(loader)) {
                return (Context)classLoaderMemoryContexts.get(loader);
            }
        }
        // return the master memory context
        return masterMemoryContext;
    }
    
    
    /**
     * This method process the result of the call
     *
     * @return The result of the processing.
     * @param result The result to process.
     */
    private Object processResult(Object result,String name) throws NamingException {
        return processResult(result, new NamingParser().parse(name));
    }
    
    /**
     * This method process the result of the object lookup.
     *
     * @return The result of the processing.
     * @param result The result to process.
     */
    private Object processResult(Object result,Name name) throws NamingException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        try {
            if (result instanceof Reference) {
                Reference ref = (Reference)result;
                ObjectFactory objFactory = (ObjectFactory)(Class.forName(
                        ref.getFactoryClassName())).newInstance();
                return objFactory.getObjectInstance(ref,name,this,
                        this.getEnvironment());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to process the result : " + 
                    ex.getMessage(),ex);
            throw new NamingException("Failed to process the result : " + 
                    ex.getMessage());
        } finally {
            // reset the class loader
            Thread.currentThread().setContextClassLoader(loader);
        }
    }
}
