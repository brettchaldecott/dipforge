/**
 * EMailServerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.email.daemon.coad.rift.com;

public interface EMailServerService extends javax.xml.rpc.Service {
    public java.lang.String getManagementAddress();

    public webservice.email.daemon.coad.rift.com.EMailServer getManagement() throws javax.xml.rpc.ServiceException;

    public webservice.email.daemon.coad.rift.com.EMailServer getManagement(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
