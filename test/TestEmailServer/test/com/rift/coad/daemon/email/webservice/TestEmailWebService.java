/*
 * NewEmptyJUnitTest.java
 * JUnit based test
 *
 * Created on April 30, 2008, 7:11 AM
 */

package com.rift.coad.daemon.email.webservice;

// java imports
import java.util.List;

// junit imports
import junit.framework.*;

// java imports
import javax.xml.rpc.Stub;
import java.net.URL;

// test imports
import webservice.email.daemon.coad.rift.com.*;


/**
 *
 * @author brett
 */
public class TestEmailWebService extends TestCase {
    
    public TestEmailWebService(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    // TODO add test methods here. The name must begin with 'test'. For example:
    public void testEmailWebService() throws Exception {
        System.out.println("testEmailServerWebService");
        EMailServerServiceLocator locator = new EMailServerServiceLocator();
        EMailServer server = locator.getManagement();
        Stub stub = (Stub)server;
        stub._setProperty(Stub.USERNAME_PROPERTY,"admin");
        stub._setProperty(Stub.PASSWORD_PROPERTY,"112233");
        assertEquals("1",server.getVersion());
        assertEquals("EMail Server",server.getName());
        assertEquals("Coadunation EMail Server",server.getDescription());
        
        
        server.addDomain("testdomain1.com");
        
        String[] domains = server.listDomains();
        
        boolean found = false;
        for (int index = 0; index < domains.length; index++) {
            if (domains[index].equals("testdomain1.com")) { 
                found = true;
                break;
            }
        }
        
        if (found == false) {
            fail("The added entry was not found");
        }
        
        server.createMailBox("testdomain1.com","fred","112233",0);
        
        String[] mailboxes = server.listMailBoxes("testdomain1.com");
        
        for (int index = 0; index < mailboxes.length; index++) {
            if (mailboxes[index].equals("fred@testdomain1.com")) { 
                found = true;
                break;
            }
        }
        
        if (found == false) {
            fail("The added entry was not found");
        }
        
        server.setMailBoxPassword("testdomain1.com","fred","password");
        
        server.createAlias("testdomain1.com","fred","info");
        String[] aliases = server.listAliases("testdomain1.com","fred");
        
        found = false;
        for (int index = 0; index < aliases.length; index++) {
            if (aliases[index].equals("info@testdomain1.com")) { 
                found = true;
                break;
            }
        }
        
        if (!found) {
            fail("The alias was not found");
        }
        
        server.removeAlias("testdomain1.com","fred","info");
        
        aliases = server.listAliases("testdomain1.com","fred");
        
        found = false;
        for (int index = 0; index < aliases.length; index++) {
            if (aliases[index].equals("info@testdomain1.com")) { 
                found = true;
                break;
            }
        }
        
        if (found) {
            fail("The alias was found");
        }
        
        server.createForward("testdomain1.com","forward",new String[] 
        {"fred@testdomain1.com","test@gmail.com"});
        
        String[] forwards = server.listForwards("testdomain1.com");
        
        found = false;
        for (int index = 0; index < forwards.length; index++) {
            if (forwards[index].equals("forward@testdomain1.com")) { 
                found = true;
                break;
            }
        }
        
        if (!found) {
            fail("The forward was not found");
        }
        
        String[] forward = server.getForward("testdomain1.com","forward");
        found = false;
        for (int index = 0; index < forward.length; index++) {
            found = false;
            if (forward[index].equals("fred@testdomain1.com")) { 
                found = true;
            } else if (forward[index].equals("test@gmail.com")) { 
                found = true;
            }
        }
        
        if (!found) {
            fail("The forward was not found");
        }
        
        server.updateForward("testdomain1.com","forward",new String[] 
        {"fred@testdomain1.com","test2@gmail.com"});
        
        forward = server.getForward("testdomain1.com","forward");
        found = false;
        for (int index = 0; index < forward.length; index++) {
            found = false;
            if (forward[index].equals("fred@testdomain1.com")) { 
                found = true;
            } else if (forward[index].equals("test2@gmail.com")) { 
                found = true;
            }
        }
        
        if (!found) {
            fail("The forward was not found");
        }
        
        server.removeForward("testdomain1.com","forward");
        
        forwards = server.listForwards("testdomain1.com");
        
        found = false;
        for (int index = 0; index < forwards.length; index++) {
            if (forwards[index].equals("forward@testdomain1.com")) { 
                found = true;
                break;
            }
        }
        
        if (found) {
            fail("The forward was found");
        }
        
        server.removeMailBox("testdomain1.com","fred");
        
        mailboxes = server.listMailBoxes("testdomain1.com");
        
        found = false;
        for (int index = 0; index < mailboxes.length; index++) {
            if (mailboxes[index].equals("fred@testdomain1.com")) { 
                found = true;
                break;
            }
        }
        
        if (found) {
            fail("The added entry was found");
        }
        
        server.removeDomain("testdomain1.com");
        
        domains = server.listDomains();
        
        found = false;
        for (int index = 0; index < domains.length; index++) {
            if (domains[index].equals("testdomain1.com")) { 
                found = true;
                break;
            }
        }
        
        if (found) {
            fail("The entry should not have been found.");
        }
        
        
        server.addRelay("10.0.1");
        
        
        String[] relays = server.listRelays();
        
        found = false;
        for (int index = 0; index < relays.length; index++) {
            if (relays[index].equals("10.0.1")) { 
                found = true;
                break;
            }
        }
        
        if (!found) {
            fail("The newly added relay was not found.");
        }
        
        server.removeRelay("10.0.1");
        
        relays = server.listRelays();
        
        found = false;
        for (int index = 0; index < relays.length; index++) {
            if (relays[index].equals("10.0.1")) { 
                found = true;
                break;
            }
        }
        
        if (found) {
            fail("The newly added relay was found again.");
        }
        
    }

}
