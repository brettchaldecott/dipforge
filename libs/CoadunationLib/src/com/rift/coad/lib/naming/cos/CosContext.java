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
 * CosContext.java
 *
 * This object wrapps the complexity of speaking to the Cos Naming Server.
 */

// package path
package com.rift.coad.lib.naming.cos;

// java imports
import java.io.Serializable;
import java.rmi.Remote;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.rmi.PortableRemoteObject;
import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.PortableServer.POA;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyFactory;
import org.omg.DynamicAny.DynAnyFactoryHelper;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import javax.rmi.CORBA.Util;

// logging import
import org.apache.log4j.Logger;

// carol imports
// We use the carol objects to store none org.omg.CORBA.Object values in the
// Cos naming server. It is not recommened that information other than corba
// objects get stored in the cos naming server but this gives us a way to do it
// if someone requires this functionality.
import org.objectweb.carol.jndi.wrapping.JNDIRemoteResource;
import org.objectweb.carol.jndi.wrapping.JNDIReferenceWrapper;
import org.objectweb.carol.jndi.wrapping.JNDIResourceWrapper;

// coadunation imports
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.naming.NamingConstants;
import com.rift.coad.lib.naming.OrbManager;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.naming.NamingDirector;

/**
 * This object wrapps the complexity of speaking to the Cos Naming Server.
 *
 * @author Brett Chaldecott
 */
public class CosContext implements Context {
    
    /**
     * The class responsible for binding this nameserver context to the master
     * nameserver context.
     */
    public class InstanceBinder extends BasicThread {
        
        // the classes private member variables
        private ThreadStateMonitor stateMonitor = new 
                ThreadStateMonitor(60 * 1000);
        
        /**
         * The constructor of the instance binder thread.
         *
         * @exception Exception
         */
        public InstanceBinder() throws Exception {
            
        }
        
        
        /**
         * This method is responsible for performing the processing in the cos
         * context.
         */
        public void process() {
            while(!stateMonitor.isTerminated()) {
                try {
                    log.info("Making a connection to the name server context");
                    NamingContext namingContext = NamingContextHelper.narrow(
                            orbManager.getORB().string_to_object(instanceCosURL));
                    log.info("Binding to the primary context [" + masterCosURL
                            + "]");
                    Context context = getInitialContext(instanceURL);
                    
                    log.info("Loop through the instance url and add [" + 
                            instanceURL.toString() + "]");
                    for (int index = 0; index < instanceURL.size() - 1; index++){
                        try {
                            context = (Context)context.lookup(
                                    instanceURL.get(index));
                        } catch (javax.naming.NameNotFoundException ex) {
                            context = context.createSubcontext(
                                    instanceURL.get(index));
                        }
                    }
                    log.info("Add entry to the context");
                    context.rebind(instanceURL.get(instanceURL.size() -1),
                            namingContext);
                    log.info("Registered with master Cos Naming service");
                    break;
                } catch (Exception ex) {
                    log.error("Failed to bind to the mater cos name server : " +
                            ex.getMessage(),ex);
                }
                stateMonitor.monitor();
            }
        }
        
        
        /**
         * The terminate method.
         */
        public void terminate() {
            stateMonitor.terminate(true);
        }
    }
            
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(CosContext.class.getName());
    
    // class constants
    private final static String SUN_COS_CONTEXT_FACTORY = 
            "com.sun.jndi.cosnaming.CNCtxFactory";
    private final static String PRIMARY = "primary";
    private final static String INSTANCE_COS_URL = "instance_cos_url";
    private final static String MASTER_COS_URL = "master_cos_url";
    private final static String USER = "cos_user";
            
