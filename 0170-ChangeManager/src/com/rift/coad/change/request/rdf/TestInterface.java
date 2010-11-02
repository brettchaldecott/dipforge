/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  Rift IT Contracting
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
 * TestInterface.java
 */

package com.rift.coad.change.request.rdf;

import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;

/**
 * This interface is implemented by a test object.
 * 
 * @author brett The test to execute.
 */
public interface TestInterface {
    /**
     * This method is used to execute the tests.
     *
     * @param stack The stack to execute the tests on.
     * @return TRUE if executed, FALSE if not.
     * @throws java.lang.Exception
     */
    public boolean execute(StackEntry stack) throws Exception;
}
