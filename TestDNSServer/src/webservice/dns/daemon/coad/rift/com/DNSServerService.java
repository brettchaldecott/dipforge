/**
 * DNSServerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.dns.daemon.coad.rift.com;

public interface DNSServerService extends java.rmi.Remote {
    public void createZone(java.lang.String zoneName, java.lang.String zoneContent) throws java.rmi.RemoteException, webservice.dns.daemon.coad.rift.com.DNSException;
    public void createSecondaryZone(java.lang.String zoneName, java.lang.String zoneSource) throws java.rmi.RemoteException, webservice.dns.daemon.coad.rift.com.DNSException;
    public void updateZone(java.lang.String zoneName, java.lang.String zoneContent) throws java.rmi.RemoteException, webservice.dns.daemon.coad.rift.com.DNSException;
    public void removeZone(java.lang.String zoneName) throws java.rmi.RemoteException, webservice.dns.daemon.coad.rift.com.DNSException;
    public java.lang.Object[] listZones(int zoneType) throws java.rmi.RemoteException, webservice.dns.daemon.coad.rift.com.DNSException;
    public java.lang.String getName() throws java.rmi.RemoteException;
    public java.lang.String getVersion() throws java.rmi.RemoteException;
    public java.lang.String getDescription() throws java.rmi.RemoteException;
    public java.lang.String getStatus() throws java.rmi.RemoteException, webservice.dns.daemon.coad.rift.com.DNSException;
    public java.lang.String getZone(java.lang.String zoneName) throws java.rmi.RemoteException, webservice.dns.daemon.coad.rift.com.DNSException;
}
