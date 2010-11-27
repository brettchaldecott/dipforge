/**
 * FetchMailServerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.email.daemon.coad.rift.com;

public interface FetchMailServerService extends javax.xml.rpc.Service {
    public java.lang.String getFetchMailAddress();

    public webservice.email.daemon.coad.rift.com.FetchMailServer getFetchMail() throws javax.xml.rpc.ServiceException;

    public webservice.email.daemon.coad.rift.com.FetchMailServer getFetchMail(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
