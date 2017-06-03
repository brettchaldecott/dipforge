/*
 * dipforge: Description
 * Copyright (C) Fri Apr 20 07:55:38 SAST 2012 owner 
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
 * RequestHandler.groovy
 */

package com.dipforge.request


// imports
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.change.request.Request;
import com.rift.coad.change.request.RequestData;
import com.rift.coad.change.request.RequestEvent;


/**
 * This object provides the access to the RDF layer.
 * 
 * @author brett chaldecott
 */
class RequestHandler {
    
    /**
     * The child request wrapper
     */
    public class RequestWrapper {
        String project
        String action
        //def data
        //def dependancies
        def children
        Request request
        
        
        /**
         * The default constructor
         */
        //public RequestWrapper() {
        //    
        //    this.dependancies = [];
        //    this.children = [];
        //}
        
        public RequestWrapper(String project, String action, def data) {
            this.project = project;
            this.action = action;
            this.data = data;
            //this.dependancies = [];
            //this.children = [];
            generateRequest(data,dependancies);
        }
        
        public RequestWrapper(String project, String action, def data, 
            def dependancies) {
            this.project = project;
            this.action = action;
            //this.data = data;
            //this.dependancies = dependancies;
            this.children = [];
            generateRequest(data,dependancies);
        }
        
        public RequestWrapper(String project, String action, def data, 
            def dependancies, def children) {
            this.project = project;
            this.action = action;
            //this.data = data;
            //this.dependancies = dependancies;
            this.children = children;
            generateRequest(data,dependancies);
        }
        
        
        public String getProject() {
            return project
        }
        
        //public void setProject(String project) {
        //    this.project = project
        //}
        
        public String getAction() {
            return action;
        }
        
        //public void setAction(String action) {
        //    this.action = action;
        //}
        
        /*public def getData() {
            return data;
        }
        
        public void setData(def data) {
            this.data = data;
        }
        
        public def getDependancies() {
            return dependancies;
        }
        
        public void setDependancies(def dependancies) {
            this.dependancies = dependancies;
        }*/
        
        public def getChildren() {
            return children
        }
        
        public void setChildren(def children) {
            this.children = children
        }
        
        /*public void addChild(def child) {
            this.children(child)
        }*/
        
        
        /**
         * This method creates all the children
         * 
         * @param project The project.
         * @param action The action name
         * @param data The data associated with this call.
         */
        public def createChild(String project, String action, def data) {
            def child = new RequestWrapper(project,action,data)
            this.children.add(child);
            return child;
        }
        
        
        /**
         * This method is called to create the children with dependancies.
         * 
         * @param project The project refenece.
         * @param action The action.
         * @param data The data.
         * @param dependancies The list of dependancies.
         */
        public def createChild(String project, String action, def data, 
                def dependancies) {
            def child = new RequestWrapper(project,action,data,dependancies)
            this.children.add(child);
            return child;
        }
        
        /**
         * This method is called to generate the request data
         */
        def generateRequest(def data, def dependancies) {
            RequestData requestData = new RequestData(this.data.getId(), this.data.builder.classDef.getURI().toString(),
                    this.data.toXML(), data.builder.classDef.getLocalName())
            request = new Request(RandomGuid.getInstance().getGuid(), 
                    project, requestData, action, new java.util.Date(), null,
                    new java.util.ArrayList<RequestEvent>())
            if (dependancies.size() > 0) {
                java.util.List<RequestData> dependancyList = new java.util.ArrayList<RequestData>();
                for (dependance in dependancies) {
                    dependancyList.add(new RequestData(dependance.getId(), dependance.builder.classDef.getURI().toString(),
                            dependance.toXML(), dependance.builder.classDef.getLocalName()))
                }
                request.setDependencies(dependancyList)
            }
            // this method loo
            def classProperties = this.data.builder.classDef.listProperties()
            for (classProperty in classProperties) {
                if (classProperty.hasRange()) {
                    continue
                }
                java.util.List<RequestData> dependancyList = request.getDependencies()
                def propertyName = classProperty.getLocalname()
                if (this.data."${propertyName}" == null) {
                    continue;
                }
                if (this.data."${propertyName}" instanceof java.util.List) {
                    for (def prop : this.data."${propertyName}") {
                        dependancyList.add(new RequestData(prop.getId(), prop.builder.classDef.getURI().toString(),
                                prop.toXML(), prop.getId()))
                    }
                } else {
                    dependancyList.add(new RequestData(this.data."${propertyName}".getId(), this.data."${propertyName}".builder.classDef.getURI().toString(),
                                this.data."${propertyName}".toXML(), propertyName))
                }
            }
            data = null
            dependancies = null
        }
        
        
        /**
         * This method makes the request onto the request broker
         */
        def createRequest() {
            
            for (child in this.children) {
                request.getChildren().add(child.createRequest());
            }
            return request;
        }
        
        
        
    } 
    
