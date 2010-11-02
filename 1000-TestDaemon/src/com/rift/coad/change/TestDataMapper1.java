/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.change;

import com.rift.coad.datamapper.DataMapper;
import com.rift.coad.datamapper.DataMapperException;
import com.rift.coad.rdf.objmapping.base.DataType;
import java.rmi.RemoteException;

/**
 *
 * @author brett
 */
public class TestDataMapper1 implements DataMapper {

    /**
     * This method is called to execute the test
     * @param serviceId
     * @param method
     * @param parameters
     * @return
     * @throws com.rift.coad.datamapper.DataMapperException
     * @throws java.rmi.RemoteException
     */
    public DataType execute(String serviceId, String method, DataType[] parameters) throws DataMapperException, RemoteException {
        TestResult.getInstance().complete("test2");
        return null;
    }

}
