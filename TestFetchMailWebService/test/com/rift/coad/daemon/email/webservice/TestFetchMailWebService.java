/*
 * TestFetchMailWebService.java
 * JUnit based test
 *
 * Created on May 1, 2008, 6:42 AM
 */

package com.rift.coad.daemon.email.webservice;

// junit imports
import junit.framework.*;

// java imports
import javax.xml.rpc.Stub;
import java.net.URL;

// test imports
import webservice.email.daemon.coad.rift.com.*;


/**
 * This object tests the fetch mail web service.
 *
 * @author brett chaldecott
 */
public class TestFetchMailWebService extends TestCase {
    
    public TestFetchMailWebService(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    /**
     * This method is used to test the fetchmail web service
     */
    public void testFetchMail() throws Exception {
        System.out.println("testEmailServerWebService");
        FetchMailServerServiceLocator locator = new 
                FetchMailServerServiceLocator();
        FetchMailServer server = locator.getFetchMail();
        Stub stub = (Stub)server;
        stub._setProperty(Stub.USERNAME_PROPERTY,"admin");
        stub._setProperty(Stub.PASSWORD_PROPERTY,"112233");
        assertEquals("1",server.getVersion());
        assertEquals("FetchMailServer",server.getName());
        assertEquals("The Fetch Mail Server",server.getDescription());
        
        server.addPOPAccount("test@test.com","test@test.com","localhost","112233");
        server.addPOPAccountWithDropBox("test2@test.com","test2@test.com","localhost","112233","test@test.com");
        FetchMailPOPAccount account = server.getPOPAccount("test@test.com");
        
        assertEquals(account.getAccount(), "test@test.com");
        assertEquals(account.getEmailAddress(), "test@test.com");
        assertEquals(account.getServer(), "localhost");
        assertEquals(account.getDropBox(), null);
        
        account = server.getPOPAccount("test2@test.com");
        
        assertEquals(account.getAccount(), "test2@test.com");
        assertEquals(account.getEmailAddress(), "test2@test.com");
        assertEquals(account.getServer(), "localhost");
        assertEquals(account.getDropBox(), "test@test.com");
        
        
        server.updatePOPAccountDropBox("test@test.com","test2@test.com");
        server.updatePOPAccountPassword("test@test.com","332211");
        
        account = server.getPOPAccount("test@test.com");
        
        assertEquals(account.getAccount(), "test@test.com");
        assertEquals(account.getEmailAddress(), "test@test.com");
        assertEquals(account.getServer(), "localhost");
        assertEquals(account.getDropBox(), "test2@test.com");
        
        String[] accounts = server.listPOPAccounts();
        boolean found1 = false;
        boolean found2 = false;
        for (int index = 0; index < accounts.length; index++) {
            if (accounts[index].equals("test@test.com")) {
                found1 = true;
            } else if (accounts[index].equals("test2@test.com")) {
                found2 = true;
            }
        }
        
        if (!found1 || !found2) {
            fail("Failed to find the addresses");
        }
        
        server.deletePOPAccount("test2@test.com");
        
        accounts = server.listPOPAccounts();
        found1 = false;
        found2 = false;
        for (int index = 0; index < accounts.length; index++) {
            if (accounts[index].equals("test@test.com")) {
                found1 = true;
            } else if (accounts[index].equals("test2@test.com")) {
                found2 = true;
            }
        }
        
        if (!found1 || found2) {
            fail("The addresses are currentl out of wack");
        }
        
        server.deletePOPAccount("test@test.com");        
        
        accounts = server.listPOPAccounts();
        found1 = false;
        found2 = false;
        for (int index = 0; index < accounts.length; index++) {
            if (accounts[index].equals("test@test.com")) {
                found1 = true;
            } else if (accounts[index].equals("test2@test.com")) {
                found2 = true;
            }
        }
        
        if (found1 || found2) {
            fail("Found the addresses");
        }
        
    }

}
