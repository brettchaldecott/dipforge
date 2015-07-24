/*
 * <Add library description here>
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
 * CodebaseIORInterceptor.java
 *
 * This interceptor is responsible for setting the code base within an IOR so
 * that the stub code can be download when required.
 *
 * $Revision: 1.1.1.1.2.1 $
 */

// package path
package com.rift.coad.lib.interceptor.iiop;

// java imports
import java.net.InetAddress;
import org.omg.CORBA.Any;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.ORB;
import org.omg.IOP.Codec;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.TAG_JAVA_CODEBASE;
import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.PortableInterceptor.IORInfo;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.interceptor.InterceptorException;
import com.rift.coad.lib.httpd.RequestListenerThread;
import com.rift.coad.lib.httpd.HttpDaemon;

/**
 * This interceptor is responsible for setting the code base within an IOR so
 * that the stub code can be download when required.
 *
 * @author Brett Chaldecott
 */
public class CodebaseIORInterceptor extends LocalObject implements 
        IORInterceptor {
    
    // class constants
    private final static String URL_FORMAT = "http://%s:%d/codebase/%s";
    
    // the class log variable
    protected static Logger log =
            Logger.getLogger(CodebaseIORInterceptor.class);
    
    // private member variables
    private String hostname = null;
    private int port = 0;
    private Codec codec = null;
    
    /** 
     * Creates a new instance of CodebaseIORInterceptor.
     *
     * @exception InterceptorException
     */
    public CodebaseIORInterceptor(Codec codec) throws InterceptorException {
        this.codec = codec;
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    RequestListenerThread.class);
            // set the port
            hostname = config.getString(RequestListenerThread.HTTP_HOST,
                    InetAddress.getLocalHost().getCanonicalHostName());
            port = (int)config.getLong(RequestListenerThread.HTTP_PORT,
                    HttpDaemon.DEFAULT_PORT);
            
        } catch (Exception ex) {
            throw new InterceptorException("Failed to init the code base " +
                    "IOR interceptor because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the name of this interceptor
     *
     * @return the name of this interceptor.
     */
    public String name() {
        return "CodebaseIORInterceptor";
    }
    
    
    /**
     * This method is called to destroy this interceptor
     */
    public void destroy() {
        // do nothing
    }
    
    
    /**
     * A server side ORB calls the establish_components operation on all 
     * registered IORInterceptor instances when it is assembling the list of 
     * components that will be included in the profile or profiles of an object 
     * reference.
     *
     * @param info The information required to setup this ior.
     */
    public void establish_components(IORInfo info) {
        try {
            String stubcodeName = DeploymentLoader.ClassLoaderLookup.getInstance().
                    getStubCodeForLoader(Thread.currentThread().
                    getContextClassLoader());
            if (stubcodeName != null) {
                String url = String.format(URL_FORMAT,hostname,port,
                        stubcodeName);
                Any any = ORB.init().create_any();
                any.insert_string(url);
                info.add_ior_component(new TaggedComponent(
                        TAG_JAVA_CODEBASE.value,codec.encode_value(any)));
            }
        } catch (Exception ex) {
            log.error("Failed to add the entry to the ior : " + ex.getMessage(),
                    ex);
        }
    }
}