    // private member variables    
    static def log = Logger.getLogger("com.dipforge.request.RequestHandler");
    
    String project
    String action
    def data
    def dependancies
    def children
    
    
    /**
     * The request wrapper constructor.
     * 
     * @param project The name of the project this request is being makde from.
     * @param action The action that is being performed on this data
     * @param data The data
     */
    RequestHandler (String project, String action, def data) {
        this.project = project
        this.action = action
        this.data = data
        this.dependancies = []
        this.children = []
    }
    
    
    /**
     * This constructor sets all the parameters.
     * 
     * @param project The name of the project
     * @param action The name of the action.
     * @param data The data object.
     * @param dependancies The list of dependancies.
     */
    RequestHandler (String project, String action, def data, def dependancies) {
        this.project = project
        this.action = action
        this.data = data;
        this.dependancies = dependancies;
        this.children = []
    }
    
    
    /**
     * This method returns a reference to the new request handler
     */
    public static RequestHandler getInstance(String project, String action, def data) {
        return new RequestHandler (project, action, data)
    }
    
    
    /**
     * This method returns a reference to the new request handler
     */
    public static RequestHandler getInstance(String project, String action, def data, def dependancies) {
        return new RequestHandler (project, action, data, dependancies)
    }
    
    
    /**
     * This method creates all the children
     * 
     * @param project The project.
     * @param action The action name
     * @param data The data associated with this call.
     */
    public def createChild(String project, String action, def data) {
        def child = new RequestWrapper(project,action,data)
        this.children.add(child);
        return child;
    }
    
    
    /**
     * This method is called to create the children with dependancies.
     * 
     * @param project The project refenece.
     * @param action The action.
     * @param data The data.
     * @param dependancies The list of dependancies.
     */
    public def createChild(String project, String action, def data, 
            def dependancies) {
        def child = new RequestWrapper(project,action,data,dependancies)
        this.children.add(child);
        return child;
    }
    
    
    /**
     * This method creates a new child using the parameters.
     * 
     * @param project The project name
     * @param action The action
     * @param data The data.
     * @param dependancies The dependancies.
     * @param children The children references.
     */
    public def createChild(String project, String action, def data, 
            def dependancies, def children) {
        def child = new RequestWrapper(project,action,data,dependancies,children)
        this.children.add(child);
        return child;
    }
    
    
    /**
     * This method makes the request onto the request broker
     */
    def makeRequest() {
        RequestBrokerConnector connector = new RequestBrokerConnector();
        
        RequestData requestData = new RequestData(this.data.getId(), this.data.builder.classDef.getURI().toString(),
                this.data.toXML(), data.builder.classDef.getLocalName())
        Request request = new Request(RandomGuid.getInstance().getGuid(), 
                project, requestData, action, new java.util.Date(), null,
                new java.util.ArrayList<RequestEvent>())
        if (dependancies.size() > 0) {
            java.util.List<RequestData> dependancyList = new java.util.ArrayList<RequestData>();
            for (dependance in dependancies) {
                dependancyList.add(new RequestData(dependance.getId(), dependance.builder.classDef.getURI().toString(),
                        dependance.toXML(), dependance.builder.classDef.getLocalName()))
            }
            request.setDependencies(dependancyList)
        }
        // this method loo
        def classProperties = this.data.builder.classDef.listProperties()
        for (classProperty in classProperties) {
            if (classProperty.hasRange()) {
                continue
            }
            java.util.List<RequestData> dependancyList = request.getDependencies()
            def propertyName = classProperty.getLocalname()
            if (this.data."${propertyName}" == null) {
                continue;
            }
            if (this.data."${propertyName}" instanceof java.util.List) {
                for (def prop : this.data."${propertyName}") {
                    dependancyList.add(new RequestData(prop.getId(), prop.builder.classDef.getURI().toString(),
                            prop.toXML(), prop.getId()))
                }
            } else {
                dependancyList.add(new RequestData(this.data."${propertyName}".getId(), this.data."${propertyName}".builder.classDef.getURI().toString(),
                            this.data."${propertyName}".toXML(), propertyName))
            }
        }
        
        for (child in this.children) {
            request.getChildren().add(child.createRequest());
        }
        
        
        connector.getBroker().createRequest(request) 
    }
}