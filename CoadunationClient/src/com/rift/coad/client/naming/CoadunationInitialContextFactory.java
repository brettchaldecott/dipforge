/*
 * CoadunationClient: The client libraries for Coadunation. (RMI/CORBA)
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
 * CoadunationInitialContextFactory.java
 *
 * This Context Factory is responsible for initializing the context of for the
 * client side. So that RMI connection can be made to Coadunation.
 *
 * Revision: $ID
 */

package com.rift.coad.client.naming;

// java imports
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import org.omg.CORBA.ORB;

// Coadunation imports
import com.rift.coad.client.interceptor.iiop.ClientInterceptorIntializer;
import com.rift.coad.lib.interceptor.credentials.Login;

/**
 * This Context Factory is responsible for initializing the context of for the
 * client side. So that RMI connection can be made to Coadunation.
 *
 * @author Brett Chaldecott
 */
public class CoadunationInitialContextFactory implements InitialContextFactory {
    
    // class conconstants
    public final static String USERNAME = "com.rift.coad.username";
    public final static String PASSWORD = "com.rift.coad.password";
    
    // the static reference to the user login object.
    public static ThreadLocal userLogin = new ThreadLocal();
    
    /**
     * 
     * Creates a new instance of CoadunationInitialContextFactory
     */
    public CoadunationInitialContextFactory() {
    }
    
    /**
     * This method is responsible for instanciating the initial context using
     * the supplied environment.
     *
     * @return The context to return.
     * @param environment The environment to use to instanciate the url context.
     * @exception NamingException
     */
    public Context getInitialContext(Hashtable environment) throws 
            NamingException {
        if (environment.containsKey(USERNAME) && 
                environment.containsKey(PASSWORD)) {
            userLogin.set(new Login((String)environment.get(USERNAME), 
                    (String)environment.get(PASSWORD)));
        }
        else {
            userLogin.set(null);
        }
            
        // setup the orb
        System.setProperty("org.omg.PortableInterceptor.ORBInitializerClass." +
                ClientInterceptorIntializer.class.getName(),"");
        Properties properties = new Properties();
        properties.setProperty("org.omg.CORBA.ORBClass",
                    "org.jacorb.orb.ORB");
        properties.setProperty("org.omg.CORBA.ORBSingletonClass",
                    "org.jacorb.orb.ORBSingleton");
        ORB orb = ORB.init(new String[0],properties);
        
        // add the extra environmental variables
        environment.put(Context.INITIAL_CONTEXT_FACTORY,
                        "com.sun.jndi.cosnaming.CNCtxFactory");
        environment.put("java.naming.corba.orb",orb);
        
        if (environment.containsKey(Context.PROVIDER_URL)) {
            String url = "corbaloc:iiop:"  + 
                    (String)environment.get(Context.PROVIDER_URL) +
                    "/StandardNS/NameServer-POA/_root";
            environment.put(Context.PROVIDER_URL,url);
        }
        
        // instanciate the context
        return new CoadunationContext(orb,environment);
    }
}
