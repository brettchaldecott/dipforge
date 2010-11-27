/**
 * FetchMailServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.email.daemon.coad.rift.com;

public interface FetchMailServer extends java.rmi.Remote {
    public java.lang.String[] listPOPAccounts() throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void addPOPAccount(java.lang.String address, java.lang.String account, java.lang.String server, java.lang.String password) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void addPOPAccountWithDropBox(java.lang.String address, java.lang.String account, java.lang.String server, java.lang.String password, java.lang.String dropbox) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void updatePOPAccountPassword(java.lang.String address, java.lang.String password) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void updatePOPAccountDropBox(java.lang.String address, java.lang.String dropbox) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public webservice.email.daemon.coad.rift.com.FetchMailPOPAccount getPOPAccount(java.lang.String address) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public void deletePOPAccount(java.lang.String address) throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
    public java.lang.String getName() throws java.rmi.RemoteException;
    public java.lang.String getVersion() throws java.rmi.RemoteException;
    public java.lang.String getDescription() throws java.rmi.RemoteException;
    public java.lang.String getStatus() throws java.rmi.RemoteException, webservice.email.daemon.coad.rift.com.EmailException;
}