    // class private member variables
    private Hashtable env = null;
    private CoadunationThreadGroup threadGroup = null;
    private OrbManager orbManager = null;
    private Name base = null;
    private Name instanceURL = null;
    private String instanceCosURL = null;
    private Context instanceContext = null;
    private boolean primary = false;
    private String masterCosURL = null;
    private Context masterContext = null;
    private InstanceBinder instanceBinder = null;
    
    
    /**
     * Creates a new instance of CosContext
     * 
     * @param env The environment of the cos context.
     * @param threadGroup The reference to the coadunation thread group.
     * @param instanceId The id of the coadunation instance.
     * @exception NamingException
     */
    public CosContext(Hashtable env, CoadunationThreadGroup threadGroup,
            OrbManager orbManager,String instanceId) throws NamingException {
        try {
            this.env = env;
            this.threadGroup = threadGroup.createThreadGroup();
            this.orbManager = orbManager;
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            
            instanceCosURL = config.getString(INSTANCE_COS_URL);
            if (config.getBoolean(PRIMARY)) {
                instanceURL = new NamingParser().parse(config.getString(
                        NamingDirector.PRIMARY_URL));
                base = instanceURL;
                primary = true;
                log.info("Running as primary");
            } else {
                instanceURL = new NamingParser().parse(config.getString(
                        NamingDirector.PRIMARY_URL))
                        .add(NamingConstants.SUBCONTEXT)
                        .add(instanceId);
            
                base = new NamingParser().parse("");
                masterCosURL = config.getString(MASTER_COS_URL);
                
                // instanciate the new instance binder thread thread.
                instanceBinder = new InstanceBinder();
                threadGroup.addThread(instanceBinder,config.getString(USER));
                instanceBinder.start();
                log.info("Running as secondary");
                
            }
            
        } catch (Exception ex) {
            log.error("Failed to create a new URL context : " +
                    ex.getMessage());
            throw new NamingException("Failed to create a new URL context : " +
                    ex.getMessage());
        }
    }
    
