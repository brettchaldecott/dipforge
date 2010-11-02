/**
 * FetchMailServerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.email.daemon.coad.rift.com;

public class FetchMailServerServiceLocator extends org.apache.axis.client.Service implements webservice.email.daemon.coad.rift.com.FetchMailServerService {

    public FetchMailServerServiceLocator() {
    }


    public FetchMailServerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FetchMailServerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FetchMail
    private java.lang.String FetchMail_address = "http://newbie.local:8085/email/FetchMail";

    public java.lang.String getFetchMailAddress() {
        return FetchMail_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FetchMailWSDDServiceName = "FetchMail";

    public java.lang.String getFetchMailWSDDServiceName() {
        return FetchMailWSDDServiceName;
    }

    public void setFetchMailWSDDServiceName(java.lang.String name) {
        FetchMailWSDDServiceName = name;
    }

    public webservice.email.daemon.coad.rift.com.FetchMailServer getFetchMail() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FetchMail_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFetchMail(endpoint);
    }

    public webservice.email.daemon.coad.rift.com.FetchMailServer getFetchMail(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            webservice.email.daemon.coad.rift.com.FetchMailSoapBindingStub _stub = new webservice.email.daemon.coad.rift.com.FetchMailSoapBindingStub(portAddress, this);
            _stub.setPortName(getFetchMailWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFetchMailEndpointAddress(java.lang.String address) {
        FetchMail_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (webservice.email.daemon.coad.rift.com.FetchMailServer.class.isAssignableFrom(serviceEndpointInterface)) {
                webservice.email.daemon.coad.rift.com.FetchMailSoapBindingStub _stub = new webservice.email.daemon.coad.rift.com.FetchMailSoapBindingStub(new java.net.URL(FetchMail_address), this);
                _stub.setPortName(getFetchMailWSDDServiceName());
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
        if ("FetchMail".equals(inputPortName)) {
            return getFetchMail();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("com.rift.coad.daemon.email.webservice", "FetchMailServerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("com.rift.coad.daemon.email.webservice", "FetchMail"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FetchMail".equals(portName)) {
            setFetchMailEndpointAddress(address);
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
