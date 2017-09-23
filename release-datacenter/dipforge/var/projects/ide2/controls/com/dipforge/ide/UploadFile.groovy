/*
 * ide2: Description
 * Copyright (C) Wed Aug 09 06:38:31 UTC 2017 owner 
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
 * UploadFile.groovy
 * @author admin
 */

package com.dipforge.ide

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;
import com.dipforge.utils.HttpRequestUtil;
import groovy.json.*
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import com.dipforge.utils.Project;


def log = Logger.getLogger("com.dipforge.log.com.dipforge.UploadFile");
def builder = new JsonBuilder()

response.setContentType("application/json");
try {
    log.info("The parameters is : ${params}")
    
    def formValue = request.getPart('project')
    def projectName = new String(formValue.getInputStream().getBytes())
    
    formValue = request.getPart('path')
    def fileName = new String(formValue.getInputStream().getBytes())
    
    def imagePart = request.getPart('file') // image is the parameter name 
    def inputStream = imagePart.getInputStream();
    def imageBytes = inputStream.getBytes()
    
    def dumpFile = Project.createFile(projectName, fileName)
    dumpFile << imageBytes
    
    builder([status:"succes"])
    print builder.toString()
} catch (Exception ex) {
    log.error("Failed to execute the upload  ${ex.getMessage()}",ex)
    builder([status:"failure: ${ex.getMessage()}"])
    print builder.toString()
}