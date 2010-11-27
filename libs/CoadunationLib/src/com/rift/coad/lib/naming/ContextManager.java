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
 * ContextManager.java
 *
 * This object managers a context.
 */

// package path
package com.rift.coad.lib.naming;

// logging import
import org.apache.log4j.Logger;

// java imports
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.CompositeName;

/**
 * This object managers a context.
 *
 * @author Brett Chaldecott
 */
public class ContextManager {
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(ContextManager.class.getName());
    
    // the classes private member variables.
    private Context baseContext = null;
    private String base = null;
    private String contextPath = null;
    
    /** 
     * Creates a new instance of ContextManager 
     *
     * @param base The base string context.
     * @exception NamingException
     */
    public ContextManager(String base) throws NamingException {
        this.base = base;
        if (base.endsWith("/")) {
            this.base = base.substring(0,base.length() - 1);
        }
        baseContext = initContext(this.base);
    }
    
    
    /**
     * This method will bind the object to the context.
     *
     * @param name The name of the context.
     * @param ref The reference to the object.
     * @exception NamingException
     */
    public void bind(String name, Object ref) throws NamingException {
        try {
            if (ref == null) {
                throw new NamingException("The reference to bind is null");
            }
            String bindName = name;
            if (contextPath != null) {
                bindName = contextPath + "/" + name;
            }
            log.info("Binding [" + bindName + "]");
            baseContext.bind(bindName,ref);
        } catch (NamingException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to bind to the context : " + ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to bind the object to the context : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will un bind the object from the context.
     *
     * @param name The name of the context.
     * @exception NamingException
     */
    public void unbind(String name) throws NamingException {
        try {
            String bindName = name;
            if (contextPath != null) {
                bindName = contextPath + "/" + name;
            }
            baseContext.unbind(bindName);
            log.info("Un-bound [" + base + "/" + name + "]");
        } catch (Exception ex) {
            log.error("Failed to unbind to the context : " + ex.getMessage(),ex);
            throw new NamingException(
                    "Failed to unbind the object to the context : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method setups the initial context.
     *
     * @return The reference to the base context.
     * @param base The base context.
     */
    private Context initContext(String base) throws NamingException {
        try {
            Context context = new InitialContext();
            StringTokenizer stringTok = new StringTokenizer(base,"/");
            while(stringTok.hasMoreTokens()) {
                String entry = stringTok.nextToken();
                Context subContext = null;
                try {
                    subContext = (Context)context.lookup(entry);
                } catch (Exception exc) {
                    // nothing
                } finally {
                    if (subContext == null) {
                        try {
                            subContext = context.createSubcontext(entry);
                        } catch (javax.naming.OperationNotSupportedException ex) {
                            // this is to deal with some JNDI implementations
                            // not supporting createSubcontext
                            contextPath = base;
                            return context;
                        } catch (javax.naming.NameAlreadyBoundException ex) {
                            contextPath = base;
                            return context;
                        }
                    }
                }
                context = subContext;
            }
            return context;
        } catch (Exception ex) {
            log.error("Failed to init the context : " + ex.getMessage(),ex);
            throw new NamingException("Failed to init the context : " + 
                    ex.getMessage(),ex);
        }
    }
}
