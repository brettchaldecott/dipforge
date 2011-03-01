/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo.basic;

// java imports
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.InvocationHandler;

/**
 * 
 * @author brett chaldecott
 */
public class BasicJDOInvocationHandler implements InvocationHandler {
    
    // private member variablees
    private Class type;
    private boolean isResource;
    private PersistanceResource resource;

    
    /**
     * The constructor 
     * @param type
     * @param resource
     */
    public BasicJDOInvocationHandler(Class type, PersistanceResource resource) {
        this.type = type;
        if (type.equals(Resource.class)) {
            isResource = true;
        }
        this.resource = resource;
    }



    
    /**
     * This method is responsible to handling the invocation request.
     * 
     * @param proxy The proxy interface the call was invoked on.
     * @param method The method to perform the invocation call on.
     * @param args The list of arguments to pass to the method.
     * @return The result of the call.
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
