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
 * JavaC.java
 *
 * This object is responsible for the creation of java classes from a source
 * directory.
 */

package com.rift.coad.lib.thirdparty.ant;

// java imports
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.net.URL;


// ant imports
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Path;

// coadunation import
import com.rift.coad.BaseClassLoader;

/**
 * This object is responsible for the creation of java classes from a source
 * directory.
 *
 * @author Brett Chaldecott
 */
public class JavaC  extends Javac {
    
    /** Creates a new instance of JavaC */
    public JavaC(File[] classPath, File source, File dest) {
        project = new Project();
        
        project.init();
        taskType = "JavaC";
        taskName = "JavaC";
        Path path = new Path(project);
        for (int index = 0; index < classPath.length; index++) {
            path.add(new Path(project,classPath[index].getAbsolutePath()));
        }
        if (this.getClass().getClassLoader() instanceof BaseClassLoader) {
            BaseClassLoader baseClassLoader = 
                    (BaseClassLoader)this.getClass().getClassLoader();
            URL urls[] = baseClassLoader.getURLs();
            for (int index = 0; index < urls.length; index++) {
                path.add(new Path(project,urls[index].getFile()));
            }
        }
        
        this.setProject(project);
        this.setClasspath(path);
        this.setSrcdir(new Path(project,source.getAbsolutePath()));
        this.setDestdir(dest);
    }
    
    /**
     * Compile the classes.
     *
     * @exception AntException
     */
    public void compileClasses() throws AntException {
        AntListener listener = new AntListener();
        project.addBuildListener(listener);
        try {
            super.execute();
        } catch (Exception ex) {
            throw new AntException("Failed to compile :" + ex.getMessage() 
                + " [" + listener.getMessage() + "]",ex);
        }
    }
}
