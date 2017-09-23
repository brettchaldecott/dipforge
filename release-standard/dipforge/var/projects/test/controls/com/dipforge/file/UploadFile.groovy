/*
 * test: Description
 * Copyright (C) Wed Jun 21 17:00:23 UTC 2017 owner 
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

package com.dipforge.file

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


def log = Logger.getLogger("com.dipforge.log.com.dipforge.file.UploadFile");

response.setContentType("application/json");
try {
    
    //log.info("The parameters is : ${params}")
    
    //FileItemFactory factory = new DiskFileItemFactory();
    //ServletFileUpload upload = new ServletFileUpload(factory);
    
    
    //javax.servlet.http.HttpServletRequest httpRequest = (javax.servlet.http.HttpServletRequest)request;
    
    //List items = upload.parseRequest(httpRequest);
    //Iterator iterator = items.iterator();
    
    //iterator = items.iterator();
    //while (iterator.hasNext()) {
    //    FileItem item = (FileItem) iterator.next();

    //    if (!item.isFormField()) {
    //        String fileName = item.getName();

    //        File path = new File("/tmp/uploads/${id}/");
    //        if (!path.exists()) {
    //            boolean status = path.mkdirs();
    //        }

    //        File uploadedFile = new File(path + "/" + fileName);
    //        log.info(uploadedFile.getAbsolutePath());
    //        item.write(uploadedFile);
    //    }
    //}
    
    def parts = request.getParts()
    log.info("The list of parts ${parts}")
    parts.each{ part ->
        log.info("The parts name ${part.getName()} ${part.getContentType()}")
    }
    
    //def requestParameterNames = request.getParameterNames()
    //log.info("The list of parameters ${requestParameterNames}")
    //requestParameterNames.each{ name ->
    //    log.info("The name of parameter ${name}")
    //}
    
    def formValue = request.getPart('id')
    def id = new String(formValue.getInputStream().getBytes())
    
    def imagePart = request.getPart('file') // image is the parameter name 
    def inputStream = imagePart.getInputStream();
    def imageBytes = inputStream.getBytes()
    
    def dumpFile = new File("/tmp/${id}.dmp")
    dumpFile << imageBytes
    
    print "{status: 'success'}"
} catch (Exception ex) {
    log.error("Failed to execute the upload  ${ex.getMessage()}",ex)
    print "{status: 'failure: ${ex.getMessage()}' }"
}