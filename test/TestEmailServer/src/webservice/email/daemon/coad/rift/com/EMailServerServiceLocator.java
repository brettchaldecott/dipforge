/**
 * EMailServerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.email.daemon.coad.rift.com;

public class EMailServerServiceLocator extends org.apache.axis.client.Service implements webservice.email.daemon.coad.rift.com.EMailServerService {

    public EMailServerServiceLocator() {
    }


    public EMailServerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EMailServerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Management
    private java.lang.String Management_address = "http://newbie.local:8085/email/Management";

    public java.lang.String getManagementAddress() {
        return Management_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ManagementWSDDServiceName = "Management";

    public java.lang.String getManagementWSDDServiceName() {
        return ManagementWSDDServiceName;
    }

    public void setManagementWSDDServiceName(java.lang.String name) {
        ManagementWSDDServiceName = name;
    }

    public webservice.email.daemon.coad.rift.com.EMailServer getManagement() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Management_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getManagement(endpoint);
    }

    public webservice.email.daemon.coad.rift.com.EMailServer getManagement(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            webservice.email.daemon.coad.rift.com.ManagementSoapBindingStub _stub = new webservice.email.daemon.coad.rift.com.ManagementSoapBindingStub(portAddress, this);
            _stub.setPortName(getManagementWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setManagementEndpointAddress(java.lang.String address) {
        Management_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (webservice.email.daemon.coad.rift.com.EMailServer.class.isAssignableFrom(serviceEndpointInterface)) {
                webservice.email.daemon.coad.rift.com.ManagementSoapBindingStub _stub = new webservice.email.daemon.coad.rift.com.ManagementSoapBindingStub(new java.net.URL(Management_address), this);
                _stub.setPortName(getManagementWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Management".equals(inputPortName)) {
            return getManagement();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("com.rift.coad.daemon.email.webservice", "EMailServerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("com.rift.coad.daemon.email.webservice", "Management"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Management".equals(portName)) {
            setManagementEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
