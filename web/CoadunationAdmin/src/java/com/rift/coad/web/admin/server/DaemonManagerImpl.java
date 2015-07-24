/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  2015 Burntjam
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
 * DaemonManagerImpl.java
 */

// package path
package com.rift.coad.web.admin.server;

// imports
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Vector;
import java.util.Date;
import java.lang.reflect.Method;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

// import log4j
import org.apache.log4j.Logger;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// coadunation imports
import com.rift.coad.lib.deployment.bean.BeanConnector;
import com.rift.coad.lib.deployment.bean.BeanManager;
import com.rift.coad.web.admin.client.DaemonManager;
import com.rift.coad.web.admin.client.DaemonException;
import com.rift.coad.web.admin.client.MethodDef;
import com.rift.coad.web.admin.client.VariableDef;

/**
 * This service is responsible for managing the daemons.
 *
 * @author brett chaldecott
 */
public class DaemonManagerImpl extends RemoteServiceServlet implements
        DaemonManager {
    
    // private member variables
    private static Logger log = Logger.getLogger(DaemonManagerImpl.class);
    
    /**
     * This method returns the list of daemons.
     *
     * @return A list of daemons.
     */
    public String[] getDaemons() throws DaemonException {
        try {
            Set entries = BeanConnector.getInstance().getKeys();
            String[] result = new String[entries.size()];
            int index = 0;
            for (Iterator iter = entries.iterator(); iter.hasNext(); index++) {
                result[index] = (String)iter.next();
            }
            Arrays.sort(result);
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve the daemon list : "
                    + ex.getMessage(),ex);
            throw new DaemonException("Failed to retrieve the daemon list : "
                    + ex.getMessage());
        }
        
    }
    
    
    /**
     * This method returns the list of daemons
     *
     * @return The list of methods.
     * @param name The name of the method.
     * @exception DaemonException
     */
    public String[] getMethods(String name) throws DaemonException {
        try {
            Object bean = BeanConnector.getInstance().getBean(name);
            Class[] interfaces = bean.getClass().getInterfaces();
            
            Vector methodVec = new Vector();
            for (int interfaceIndex = 0; interfaceIndex < interfaces.length;
            interfaceIndex++) {
                Method[] methods = interfaces[interfaceIndex].
                        getDeclaredMethods();
                for (int index = 0; index < methods.length; index++) {
                    Method method = methods[index];
                    String methodDec = method.getName();
                    methodDec += "(";
                    String sep = "";
                    for (int param = 0; param < method.getParameterTypes().length;
                    param++) {
                        methodDec += sep +
                                method.getParameterTypes()[param].getName();
                        sep = ",";
                    }
                    methodDec += ")";
                    methodVec.add(methodDec);
                }
            }
            String[] result = (String[])methodVec.toArray(new String[0]);
            Arrays.sort(result);
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve the method list : "
                    + ex.getMessage(),ex);
            throw new DaemonException("Failed to retrieve the method list : "
                    + ex.getMessage());
        }
    }
    
    
    /**
     * This method returns definition of the method.
     *
     * @return A list of methods.
     * @param The name of the method.
     * @exception DaemonException
     */
    public MethodDef getMethod(String objectName, String methodName)
    throws DaemonException {
        try {
            Object bean = BeanConnector.getInstance().getBean(objectName);
            String name = methodName.substring(0,methodName.indexOf("("));
            String paramTypes = methodName.substring(methodName.indexOf("(") + 1,
                    methodName.indexOf(")")).trim();
            Class[] types = null;
            log.debug("Object name " + bean.getClass().getName());
            if (paramTypes.length() != 0) {
                String[] params = paramTypes.split("[,]");
                types = new Class[params.length];
                log.debug("There are :" + params.length + ": params : " + paramTypes);
                for (int param = 0; param < params.length; param++) {
                    log.debug("Set the type :" + params[param]);
                    if (ObjectTypeUtil.isPrimitive(params[param])) {
                        types[param] = ObjectTypeUtil.getPrimitive(params[param]);
                    } else {
                        types[param] = Class.forName(params[param],false,
                                bean.getClass().getClassLoader());
                    }
                }
            } else {
                types = new Class[0];
            }
            Method method = ObjectTypeUtil.getMethod(
                    bean.getClass().getInterfaces(),name,
                    types);
            VariableDef result = new VariableDef("Result",
                    method.getReturnType().getName(), "");
            MethodDef methodDef = new MethodDef(method.getName(),
                    method.toGenericString(), result, null);
            VariableDef[] parameters = new VariableDef[
                    method.getParameterTypes().length];
            for (int index = 0; index < method.getParameterTypes().length;
            index++) {
                parameters[index] = new VariableDef("p" + index,
                        method.getParameterTypes()[index].getName(),
                        method.getParameterTypes()[index].getSimpleName());
            }
            methodDef.setParameters(parameters);
            
            return methodDef;
        } catch (Exception ex) {
            log.error("Failed to retrieve the method info : "
                    + ex.getMessage(),ex);
            throw new DaemonException("Failed to retrieve the method info : "
                    + ex.getMessage());
        }
    }
    
    
    /**
     * This method is called to invoke the daemon.
     *
     * @return The result of the method call.
     * @param method The method to call.
     * @exception DaemonException
     */
    public String invokeMethod(String objectName, MethodDef methodDef) 
    throws DaemonException {
        Date start = new Date();
        try {
            Object bean = BeanConnector.getInstance().getBean(objectName);
            VariableDef[] parameters = methodDef.getParameters();
            Class[] types = new Class[parameters.length];
            Object[] arguments = new Object[parameters.length];
            for (int index = 0; index< parameters.length; index++) {
                if (ObjectTypeUtil.isPrimitive(parameters[index].getType())) {
                    types[index] = ObjectTypeUtil.
                            getPrimitive(parameters[index].getType());
                } else {
                    types[index] = Class.forName(parameters[index].getType(),
                            false,bean.getClass().getClassLoader());
                }
                arguments[index] = ObjectTypeUtil.getValue(types[index],
                        parameters[index].getValue());
            }
            Method method = bean.getClass().getMethod(methodDef.getName(),
                    types);
            
            // invoke the method
            ClassLoader currentLoader = Thread.currentThread().
                    getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(
                        bean.getClass().getClassLoader());
                Object result = method.invoke(bean,arguments);
                if (result == null) {
                    return printResult(start,"null",true);
                }
                return printResult(start,result.toString(),true);
            } finally {
                // reset the class loader
                Thread.currentThread().setContextClassLoader(
                        currentLoader);
            }
        } catch (Exception ex) {
            log.error("Failed to invoke the method : "
                    + ex.getMessage(),ex);
            String result = "Failed to invoke the method : "
                    + ex.getMessage() + "\n";
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PrintStream outStream = new PrintStream(output);
            ex.printStackTrace(outStream);
            outStream.flush();
            result += output.toString();
            
            return printResult(start,result,false);
        }
    }
    
    
    /**
     * This method prints the result.
     */
    private String printResult(Date start, String methodResult, boolean successfull) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss Z");
        String result = "Start: " +format.format(start)+ "\nEnd " +
                format.format(new Date())+ "\n";
        if (successfull) {
            result += "State: successfull\n";
        } else {
            result += "State: failed\n";
        }
        result += "Result: " + methodResult + "\n";
        return result;
    }
    
}
