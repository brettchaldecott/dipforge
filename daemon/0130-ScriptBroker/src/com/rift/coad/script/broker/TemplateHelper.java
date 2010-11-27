/*
 * ScriptBroker: The script broker daemon.
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
 * TemplateHelper.java
 */

// the package path
package com.rift.coad.script.broker;

// java imports
import java.io.File;
import java.util.Map;
import java.util.HashMap;



/**
 * The template helper
 *
 * @author brett chaldecott
 */
public class TemplateHelper {
    // private member variables
    private File path;
    private Map<String,String> parameters;
    private String fileContents;


    /**
     * The constructor that retrieves the template.
     *
     * @param path The path.
     */
    public TemplateHelper(String path) throws TemplateException {
        this.path = new File(path);
        if (!this.path.exists()) {
            throw new TemplateException("The template [" + path +
                    "] does not exist.");
        } else if (!this.path.isFile()) {
            throw new TemplateException("The template [" + path +
                    "] is not a file.");
        }

    }


    /**
     * This method sets the parameters for the template.
     *
     * @param parameters The list of parameters for the template.
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }


    /**
     * This method is called to parse the template.
     *
     * @return The results of the parsing.
     */
    public String parse() throws TemplateException {
        try {
            byte[] buffer = new byte[(int)path.length()];
            java.io.FileInputStream in = new java.io.FileInputStream(this.path);
            in.read(buffer);
            in.close();
            String result = new String(buffer);
            for (String key : parameters.keySet()) {
                result = result.replaceAll("%" + key, parameters.get(key));
            }
            return result;
        } catch (Throwable ex) {
            throw new TemplateException("Failed to parse the template [" +
                    this.path.getPath() + "] because : " + ex.getMessage(),ex);
        }
    }

}
