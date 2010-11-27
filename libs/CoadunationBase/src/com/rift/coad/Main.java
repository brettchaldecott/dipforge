/*
 * CoadunationBase: The base for a Coadunation instance.
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
 * Main.java
 *
 * The main class responsible for starting up the coadunation base.
 */

// package path
package com.rift.coad;

// java imports
import java.util.StringTokenizer;
import java.io.File;
import java.util.Vector;
import java.net.URL;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

/**
 * The main class responsible for starting up the coadunation base.
 *
 * @author Brett Chaldecott
 */
public class Main {
    
    /**
     * The main method responsible for starting the coadunation base.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String libdirs = System.getenv("COAD_LIB_DIRS");
            StringTokenizer stringTok = null;
            if (libdirs.contains(";")) {
                stringTok = new StringTokenizer(libdirs,";");
                System.out.println("Contains ;");
            } else {
                stringTok = new StringTokenizer(libdirs,":");
                System.out.println("Contains :");
            }
            System.out.println("Load from path : " + 
                    System.getenv("COAD_LIB_DIRS"));
            Vector urls = new Vector();
            while(stringTok.hasMoreTokens()) {
                String dirPath = stringTok.nextToken();
                File dir = new File(dirPath);
                if (dir.isFile() == true) {
                    urls.add(dir.toURL());
                    continue;
                }
                if (dir.isDirectory() == false) {
                    System.out.println("The path [" + dirPath 
                            + "] does not point at a valid directory.");
                    System.exit(-1);
                }
                File[] files = dir.listFiles();
                for (int index = 0; index < files.length; index++) {
                    String filePath = files[index].getAbsolutePath();
                    if (filePath.endsWith(".jar") || filePath.endsWith(".zip")) {
                        urls.add(files[index].toURL());
                    }
                }
            }
            URL[] urlArray = new URL[urls.size()];
            urls.toArray(urlArray);
            BaseClassLoader baseClassLoader = new BaseClassLoader(urlArray,
                    Main.class.getClassLoader());
            Class classRef = baseClassLoader.loadClass("com.rift.coad.Runner");
            if (classRef == null) {
                System.out.println(
                        "Failed to run the Coadunation base Runner reference " +
                        "is null.");
                throw new Exception("Failed to retrieve the appropriate class " +
                        "reference to start coadunation.");
            }
            System.out.println("Start Coadunation");
            Method method = classRef.getMethod("main");
            System.out.println("Invoking Coadunation");
            //System.setProperty("java.rmi.server.RMIClassLoaderSpi",
            //        "com.rift.coad.RemoteClassLoaderSpi");
            Thread.currentThread().setContextClassLoader(baseClassLoader);
            method.invoke(null);
            
        } catch (Exception ex) {
            System.out.println("Failed to run the Coadunation base [" +
                    ex.getMessage() + "]");
            ex.printStackTrace(System.out);
            System.exit(-1);
        }
    }
    
}
