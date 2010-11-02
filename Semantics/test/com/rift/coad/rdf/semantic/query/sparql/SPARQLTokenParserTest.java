/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.query.sparql;

import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class SPARQLTokenParserTest extends TestCase {
    
    public SPARQLTokenParserTest(String testName) {
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
     * Test of generateQuery method, of class SPARQLTokenParser.
     */
    public void testGenerateQuery() throws Exception {
        System.out.println("generateQuery");
        String query =
                "SELECT ?s WHERE { ?s a <http://www.coadunation.net/schema/rdf/1.0/organisation#Organisation> ." +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Id>  $. " +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Name>  $. " +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Surname>  ${surname}. " +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Username>  ${username}. }";
        SPARQLTokenParser instance = new SPARQLTokenParser(query);
        instance.setIndexValue(0, "1");
        instance.setIndexValue(1, "test");
        instance.setKeyValue("surname", "surname");
        instance.setKeyValue("username", "username");
        String expResult = 
                "SELECT ?s WHERE { ?s a <http://www.coadunation.net/schema/rdf/1.0/organisation#Organisation> ." +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Id>  \"1\". " +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Name>  \"test\". " +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Surname>  \"surname\". " +
                "?s  <http://www.coadunation.net/schema/rdf/1.0/organisation#Username>  \"username\". }";
        String result = instance.generateQuery();
        assertEquals(expResult, result);
    }

}
