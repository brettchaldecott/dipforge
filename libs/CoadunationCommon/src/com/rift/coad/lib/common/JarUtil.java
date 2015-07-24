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
 * JarUtil.java
 *
 * This class supplies methods to interact with a jar file.
 */

// package path
package com.rift.coad.lib.common;

// java imports
import com.rift.coad.lib.common.*;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;

/**
 * This class supplies methods to interact with a jar file.
 *
 * @author Brett Chaldecott
 */
public class JarUtil {
    
    /** 
     * Private constructor to prevent instanciation of this object.
     */
    private JarUtil() {
    }
    
    
    /**
     * This method extracts the contents of a source file to a destination file.
     *
     * @param source The source file to extract.
     * @param destination The destination file to extract.
     */
    public static void extract (File source, File destination) 
            throws CommonException {
        try {
            JarFile jarFile = new JarFile(source);
            Enumeration entries = jarFile.entries();
            byte[] readBuffer = new byte[1024];
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry)entries.nextElement();
                File path = new File(destination + File.separator
                        + entry.getName());
                if (entry.isDirectory()) {
                    if (!path.mkdirs()) {
                        throw new CommonException("Failed to create the dir [" + 
                                path.getAbsolutePath() + "]");
                    }
                    continue;
                }
                InputStream inputStream = 
                        jarFile.getInputStream(entry);
                FileOutputStream outputStream = new FileOutputStream(path);
                int readBytes = 0;
                while((readBytes = 
                        inputStream.read(readBuffer,0,readBuffer.length)) != -1) {
                    outputStream.write(readBuffer,0,readBytes);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
            }
        } catch (Exception ex) {
            throw new CommonException("Failed to extract the file [" + 
                    source.getAbsolutePath() + "] to [" + 
                    destination.getAbsolutePath() + "] because :" + 
                    ex.getMessage(),ex);
        }
    }
}
