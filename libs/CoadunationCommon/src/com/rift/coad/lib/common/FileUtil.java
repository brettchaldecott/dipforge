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
 * FileUtil.java
 *
 * This class is responsible for supplying simple file utilities
 */

// package path
package com.rift.coad.lib.common;

// java imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;


/**
 * This class is responsible for supplying simple file utilities
 *
 * @author Brett Chaldecott
 */
public class FileUtil {
    
    /** 
     * A private constructor to prevent instanciation of this object. 
     */
    private FileUtil() {
    }
    
    
    /**
     * This method filters the file list and returns a list of containing only
     * jar files.
     *
     * @return The filtered list of files.
     * @param files The unfiltered list of files.
     * @param suffix The suffix to filter the file list on.
     */
    public static File[] filter(File[] files,String suffix) {
        Vector filteredFiles = new Vector();
        for (int index = 0; index < files.length; index++) {
            File file = files[index];
            if (file.isFile() != true) {
                continue;
            }
            String path = file.getPath();
            if ((path.length() > 3) && (path.substring(path.length() - 
                    suffix.length()).equals(suffix))) {
               filteredFiles.add(file);
            }
        }
        File[] newFileList = new File[filteredFiles.size()];
        return (File[])filteredFiles.toArray(newFileList);
    }
    
    
    /**
     * This method copies a file from the source to the target.
     *
     * @param source The source file to copy.
     * @param target The target the file must be copied to.
     * @exception CommonException
     */
    public static void copyFile(File source, File target) throws 
            CommonException {
        try {
            FileInputStream in = new FileInputStream(source);
            FileOutputStream out = new FileOutputStream(target);
            int bytes = 0;
            byte[] buf = new byte[1024];
            while((bytes = in.read(buf)) != -1) {
                out.write(buf,0,bytes);
            }
            in.close();
            out.close();
        } catch (Exception ex) {
            throw new CommonException("Failed to copy the file to the target : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method deletes the target recursively
     *
     * @param target The target to delete.
     * @exception CommonException
     */
    public static void delTargetRecursive(File target) throws
            CommonException {
        try {
            File[] files = target.listFiles();
            for (int index = 0;(files != null) && 
                    (index < files.length); index++) {
                if (files[index].isDirectory()) {
                    delTargetRecursive(files[index]);
                } else {
                    files[index].delete();
                }
            }
            target.delete();
        } catch (Exception ex) {
            throw new CommonException("Failed to delete the target recursively :" 
                    + ex.getMessage(),ex);
        }
    }
}
