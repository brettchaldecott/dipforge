/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo.obj.test;

import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import com.rift.coad.rdf.semantic.annotation.PropertyNamespace;
import com.rift.coad.rdf.semantic.annotation.PropertyURI;

/**
 *
 * @author brettc
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/test")
@LocalName("TestClass")
public class TestObject {

    /**
     * The getter test
     * @return The string
     */
    @PropertyLocalName("GetTest")
    public String getTest() {
        return "get";
    }


    @PropertyNamespace("http://www.coadunation.net/schema/rdf/2.0/test")
    @PropertyLocalName("IsTest")
    public boolean isTest() {
        return true;
    }

    @PropertyURI("http://www.coadunation.net/schema/rdf/2.0/test#SetTest")
    public void setTest(boolean value) {

    }

    @PropertyLocalName("ListValues")
    public String[] listValues() {
        return null;
    }
}
