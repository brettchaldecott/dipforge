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
 * JAR.java
 *
 * This object is responsible for the creation of java archives.
 */

// package path
package com.rift.coad.lib.thirdparty.ant;

// java imports
import java.io.File;
import org.apache.tools.ant.BuildException;

// ant imports
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.Path;

/**
 * This object is responsible for the creation of java archives.
 *
 * @author Brett Chaldecott
 */
public class JAR extends Jar {
    
    /**
     * Creates a new instance of JAR object that can be executed.
     *
     * @param source The source file.
     * @param dest The target file.
     */
    public JAR(File source, File dest) {
        project = new Project();
        
        project.init();
        taskType = "jar";
        taskName = "jar";
        target = new Target();
        Path path = new Path(project);
        this.setBasedir(source);
        this.setDestFile(dest);
    }
    
    
    /**
     * This method archives the specified file.
     *
     * @exception AntException
     */
    public void archive() throws AntException {
        AntListener listener = new AntListener();
        project.addBuildListener(listener);
        try {
            super.execute();
        } catch (Exception ex) {
            throw new AntException("Failed to archive :" + ex.getMessage() 
                + " [" + listener.getMessage() + "]",ex);
        }
    }
}
