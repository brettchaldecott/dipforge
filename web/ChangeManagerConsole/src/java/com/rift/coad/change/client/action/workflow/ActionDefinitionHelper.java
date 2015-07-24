/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  2015 Burntjam
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
 * ActionDefinitionHelper.java
 */


package com.rift.coad.change.client.action.workflow;

import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.If;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * This class helps put together the action definition information.
 *
 * @author brett chaldecott
 */
public class ActionDefinitionHelper {


    /**
     * This method walks the definition.
     *
     * @param workflow The workflow canvas that everything is attached to.
     * @param currentCanvas The current canvas that the tasks will be attached to.
     * @param currentTask The current task
     * @param bin The parent bin.
     */
    public static void walkDefinition(WorkflowCanvas workflow, VStack currentCanvas,
            ActionTaskDefinition currentTask, PieceBin bin) throws Exception {
        String errors = "";
        boolean hasErrors = false;
        while(currentTask != null) {
            String contents = "";
            try {
                contents += "[before canvas]";
                Canvas childCanvas = TypeLookupFactory.getType(currentTask, workflow,bin);
                if (!(currentTask instanceof If)) {
                    contents += "[before add member]";
                    currentCanvas.addMember(childCanvas);
                }
            } catch (Exception ex) {
                errors += "Object [" + currentTask.getDescription() + ":" +
                        currentTask.getClass().getName() + contents + "] [" + ex.getMessage() + "]<br>";
                hasErrors = true;
            }
            currentTask = currentTask.getNext();
        }
        if (hasErrors) {
            throw new Exception(errors);
        }
    }
}