    /**
     * Adds a new environment property to the environment of this context.
     *
     * @return The previous value of the property or null.
     * @param propName The property to replace or add.
     * @param propVal The new property value.
     */
    public Object addToEnvironment(String propName, Object propVal) throws 
            NamingException {
        return null;
    }
    
    
    /**
     * Binds a name to an object.
     *
     * @param name The name of the object to bind.
     * @param obj The object to bind.
     * @exception NamingException
     */
    public void bind(Name name, Object obj) throws NamingException {
        if (name.get(0).equals(NamingConstants.JNDI_NETWORK_PREFIX)) {
            throw new NamingException(
                    "Not allowed to bind to the base context. " +
                    "Must use relative binding");
        }
        Context context = getInitialContext(name);
        try {
            Name composedName = composeName(name, base);
            for (int index = 0; index < (composedName.size() -1); index++) {
                try {
                    context = (Context)context.lookup(composedName.get(index));
                } catch (javax.naming.NameNotFoundException ex) {
                    context = context.createSubcontext(composedName.get(index));
                }
            }
            log.info("Binding object [" + composedName.toString() + "] [" + 
                    name.get(0) + "][" + composedName.get(
                    composedName.size() -1) + "] object [" +
                    obj.getClass().getName() + "]");
            context.bind(composedName.get(composedName.size() -1),
                    wrapBindingValue(obj));
        } catch (NamingException ex) {
            resetContext(context);
            log.error("Failed to bind the object to the context :"  + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            resetContext(context);
            log.error("Failed to bind the object to the context :"  + 
                    ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to bind the object to the context :"  + 
                    ex.getMessage());
        }
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
    public void close() throws NamingException {
        if (instanceContext != null) {
            instanceContext.close();
        }
        if (masterContext != null) {
            masterContext.close();
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
        Name tmp = (Name)prefix.clone();
        tmp.addAll(name);
        return tmp;
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
        if (name.get(0).equals(NamingConstants.JNDI_NETWORK_PREFIX)) {
            throw new NamingException(
                    "Must use relative bindings.");
        }
        Context context = getInitialContext(name);
        try {
            Name composedName = composeName(name, base);
            for (int index = 0; index < (composedName.size()); index++) {
                try {
                    context = (Context)context.lookup(composedName.get(index));
                } catch (javax.naming.NameNotFoundException ex) {
                    context = context.createSubcontext(composedName.get(index));
                }
            }
            return context;
        } catch (NamingException ex) {
            resetContext(context);
            log.error("Failed to create the sub context :"  + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            resetContext(context);
            log.error("Failed to create the sub context :"  + 
                    ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to create the sub context :"  + 
                    ex.getMessage());
        }
    }
    
    
    /**
     * Creates and binds a new context.
     *
     * @return The newly create sub context.
     * @param name The name of the new sub context.
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
        if (name.get(0).equals(NamingConstants.JNDI_NETWORK_PREFIX)) {
            throw new NamingException(
                    "Must use relative bindings.");
        }
        Context context = getInitialContext(name);
        try {
            Name composedName = composeName(name, base);
            context.destroySubcontext(composedName);
        } catch (NamingException ex) {
            resetContext(context);
            log.error("Failed to destroy the sub context :"  + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            resetContext(context);
            log.error("Failed to destroy the sub context :"  + 
                    ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to destroy the sub context :"  + 
                    ex.getMessage());
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
        return null;
    }
    
    
    /**
     * Retrieves the full name of this context within its own namespace.
     */
    public String getNameInNamespace() throws NamingException {
        return this.instanceURL.toString();
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
        throw new OperationNotSupportedException(
                "This operation is currently not supported");
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
        throw new OperationNotSupportedException(
                "This operation is currently not supported");
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
        throw new OperationNotSupportedException(
                "This operation is currently not supported");
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
        throw new OperationNotSupportedException(
                "This operation is currently not supported");
    }
    
    
    /**
     * Retrieves the named object.
     *
     * @return The named object.
     * @param name The name to retrieve the object for.
     * @exception NamingException
     */
    public Object lookup(Name name) throws NamingException {
        Context context = getInitialContext(name);
        String currentName = null;
        try {
            Name composedName = null;
            if (name.get(0).equals(NamingConstants.JNDI_NETWORK_PREFIX)) {
                composedName = name;
            } else {
                composedName = composeName(name, base);
            }
            for (int index =0; index < (composedName.size() - 1); index++ ) {
                currentName = composedName.get(index);
                context = (Context)context.lookup(currentName);
            }
            currentName = composedName.get(composedName.size() - 1);
            return unwrapBindingValue(
                    context.lookup(currentName));
        } catch (NamingException ex) {
            resetContext(context);
            log.error("Failed to lookup the object [" + currentName + "] :"  + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            resetContext(context);
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
        throw new OperationNotSupportedException(
                "This method is not currently supported");
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
        if (name.get(0).equals(NamingConstants.JNDI_NETWORK_PREFIX)) {
            throw new NamingException(
                    "Not allowed to bind to the base context. " +
                    "Must use relative binding");
        }
        Context context = getInitialContext(name);
        try {
            Name composedName = composeName(name, base);
            for (int index = 0; index < (composedName.size() -1); index++) {
                try {
                    context = (Context)context.lookup(composedName.get(index));
                } catch (javax.naming.NameNotFoundException ex) {
                    context = context.createSubcontext(composedName.get(index));
                }
            }
            log.info("Rebinding object [" + composedName.toString() + "] [" + 
                    name.get(0) + "][" + composedName.get(
                    composedName.size() -1) + "] object [" +
                    obj.getClass().getName() + "]");
            context.rebind(composedName.get(composedName.size() -1),
                    wrapBindingValue(obj));
        } catch (NamingException ex) {
            resetContext(context);
            log.error("Failed to rebind the object to the context :"  + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            resetContext(context);
            log.error("Failed to rebind the object to the context :"  + 
                    ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to rebind the object to the context :"  + 
                    ex.getMessage());
        }
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
     * @param propName The name of the entry to remove from the environment.
     * @exception NamingException
     */
    public Object removeFromEnvironment(String propName) throws NamingException {
        return null;
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
        if (oldName.get(0).equals(NamingConstants.JNDI_NETWORK_PREFIX) ||
                newName.get(0).equals(NamingConstants.JNDI_NETWORK_PREFIX)) {
            throw new NamingException(
                    "Must use relative binding");
        }
        Context context = getInitialContext(oldName);
        try {
            Object value = lookup(oldName);
            bind(newName,value);
            unbind(oldName);
        } catch (NamingException ex) {
            resetContext(context);
            log.error("Failed to rename the object in the context :"  + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            resetContext(context);
            log.error("Failed to rename the object in the context :"  + 
                    ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to rename the object in the context :"  + 
                    ex.getMessage());
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
    public void rename(String oldName, String newName) throws NamingException {
        rename(new NamingParser().parse(oldName),
                new NamingParser().parse(newName));
    }
    
    
    /**
     * Unbinds the named object.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(Name name) throws NamingException {
        if (name.get(0).equals(NamingConstants.JNDI_NETWORK_PREFIX)) {
            throw new NamingException(
                    "Not allowed to use the base context. " +
                    "Must use relative binding");
        }
        Context context = getInitialContext(name);
        try {
            Name composedName = composeName(name, base);
            context.unbind(composedName);
        } catch (NamingException ex) {
            resetContext(context);
            log.error("Failed to unbind the object to the context :"  + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            resetContext(context);
            log.error("Failed to unbind the object to the context :"  + 
                    ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to unbind the object to the context :"  + 
                    ex.getMessage());
        }
    }
    
    
    /**
     * Unbinds the named object.
     *
     * @param name The name to unbind.
     * @exception NamingException
     */
    public void unbind(String name) throws NamingException {
        unbind(new NamingParser().parse(name));
    }
    
    
    /**
     * This method is responsible for terminating the processing of this cos
     * context.
     */
    public void terminate() {
        threadGroup.terminate();
        try {
            close();
        } catch (Exception ex) {
            log.error("Failed to close the contexts : " + ex.getMessage(),ex);
        }
        if (instanceBinder != null) {
            instanceBinder.terminate();
        }
    }
    
    
    /**
     * This method returns the initial context that matches the given name.
     *
     * @return The context that matches the name.
     * @param name The name to retrieve the context for.
     * @exception NamingException
     */
    private Context getInitialContext(Name name) throws NamingException {
        try {
            if (primary || !(name.get(0).equals(this.instanceURL.get(0)))) {
                synchronized(this) {
                    if (instanceContext != null) {
                        return instanceContext;
                    }
                    Hashtable env = new Hashtable();
                    env.put(Context.INITIAL_CONTEXT_FACTORY,
                            SUN_COS_CONTEXT_FACTORY);
                    env.put(Context.PROVIDER_URL,
                            instanceCosURL);
                    env.put("java.naming.corba.orb",orbManager.getORB());
                    return instanceContext = new InitialContext(env);
                }
            }
            synchronized(this) {
                if (masterContext != null) {
                    return masterContext;
                }
                Hashtable env = new Hashtable();
                env.put(Context.INITIAL_CONTEXT_FACTORY,
                        SUN_COS_CONTEXT_FACTORY);
                env.put(Context.PROVIDER_URL,
                        masterCosURL);
                env.put("java.naming.corba.orb",orbManager.getORB());
                log.info("Retrieve the master context url");
                return masterContext = new InitialContext(env);
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve an initial context for the name [" +
                    name.toString() + "] because : " + ex.getMessage(),ex);
            throw new NamingException("Failed to retrieve an initial context " +
                    "for the name [" + name.toString() + "] because : " + 
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method is used to reset contexts that are not working correctly.
     *
     * @param context The reference to the context that needs resetting
     */
    private void resetContext(Context context) {
        if (context == instanceContext) {
            instanceContext = null;
        }
        if (context == masterContext) {
            masterContext = null;
        }
    }
    
    
    /**
     * This method is responsible for wrapping the binding value. It uses the
     * dynamic any value stub code to perform this task.
     *
     * @return The wrapped object or Remote.
     * @param value The object to wrap if remote it will just be returned.
     * @exception NamingException
     */
    public Object wrapBindingValue(Object value) throws NamingException {
        if (value instanceof Remote) {
            return value;
        } else if (!(value instanceof Serializable)) {
            throw new NamingException("binding value is not instance of " +
                    "Remote or Serializable cannot be stored in Cos Naming");
        } 
        JNDIResourceWrapper resourceWrapper = null;
        if (value instanceof Referenceable) {
            resourceWrapper = new JNDIResourceWrapper((Serializable)((
                    Referenceable)value).
                    getReference());
        } else {
            resourceWrapper = new JNDIResourceWrapper((Serializable)value);
        }
        try {
            PortableRemoteObject.exportObject(resourceWrapper);
        } catch (Exception ex) {
            log.error("Failed to export wrapper :" + ex.getMessage(),ex);
            throw new NamingException("Failed to export wrapper :" + 
                    ex.getMessage());
        }
        return resourceWrapper;
    }
    
    
    /**
     * This method unwrapps the retrieved value. If it is an instance of remote
     * than the value just gets returned.
     *
     * @return The unwrapped object.
     * @param value The value to unwrapp.
     * @exception NamingException
     */
    public Object unwrapBindingValue(Object value) throws 
            NamingException {
        try {
            ObjectImpl  objectImpl = (ObjectImpl)PortableRemoteObject.narrow(
                    value,ObjectImpl.class);
            String[] ids = objectImpl._ids();
            String itf = ids[0];
            
            if (itf.indexOf(":org.objectweb.carol.jndi.wrapping." +
                    "JNDIRemoteResource:") != -1) {
                JNDIRemoteResource jndiRemoteResource = (JNDIRemoteResource)
                    PortableRemoteObject.narrow(value,JNDIRemoteResource.class);
                return jndiRemoteResource.getResource();
            }
            
            return value;
        } catch (Exception ex) {
            log.error("Failed to wrapp the object :"  + ex.getMessage(),ex);
            throw new NamingException("Failed to wrapp the object :"  + 
                    ex.getMessage());
        }
    }
}
