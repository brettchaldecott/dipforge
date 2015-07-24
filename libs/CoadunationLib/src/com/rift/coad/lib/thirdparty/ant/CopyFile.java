/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * CopyFile.java
 *
 * This class is responsible for copy files from one location to another.
 */

// package path
package com.rift.coad.lib.thirdparty.ant;

// java imports
import java.io.File;
import org.apache.tools.ant.BuildException;

// ant imports
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.BuildEvent;


/**
 * This class is responsible for copy files from one location to another.
 *
 * @author Brett Chaldecott
 */
public class CopyFile extends Copy {
    
    /** Creates a new instance of CopyFile */
    public CopyFile(File source, File dest) {
        project = new Project();
        
        project.init();
        taskType = "jar";
        taskName = "rmic";
        target = new Target();
        Path path = new Path(project);
        this.setFile(source);
        this.setTofile(dest);
    }
    
    
    /**
     * This method executes the copy.
     *
     * @exception AntException
     */
    public void copy() throws AntException {
        AntListener listener = new AntListener();
        project.addBuildListener(listener);
        try {
            super.execute();
        } catch (Exception ex) {
            throw new AntException("Failed to copy the file :" + ex.getMessage() 
                + " [" + listener.getMessage() + "]",ex);
        }
    }
}
