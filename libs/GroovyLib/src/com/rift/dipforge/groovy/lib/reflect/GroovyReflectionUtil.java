/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.dipforge.groovy.lib.reflect;

import java.lang.reflect.Method;

/**
 *
 * @author brettc
 */
public class GroovyReflectionUtil {

    /**
     * This method returns the requested method.
     *
     * @param ref The reference to the object containing the method.
     * @param methodName
     * @return The reference to the method
     * @throws GroovyEnvironmentException
     */
    public static Method getMethod(Object ref, String methodName, Class ... parameters) throws GroovyReflectException {
        try {
            return ref.getClass().getMethod(methodName, parameters);
        } catch (Exception ex) {
            // ignore
        }
        for (Class classRef : ref.getClass().getClasses()) {
            try {
                return classRef.getMethod(methodName, parameters);
            } catch (Exception ex) {
                // ignore
            }
        }
        throw new GroovyReflectException("The method [" + methodName + "] does not exist on this class [" + ref.getClass() + "]");
    }

}
