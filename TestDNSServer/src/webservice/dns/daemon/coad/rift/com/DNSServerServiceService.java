/**
 * DNSServerServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.dns.daemon.coad.rift.com;

public interface DNSServerServiceService extends javax.xml.rpc.Service {
    public java.lang.String getManagementAddress();

    public webservice.dns.daemon.coad.rift.com.DNSServerService getManagement() throws javax.xml.rpc.ServiceException;

    public webservice.dns.daemon.coad.rift.com.DNSServerService getManagement(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
