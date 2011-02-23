/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.util;

// imports
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


/**
 * This class contains methods to evaluate the type information about a class.
 *
 * @author brett chaldecott
 */
public class ClassTypeInfo {


    /**
     * This method returns true if the object passed in is a basic type
     *
     * @param c The class to perform the comparison on.
     * @return The return type.
     */
    public static boolean isBasicType(Class c) {
        if (c.equals(String.class)) {
            return true;
        } else if (c.equals(Date.class)) {
            return true;
        } else if (c.equals(Calendar.class)) {
            return true;
        } else if (c.equals(BigDecimal.class)) {
            return true;
        } else if (Integer.class.equals(c)) {
            return true;
        } else if (Long.class.equals(c)) {
            return true;
        } else if (Double.class.equals(c)) {
            return true;
        } else if (Float.class.equals(c)) {
            return true;
        } else if (Character.class.equals(c)) {
            return true;
        } else if (Short.class.equals(c)) {
            return true;
        } else if (c.isPrimitive()) {
            return true;
        }
        return false;
    }


    /**
     * This method returns true if this class is a collection object.
     *
     * @param c The reference to the class to perform the comparison on.
     * @return TRUE if a collection, FALSE if not.
     */
    public static boolean isCollection(Class c) {
        if (Collection.class.isAssignableFrom(c)) {
            return true;
        }
        return false;
    }
}
