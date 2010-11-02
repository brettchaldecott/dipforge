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
 * TextFile.java
 *
 * This class is responsible for reading in the text file it is pointed at.
 */

// the package path
package com.rift.coad.lib.common;

// java imports
import com.rift.coad.lib.common.*;
import java.io.File;
import java.io.FileReader;

/**
 * This class is responsible for reading in the text file it is pointed at.
 *
 * @author Brett Chaldecott
 */
public class TextFile {
    
    // private member variables
    private String textDocument = null;
    
    /**
     * Creates a new instance of TextFile
     *
     * @param targetFile The path to the target file.
     */
    public TextFile(File targetFile) throws CommonException {
        try {
            FileReader fileReader = new FileReader(targetFile);
            char[] buffer = new char[1024];
            StringBuffer stringBuffer = new StringBuffer();
            int length = 0;
            while ((length = fileReader.read(buffer)) != -1) {
                stringBuffer.append(buffer,0,length);
            }
            textDocument = stringBuffer.toString();
            fileReader.close();
        } catch (Exception ex) {
            throw new CommonException("Failed to read in the text document [" +
                    targetFile.getPath() + "] because : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method returns the text document that has been read in.
     *
     * @return The text document that has been read in.
     */
    public String getTextDocument() {
        return textDocument;
    }
}
