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
 * TestTask.java
 */

package com.rift.coad.change.request.rdf;

import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;

/**
 * This object represents a test
 *
 * @author brett chaldecott
 */
public class TestTask extends ActionTaskDefinition {

    public TestInterface test;

    /**
     * The constructor for the test task.
     *
     * @param test The test that must be executed by this task.
     */
    public TestTask(TestInterface test) {
        this.test = test;
    }

    /**
     * The getter for the test.
     *
     * @return The reference to the test.
     */
    public TestInterface getTest() {
        return test;
    }


    /**
     * The setter for the test interface.
     *
     * @param test The test task.
     */
    public void setTest(TestInterface test) {
        this.test = test;
    }



}
