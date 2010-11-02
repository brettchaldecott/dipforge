/*
 * ResourceManager.java
 *
 * Created on 28 December 2009, 2:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.rift.coad.catalog.client.types;
import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import java.util.List;

/**
 * This interface represents a resource manager.
 *
 * @author brett chaldecott
 */
public interface ResourceManager extends RemoteService{

    /**
     * This method returns a list of the resources.
     *
     * @return This list of resources.
     * @throws ResourcesException
     */
    public List<ResourceBase> listTypes() throws ResourceException;
}
