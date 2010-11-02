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
public class TestDataMapper2 implements DataMapper {


    public DataType execute(String serviceId, String method, DataType[] parameters) throws DataMapperException, RemoteException {
        TestResult.getInstance().complete("test3");
        return null;
    }

}
