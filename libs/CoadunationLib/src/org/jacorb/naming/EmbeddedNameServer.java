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
 * EmbeddedNameServer.java
 *
 * This object is responsible for starting the JacORB naming service. It starts
 * it in process and not as another process. This means this class copies a lot
 * from JacORB's own naming service.
 */

package org.jacorb.naming;

// java imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import org.jacorb.config.Configuration;
import org.jacorb.config.ConfigurationException;

// apache imports
//import org.apache.avalon.framework.configuration.Configuration;
//import org.apache.avalon.framework.configuration.ConfigurationException;
//import org.apache.avalon.framework.logger.Logger;

// JacORB imports
import org.jacorb.imr.util.ImRManager;
import org.jacorb.util.ObjectUtil;

// omg impors
import org.omg.CORBA.ORB;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer._ServantActivatorLocalBase;


/**
 * This object is responsible for starting the JacORB naming service. It starts
 * it in process and not as another process. This means this class copies a lot
 * from JacORB's own naming service.
 *
 * @author Brett Chaldecott
 */
public class EmbeddedNameServer {
    
    /**
    * Creates a new instance of EmbeddedNameServer
    */
    public EmbeddedNameServer(ORB orb, POA poa) throws NameServerException {
        try {
            // retrieve the orb configuration object
            Configuration config =
                    ((org.jacorb.orb.ORB)orb).getConfiguration();
            
            /* configure the name service using the ORB configuration */
            NameServer.configure(config);
            
            /* create a user defined poa for the naming contexts */
            org.omg.CORBA.Policy [] policies = new org.omg.CORBA.Policy[3];
            policies[0] =
                    poa.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID);
            policies[1] =
                    poa.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
            policies[2] =
                    poa.create_request_processing_policy(
                    RequestProcessingPolicyValue.USE_SERVANT_MANAGER);
            POA nsPOA = poa.create_POA("NameServer-POA",
                    poa.the_POAManager(),
                    policies);
            
            NamingContextImpl.init(orb, poa);
            NameServer.NameServantActivatorImpl servantActivator =
                    new NameServer.NameServantActivatorImpl( orb );
            servantActivator.configure(config);
            NamingContextImpl namingContext = new NamingContextImpl();
            nsPOA.set_servant_manager( servantActivator );
            nsPOA.the_POAManager().activate();
            
            byte[] oid = ( new String("_root").getBytes() );
            org.omg.CORBA.Object obj =
                    nsPOA.create_reference_with_id( oid, 
                    "IDL:omg.org/CosNaming/NamingContextExt:1.0");
            
            System.out.println("NS SERVER IOR: " + orb.object_to_string(obj));
            
            // free up the policies
            for (int i = 0; i < policies.length; i++)
                policies[i].destroy();
            
        } catch( ConfigurationException ex ) {
            throw new NameServerException("Failed to init the " +
                    "EmbeddedNameServer : " + ex.getMessage(),ex);
        } catch( Exception ex ) {
            throw new NameServerException("Failed to init the " +
                    "EmbeddedNameServer : " + ex.getMessage(),ex);
        }
    }
    
}
