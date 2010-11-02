/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.datamapperbroker;

import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;
import com.rift.coad.datamapperbroker.util.DataMapperBrokerUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class DataMapperTest {

    public DataMapperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * This method is used to test the data mapper client utils
     */
    @Test
    public void testDataMapperBrokerUtil() throws Exception {
        DataMapperBrokerUtil util = new DataMapperBrokerUtil("test", "test/TestManager");
        util.register(new DataMapperMethod[] {new DataMapperMethod("test","method1"),new DataMapperMethod("test","method2")});

        
    }


}