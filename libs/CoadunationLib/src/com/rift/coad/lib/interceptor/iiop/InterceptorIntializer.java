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
 * InterceptorIntializer.java
 *
 * This class is responsible for initializing all the IIOP interceptors.
 */

// package path
package com.rift.coad.lib.interceptor.iiop;

// imports
import java.util.StringTokenizer;
import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ServerRequestInterceptor;
import java.lang.reflect.Constructor;
import org.omg.IOP.Codec;
import org.omg.IOP.Encoding;
import org.omg.IOP.ENCODING_CDR_ENCAPS;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This class is responsible for initializing all the IIOP interceptors.
 *
 * @author Brett Chaldecott
 */
public class InterceptorIntializer extends LocalObject implements
        ORBInitializer {
    
    // class constants
    private static final String CLIENT_INTERCEPTORS = "client_interceptors";
    private static final String SERVER_INTERCEPTORS = "server_interceptors";
    
    // local configuration
    private Configuration config = null;
    
    /**
     * Creates a new instance of InterceptorIntializer
     *
     * @exception SecurityInterceptorException
     */
    public InterceptorIntializer() throws SecurityInterceptorException {
        try {
            config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
        } catch (Exception ex) {
            throw new SecurityInterceptorException(
                    "Failed to retrieve the configuration : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * Called during ORB initialization.
     *
     * @param info The object to add the interceptors to
     */
    public void pre_init(ORBInitInfo info) {
        
    }
    
    
    /**
     * Called during ORB initialization.
     *
     * @param info The object to add the interceptors to
     */
    public void post_init(ORBInitInfo info) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(config.getString(
                    CLIENT_INTERCEPTORS),",");
            while (tokenizer.hasMoreTokens()) {
                Class ref = Class.forName(tokenizer.nextToken().trim());
                System.out.println("Retrieve interceptor class : " + 
                        ref.getName());
                Constructor constructor = ref.getConstructor(ORBInitInfo.class);
                info.add_client_request_interceptor(
                        (ClientRequestInterceptor)constructor.newInstance(info));
            }
            tokenizer = new StringTokenizer(config.getString(
                    SERVER_INTERCEPTORS),",");
            while (tokenizer.hasMoreTokens()) {
                Class ref = Class.forName(tokenizer.nextToken().trim());
                System.out.println("Retrieve interceptor class : " + 
                        ref.getName());
                Constructor constructor = ref.getConstructor(
                        ORBInitInfo.class);
                info.add_server_request_interceptor(
                        (ServerRequestInterceptor)constructor.newInstance(info));
                System.out.println("Add interceptor : " + ref.getName());
            }
            
            // add in the ior interceptor
            Encoding encoding = new Encoding(ENCODING_CDR_ENCAPS.value,
                    (byte)1,/* GIOP version */
                    (byte)2 /* GIOP version */);
            Codec codec = info.codec_factory().create_codec(encoding);
            info.add_ior_interceptor(new CodebaseIORInterceptor(codec));
        } catch (Exception ex) {
            System.out.println("Failed to initialize the interceptor");
            ex.printStackTrace(System.out);
            throw new SecurityInterceptorException(
                    "Failed to init the interceptors : " + ex.getMessage(),ex);
        }
    }
    
    
    
    
}
