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
 * MXManagerImpl.java
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
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;

// import log4j
import org.apache.log4j.Logger;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// coadunation imports
import com.rift.coad.lib.deployment.jmxbean.JMXBeanConnector;
import com.rift.coad.lib.deployment.jmxbean.JMXBeanManager;
import com.rift.coad.web.admin.client.MXManager;
import com.rift.coad.web.admin.client.MXException;
import com.rift.coad.web.admin.client.MethodDef;
import com.rift.coad.web.admin.client.VariableDef;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ExceptionInfo;

/**
 *
 * @author brett chaldecott
 */
public class MXManagerImpl extends RemoteServiceServlet implements
        MXManager {
    
    // private member variables
    private static Logger log = Logger.getLogger(MXManagerImpl.class);
    
    /**
     * This method returns the list of daemons.
     *
     * @return A list of daemons.
     */
    public String[] getMXBeans() throws MXException {
        try {
            Set entries = JMXBeanConnector.getInstance().getJMXBeanKeys();
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
            throw new MXException("Failed to retrieve the daemon list : "
                    + ex.getMessage());
        }
        
    }
    
    
    /**
     * This method returns the list of daemons
     *
     * @return The list of methods.
     * @param name The name of the method.
     * @exception MXException
     */
    public String[] getMethods(String name) throws MXException {
        try {
            Object bean = JMXBeanConnector.getInstance().getJMXBean(name);
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
            throw new MXException("Failed to retrieve the method list : "
                    + ex.getMessage());
        }
    }
    
    
    /**
     * This method returns definition of the method.
     *
     * @return A list of methods.
     * @param The name of the method.
     * @exception MXException
     */
    public MethodDef getMethod(String objectName, String methodName)
    throws MXException {
        try {
            Object bean = JMXBeanConnector.getInstance().getJMXBean(objectName);
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
            // process the annotations
            Annotation[] methodAnnotations = method.getAnnotations();
            String resultDesc = "";
            String version = "";
            String exceptionDesc = "";
            String methodDesc = "";
            log.debug("Process the Annotations");
            for (int index = 0; index < methodAnnotations.length; index++) {
                Annotation anno = methodAnnotations[index];
                log.debug("The annotation : " + anno.toString());
                if (checkAnnotation(anno, MethodInfo.class)) {
                    methodDesc = invokeAnnotation(anno, "description") + "\n";
                } else if (checkAnnotation(anno, Result.class)) {
                    resultDesc = invokeAnnotation(anno, "description") + "\n";
                } else if (checkAnnotation(anno, Version.class)) {
                    version = invokeAnnotation(anno, "number") + "\n";
                } else if (checkAnnotation(anno, ExceptionInfo.class)) {
                    exceptionDesc += invokeAnnotation(anno, "name") + " " +
                            invokeAnnotation(anno, "description") +"\n";
                }
            }
            log.debug("After processing the annotations");
            
            VariableDef result = new VariableDef("Result",
                    method.getReturnType().getName(), resultDesc);
            MethodDef methodDef = new MethodDef(method.getName(),
                    methodDesc + version + exceptionDesc +
                    method.toGenericString() , result, null);
            VariableDef[] parameters = new VariableDef[
                    method.getParameterTypes().length];
            Annotation[][] paramersAnnotations = method.getParameterAnnotations();
            for (int index = 0; index < method.getParameterTypes().length;
            index++) {
                VariableDef var = new VariableDef("p" + index,
                        method.getParameterTypes()[index].getName(),
                        method.getParameterTypes()[index].getSimpleName());
                if (paramersAnnotations.length > index) {
                    Annotation[] paramAnnotations = paramersAnnotations[index];
                    for (int paramIndex = 0;
                    paramIndex < paramAnnotations.length; paramIndex++ ) {
                        Annotation anno = paramAnnotations[paramIndex];
                        if (checkAnnotation(anno, ParamInfo.class)) {
                            var.setName(invokeAnnotation(anno, "name"));
                            var.setDescription(
                                    invokeAnnotation(anno, "description"));
                            break;
                        }
                    }
                }
                parameters[index] = var;
            }
            methodDef.setParameters(parameters);
            
            return methodDef;
        } catch (Exception ex) {
            log.error("Failed to retrieve the method info : "
                    + ex.getMessage(),ex);
            throw new MXException("Failed to retrieve the method info : "
                    + ex.getMessage());
        }
    }
    
    
    /**
     * This method is called to invoke the daemon.
     *
     * @return The result of the method call.
     * @param method The method to call.
     * @exception MXException
     */
    public String invokeMethod(String objectName, MethodDef methodDef)
    throws MXException {
        Date start = new Date();
        try {
            Object bean = JMXBeanConnector.getInstance().getJMXBean(objectName);
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
     * This method returns true if the annotation is of the correct type.
     *
     * @return TRUE if the annotation is of the correct type.
     * @param anno The annotation to check.
     * @param type The type to check.
     */
    private boolean checkAnnotation(Annotation anno, Class type) {
        return anno.annotationType().getName().equals(type.getName());
    }
    
    
    /**
     * This method invokes the annotation and returns the string value.
     *
     * @return The string containing the annotation value.
     * @param anno The annotation to invoke.
     * @param methodName The method name to invoke.
     */
    private String invokeAnnotation(Annotation anno, String methodName) throws
            Exception {
        Method desc = anno.getClass().getMethod(methodName,new Class[] {});
        if (desc == null) {
            return "";
        }
        return desc.invoke(anno,new Object[] {}).toString();
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


