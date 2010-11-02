/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base;

import com.rift.coad.rdf.objmapping.base.Phone;
import com.rift.coad.rdf.objmapping.base.phone.CellPhone;
import junit.framework.TestCase;

import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;
import thewebsemantic.binding.Jenabean;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;


/**
 *
 * @author brett
 */
public class TestBase extends TestCase {
    
    public TestBase(String testName) {
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
     * This object throws the basic exception.
     */
    public void testBasic() throws Exception {
        
        Model m = ModelFactory.createDefaultModel();
        Jenabean.instance().bind(m);
        Bean2RDF writer = new Bean2RDF(m);
        
        Phone testPhone = new Phone("011 700-5000");
        System.out.println("###### Testing this : " + testPhone.getDataName());
        //testPhone.setDataName("Phone-test");
        writer.save(testPhone);
        CellPhone testCellPhone = new CellPhone("083 300-5000");
        testCellPhone.setDataName("CellPhone-test");
        writer.save(testCellPhone);
        
        m.write(System.out, "RDF/XML-ABBREV");
        
        RDF2Bean reader = new RDF2Bean(m);
        Phone testPhone2 = (Phone)reader.load(Phone.class,"011 700-5000");
        assertEquals(testPhone2.getValue(), testPhone.getValue());
        if (testPhone2 instanceof CellPhone) {
            System.out.println("The cell phone is the right type");
        } else {
            System.out.println("The cell phone is not the right type");
        }
        
        Phone testPhone3 = (Phone)reader.load(CellPhone.class,"083 300-5000");
        assertEquals(testPhone3.getValue(), testCellPhone.getValue());
        
        if (testPhone3 instanceof CellPhone) {
            System.out.println("The cell phone is the right type");
        } else {
            System.out.println("The cell phone is not the right type");
        }
    }

}
