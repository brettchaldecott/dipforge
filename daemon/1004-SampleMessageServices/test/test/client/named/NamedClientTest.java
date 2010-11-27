/*
 * NamedClientTest.java
 * JUnit based test
 *
 * Created on 27 February 2007, 09:04
 */

package test.client.named;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import junit.framework.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Admin
 */
public class NamedClientTest extends TestCase {
    
    public NamedClientTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(NamedClientTest.class);
        
        return suite;
    }

    /**
     * Test of runBasicTest method, of class test.client.named.NamedClient.
     */
    public void testRunBasicTest() throws Exception {
        System.out.println("runBasicTest");
        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.rift.coad.client.naming." +
                    "CoadunationInitialContextFactory");
            env.put(Context.PROVIDER_URL,System.getProperty("coadunation.master"));
            env.put("com.rift.coad.username","test");
            env.put("com.rift.coad.password","112233");
            Context ctx = new InitialContext(env);
            
            Object obj = ctx.lookup(System.getProperty("test.url"));
            test.client.named.NamedClient beanInterface =
                    (test.client.named.NamedClient)
                    PortableRemoteObject.narrow(obj,
                    test.client.named.NamedClient.class);
            
            if (beanInterface == null) {
                throw new Exception("narrow failed.");
            } else {
                beanInterface.runBasicTest("huh");
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
            throw new Exception(ex);
        }
    }

    /**
     * Generated implementation of abstract class test.client.named.NamedClient. Please fill dummy bodies of generated methods.
     */
    private class NamedClientImpl implements NamedClient {

        public void runBasicTest(java.lang.String text) {
            // TODO fill the body in order to provide useful implementation
            
        }
    }

    
}
