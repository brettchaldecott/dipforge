/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.person;

import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.password.ClearTextPassword;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.config.Basic;
import junit.framework.TestCase;

/**
 * This object test the user object.
 *
 * @author brett chaldecott
 */
public class UserTest extends TestCase {
    
    public UserTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * Test of getObjId method, of class User.
     */
    public void testUser() throws Exception {
        System.out.println("testUser");
        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();

        User instance = new User(new DataType[]{}, "100","test", "test","test", new ClearTextPassword("test","testpassword"));
        session.persist(instance);

        session.dump(System.out, "RDF/XML-ABBREV");

        User result = session.get(User.class, User.class.getName(), instance.getObjId());

        assertEquals(instance.getUsername(), result.getUsername());
        assertEquals(instance.getPassword().getValue(), result.getPassword().getValue());
        assertEquals(instance.getObjId(), "test");
        assertEquals(result.getObjId(), "test");
        assertEquals(instance.getId(), "100");
        assertEquals(result.getId(), "100");

    }

    
}
