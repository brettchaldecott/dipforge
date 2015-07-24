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
 * RMIC.java
 *
 * The RMI generator wrapps the ANT rmi task.
 */

// package path
package com.rift.coad.lib.thirdparty.ant;

// java imports
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.net.URL;

// ant imports
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Rmic;
import org.apache.tools.ant.types.Path;

// coadunation import
import com.rift.coad.BaseClassLoader;

/**
 * The RMI generator wrapps the ANT rmi task.
 *
 * @author Brett Chaldecott
 */
public class RMIC extends Rmic {
    
    /**
     * Creates a new instance of RMIC
     *
     * @param base The base path to the file.
     * @param className The name of the class.
     * @param dest The destination of the compiled file.
     */
    public RMIC(File[] base, String className, File dest) {
        project = new Project();
        project.init();
        taskType = "rmic";
        taskName = "rmic";
        target = new Target();
        Path path = new Path(project);
        for (int index = 0; index < base.length; index++) {
            path.add(new Path(project,base[index].getAbsolutePath()));
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
        this.setClassname(className);
        this.setBase(dest);
        this.setIiop(true);
        this.setIiopopts("-poa");
    }
    
    
    /**
     * Creates a new instance of RMIC
     *
     * @param base The base path to the file.
     * @param source The source for the files
     * @param includes The includes to compile.
     * @param dest The destination
     */
    public RMIC(File[] base, File source, String includes, File dest) {
        project = new Project();
        project.init();
        taskType = "rmic";
        taskName = "rmic";
        target = new Target();
        Path path = new Path(project);
        for (int index = 0; index < base.length; index++) {
            path.add(new Path(project,base[index].getAbsolutePath()));
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
        this.setSourceBase(source);
        this.setIncludes(includes);
        this.setBase(dest);
        this.setIiop(true);
        this.setIiopopts("-poa");
    }
    
    
    /**
     * This method executes the rmi parser.
     *
     * @exception AntException
     */
    public void parse() throws AntException {
        AntListener listener = new AntListener();
        project.addBuildListener(listener);
        try {
            execute();
        } catch (Exception ex) {
            throw new AntException("Failed to parse the file : " 
                    + ex.getMessage() + " [" + 
                    listener.getMessage() + "]",ex);
        }
    }
}
