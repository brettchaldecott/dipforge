/*
 * RPCClientTestTest.java
 * JUnit based test
 *
 * Created on 22 February 2007, 02:51
 */

package test.client;

import java.util.Hashtable;
import javax.rmi.PortableRemoteObject;
import junit.framework.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.InitialContext;
/**
 *
 * @author Admin
 */
public class RPCClientTestTest extends TestCase {
    
    public RPCClientTestTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RPCClientTestTest.class);
        
        return suite;
    }

    /**
     * Test of runBasicTest method, of class test.client.RPCClientTest.
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
            
            Object obj = ctx.lookup(System.getProperty("test2.url"));
            test.client.RPCClientTest beanInterface =
                    (test.client.RPCClientTest)
                    PortableRemoteObject.narrow(obj,
                    test.client.RPCClientTest.class);
            
            if (beanInterface == null) {
                throw new Exception("narrow failed.");
            } else {
                beanInterface.runBasicTest("huh");
                beanInterface.runBasicMessageTest("do you think i'm sexy?");
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
            throw new Exception(ex);
        }
    }

    
}
