/**
 * EMailServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.email.daemon.coad.rift.com;

public interface EMailServer extends java.rmi.Remote {
    public java.lang.String[] listDomains() throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void addDomain(java.lang.String domain, java.lang.String dropbox) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void addDomain(java.lang.String domain) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void removeDomain(java.lang.String domain) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public java.lang.String getDomainDropBox(java.lang.String domain) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void setDomainDropBox(java.lang.String domain, java.lang.String dropbox) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void removeDomainDropBox(java.lang.String domain) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void createMailBox(java.lang.String domain, java.lang.String username, java.lang.String password, long quota) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void setMailBoxPassword(java.lang.String domain, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public long getMailBoxQuota(java.lang.String domain, java.lang.String username) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void setMailBoxQuota(java.lang.String domain, java.lang.String username, long quota) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void removeMailBox(java.lang.String domain, java.lang.String username) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public java.lang.String[] listMailBoxes(java.lang.String domain) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void createForward(java.lang.String domain, java.lang.String alias, java.lang.String[] targets) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void updateForward(java.lang.String domain, java.lang.String alias, java.lang.String[] targets) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public java.lang.String[] getForward(java.lang.String domain, java.lang.String alias) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void removeForward(java.lang.String domain, java.lang.String alias) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public java.lang.String[] listForwards(java.lang.String domain) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void createAlias(java.lang.String domain, java.lang.String username, java.lang.String alias) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void removeAlias(java.lang.String domain, java.lang.String username, java.lang.String alias) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public java.lang.String[] listAliases(java.lang.String domain, java.lang.String username) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void addRelay(java.lang.String address) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void removeRelay(java.lang.String address) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public java.lang.String[] listRelays() throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public java.lang.String getName() throws java.rmi.RemoteException;
    public java.lang.String getVersion() throws java.rmi.RemoteException;
    public java.lang.String getDescription() throws java.rmi.RemoteException;
    public java.lang.String getStatus() throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
}
