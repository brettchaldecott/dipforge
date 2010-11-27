/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.change;

import com.rift.coad.change.rdf.objmapping.change.ActionDefinition;
import com.rift.coad.change.rdf.objmapping.change.ActionInfo;
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.change.Request;
import com.rift.coad.change.rdf.objmapping.change.task.Block;
import com.rift.coad.change.rdf.objmapping.change.task.Call;
import com.rift.coad.change.request.RequestFactoryDaemon;
import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;
import com.rift.coad.datamapperbroker.util.DataMapperBrokerUtil;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.number.RDFLong;
import com.rift.coad.util.connection.ConnectionManager;
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
public class ChangeTest {

    public ChangeTest() {
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
    public void testChangeManager() throws Exception {
        // register one
        TestResult.getInstance().reset();
        DataMapperBrokerUtil util1 = new DataMapperBrokerUtil("test2", "test/DataMapperClient1");
        DataMapperMethod method1 = new DataMapperMethod("test2","method1");
        util1.register(new DataMapperMethod[] {method1,new DataMapperMethod("test2","method2")});

        // register two
        DataMapperBrokerUtil util2 = new DataMapperBrokerUtil("test3", "test/DataMapperClient2");
        DataMapperMethod method2 = new DataMapperMethod("test3","method2");
        util2.register(new DataMapperMethod[] {new DataMapperMethod("test3","method1"),method2});

        // sleep for 2 seconds
        Thread.sleep(2000);

        // register the action
        ChangeManagerDaemon changeManager = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "change/ChangeManagerDaemon");

        ActionInfo actionInfo = new ActionInfo("test2", "the second test");
        changeManager.addAction(actionInfo);

        // register the flow
        Call call1 = new Call("method1", "call method 1", method1);
        Call call2 = new Call("method2", "call method 2", method2);
        call1.setNext(call2);
        ActionTaskDefinition parent = new Block("Block","Test Start",call1);
        ActionDefinition action = new ActionDefinition("test", actionInfo,parent);
        //ActionDefinition action = new ActionDefinition("test", actionInfo);
        changeManager.addActionDefinition(action);

        // create a request
        RequestFactoryDaemon requestDaemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class, "change/request/RequestFactoryDaemon");
        Request request = new Request("test2");
        RDFLong dataType = new RDFLong((long)10);
        dataType.setIdForDataType("test");
        request.setData(dataType);
        request.setDependancies(new DataType[] {});
        requestDaemon.createRequest(request);

        // wait for the result.
        assertEquals(2, TestResult.getInstance().waitForComplete(10 * 1000, 2, 2));
    }


}