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
 * ClassUtil.java
 *
 * This class supplies some methods for performing tests on classes.
 */

package com.rift.coad.lib.common;

import com.rift.coad.lib.common.*;

/**
 * This class supplies some methods for performing tests on classes.
 *
 * @author Brett Chaldecott
 */
public class ClassUtil {
    
    /** 
     * Creates a new instance of ClassUtil 
     */
    private ClassUtil() {
    }
    
    /**
     * This method performs the test on a class for a given parent interface
     * or object.
     *
     * @return TRUE if found, FALSE if not.
     * @param ref The reference to the class to check.
     * @param parentName The name of the parent to perform the check for.
     */
    public static boolean testForParent(Class ref, Class parentName) {
        return testForParent(ref, parentName.getName());
    }
    
    /**
     * This method performs the test on a class for a given parent interface
     * or object.
     *
     * @return TRUE if found, FALSE if not.
     * @param ref The reference to the class to check.
     * @param parentName The name of the parent to perform the check for.
     */
    public static boolean testForParent(Class ref, String parentName) {
        if (ref == null) {
            return false;
        } else if (ref.getName().equals(parentName)) {
            return true;
        } else if (ref.getName().equals(java.lang.Object.class.getName())) {
            return false;
        }
        Class[] interfaces = ref.getInterfaces();
        for (int index = 0; index < interfaces.length; index++) {
            if (testForParent(interfaces[index],parentName)) {
                return true;
            }
        }
        return testForParent(ref.getSuperclass(),parentName);
    }
}
