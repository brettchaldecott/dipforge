/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.organisation;

import com.rift.coad.rdf.objmapping.base.Name;
import com.rift.coad.rdf.objmapping.base.Phone;
import junit.framework.TestCase;
import java.util.Collection;
import java.util.Iterator;

// coadunation semantics
import com.rift.coad.rdf.semantic.*;
import com.rift.coad.rdf.semantic.config.Basic;

// coadunation imports
import com.rift.coad.rdf.objmapping.base.Name;
import com.rift.coad.rdf.objmapping.base.Phone;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.phone.CellPhone;
import com.rift.coad.rdf.objmapping.base.ip.IPv4;
import com.rift.coad.rdf.objmapping.base.ip.IPv6;
import com.rift.coad.rdf.objmapping.base.name.Surname;
import java.util.List;

/**
 * This is a test of the organisation object.
 * 
 * @author brett
 */
public class OrganisationTest extends TestCase {
    
    public OrganisationTest(String testName) {
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
     * Test of getId method, of class Organisation.
     */
    public void testOrganisation() throws Exception {
        System.out.println("testOrganisation");
        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();

        session.dump(System.out, "RDF/XML-ABBREV");
        
        Organisation test1 = new Organisation(new DataType[] {new Phone("011 300 5000"), new CellPhone("082 300 5000"), new IPv4("192.168.1.1"), new Surname("surname1")}, "1", "test1");
        System.out.println("Get id " + test1.getId());
        session.persist(test1);
        Organisation test2 = new Organisation(new DataType[] {new Phone("021 300 5000"), new CellPhone("083 300 5000"), new IPv6("192.168.1.2.1.2"), new Surname("surname1")}, "2", "test2");
        session.persist(test2);
        
        session.dump(System.out, "RDF/XML-ABBREV");
        
        //Organisation testOrg1 = (Organisation)reader.load(Organisation.class,new Long(1));
        //Organisation testOrg2 = (Organisation)reader.load(Organisation.class,new Long(2));
        
        //assertEquals(test1.getId(), testOrg1.getId());
        //assertEquals(test1.getName().getValue(), testOrg1.getName().getValue());
        List<SPARQLResultRow> rows = session.createSPARQLQuery(
                "SELECT ?s WHERE { ?s a <http://www.coadunation.net/schema/rdf/1.0/organisation#Organisation> }").execute();
        
        assertEquals(rows.size(),2);
        Organisation testOrg2 = rows.get(0).get(0).cast(Organisation.class);
        Resource resource = rows.get(0).get(0).getResource();
        Organisation testOrg21 = resource.get(Organisation.class);
        Organisation testOrg1 = rows.get(1).get(0).cast(Organisation.class);
        assertEquals(test1.getId(), testOrg1.getId());
        assertEquals(test1.getName(), testOrg1.getName());
        assertEquals(test2.getId(), testOrg2.getId());
        assertEquals(test2.getName(), testOrg2.getName());
        assertEquals(test2.getId(), testOrg21.getId());
        assertEquals(test2.getName(), testOrg21.getName());


        // check the number of attributes
        assertEquals(test1.getAttributes().length, testOrg1.getAttributes().length);
        assertEquals(test2.getAttributes().length, testOrg2.getAttributes().length);
        testOrg1 = session.get(Organisation.class, Organisation.class.getName(), "1");
        //testOrg1 = result3.iterator().next();
        assertEquals(test1.getId(), testOrg1.getId());
        assertEquals(test1.getName(), testOrg1.getName());
        assertEquals(testOrg1.getAttribute(Phone.class, "Phone").getValue(),"011 300 5000");
        assertEquals(testOrg1.getAttribute(CellPhone.class, "CellPhone").getValue(),"082 300 5000");
        assertEquals(testOrg1.getAttributes(CellPhone.class,CellPhone.class.getName()).size(),1);

        assertEquals(testOrg2.getAttribute(Phone.class, "Phone").getValue(),"021 300 5000");
        assertEquals(testOrg2.getAttribute(CellPhone.class, "CellPhone").getValue(),"083 300 5000");


        Phone phone = session.get(Phone.class,test1.getAttribute(Phone.class, "Phone"),"021 300 5000");
        assertEquals(phone.getValue(),"021 300 5000");


        rows = session.createSPARQLQuery(
                "SELECT ?s WHERE { ?s a <http://www.coadunation.net/schema/rdf/1.0/organisation#Organisation> ." +
                "?s <http://www.coadunation.net/schema/rdf/1.0/organisation#Name> ${name} }").setString("name", "test1").execute();

        assertEquals(1,rows.size());

    }

    

}
