/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * WebService.java
 *
 * This is a test web service.
 */

// Package path
package com.webservicetest.webservice;

// logging import
import org.apache.log4j.Logger;

// java imports
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

// extra imports
import com.extra.bean.Bean2Impl;
import com.extra.bean.Bean2Interface;
import com.extra.bean.TopInterface;
import com.extra.bean.MiddleInterface;

// coadunation imports
import com.rift.coad.lib.bean.test.CalledObjects;

/**
 * This is a test web service.
 *
 * @author Brett Chaldecott
 */
public class WebService implements WebServiceSEI {
    
    private Logger log =
            Logger.getLogger(WebService.class.getName());
    private Context context = null;
    
    /** 
     * Creates a new instance of WebService 
     */
    public WebService() {
        try {
            context = new InitialContext();
        } catch (Exception ex) {
            log.error("Failed to retrieve the initial context : " + 
                    ex.getMessage(), ex);
        }
    }
    
    /**
     * This method will be called to test the web service end point interface.
     *
     * @param msg The string containing the message for the server.
     * @return The containing the message from the server.
     */
    public String helloWorld(String msg) {
        try {
            if (context != null) {
                String result = null;
                try {
                    Object obj = context.lookup("java:network/env/newbie/testbean");
                    log.info("Found been at : java:network/env/newbie/testbean");
                    com.test.BeanInterface beanInterface = 
                            (com.test.BeanInterface)
                            PortableRemoteObject.narrow(obj,
                            com.test.BeanInterface.class);
                    log.info("Call hello world");
                    result = beanInterface.helloWorld("This is to a bean");
                    System.out.println("Hello world message : " + result);
                    
                    log.info("Call exception");
                    try {
                        beanInterface.exceptionMethod("This is to a bean");
                        throw new Exception("Failed to retrieve an exception");
                    } catch (com.test.TestException ex) {
                        System.out.println("Caught an exception : " + ex.getMessage());
                        ex.printStackTrace(System.out);
                    }
                    
                    log.info("Call onto bean.");
                    com.test.ValueObject vo = beanInterface.getValue();
                    if (!vo.getValue().equals("test")) {
                        log.info("The value was set incorrectly : " + 
                                vo.getValue());
                        throw new Exception("Invalid value");
                    }
                    log.info("Factory object");
                    
                    com.test.FactoryInterface factoryInterface = 
                            beanInterface.connectToObject();
                    log.info("The int value for bob is : " + 
                            factoryInterface.getInt("bob"));
                    log.info("Test the get method");
                    log.info("Retrieve a list of entries from fred : " + 
                            factoryInterface.getList("fredds list").size());
                    log.info("Test the get method");
                    CalledObjects.called = 0;
                    com.test.FactoryInterface factoryInterface2 = 
                            beanInterface.addObject("fred");
                    com.test.FactoryParentInterface parentInter = 
                            beanInterface.getObject("fred");
                    factoryInterface2 = (com.test.FactoryInterface)
                            PortableRemoteObject.narrow(parentInter,
                            com.test.FactoryInterface.class);
                    if (factoryInterface2 == null) {
                        log.error("The norrow to a factory interface failed");
                       return "failed";
                    }
                    if (CalledObjects.called != 0) {
                       log.error("Test failed landed on back end ");
                       return "failed";
                    }
                    parentInter = 
                            beanInterface.getObject("fred2");
                    factoryInterface2 = (com.test.FactoryInterface)
                            PortableRemoteObject.narrow(parentInter,
                            com.test.FactoryInterface.class);
                    if (factoryInterface2 == null) {
                        log.error("The norrow to a factory interface failed");
                        return "failed";
                    }
                    if (CalledObjects.called != 1) {
                        log.error("Test failed call did not land on backed");
                        return "failed";
                    }
                    log.info("The length of int value is : " + 
                            factoryInterface2.getIntArray(
                            new String[] {"test"}).length);
                    
                    beanInterface.removeObject("fred");
                    beanInterface.removeObject("fred2");
                    try {
                        factoryInterface2.getInt("bob");
                        log.error("ERROR The factory object is still valid ");
                        return "failed";
                    } catch (Exception ex) {
                        // ignore
                    }
                    
                    log.info("Call onto the bean : " + result);
                    
                    
                    com.test.BeanInterface beanInterface2 = 
                            (com.test.BeanInterface)
                            context.lookup("java:comp/env/bean/testbean");
                    log.info("[Local Interface]Call onto bean.");
                    result += beanInterface2.helloWorld("This is to a bean");
                    log.info("[Local Interface]Factory object");
                } catch (javax.naming.NameNotFoundException ex) {
                    log.info("Environment not setup for full test : " + 
                            ex.getMessage(),ex);
                } catch (java.lang.ClassCastException ex) {
                    log.info("Class cast problems  : " + 
                            ex.getMessage(),ex);
                } catch (Exception ex) {
                    log.error("Failed to call bean : " + 
                            ex.getMessage());
                }
                
                try {
                    Object obj = context.lookup("java:network/env/newbie/testbean3");
                    log.info("Found been at : java:network/env/newbie/testbean3");
                    com.test3.BeanInterface beanInterface = 
                            (com.test3.BeanInterface)
                            PortableRemoteObject.narrow(obj,
                            com.test3.BeanInterface.class);
                    log.info("Call hello world");
                    result += beanInterface.helloWorld("This is to a bean");
                    System.out.println("Hello world message : " + result);
                    
                    log.info("Call exception");
                    try {
                        beanInterface.exceptionMethod("This is to a bean");
                        throw new Exception("Failed to retrieve an exception");
                    } catch (com.test.TestException ex) {
                        System.out.println("Caught an exception : " + ex.getMessage());
                        ex.printStackTrace(System.out);
                    }
                    
                    log.info("Call onto bean.");
                    com.test3.ValueObject vo = beanInterface.getValue();
                    if (!vo.getValue().equals("test")) {
                        log.info("The value was set incorrectly : " + 
                                vo.getValue());
                        throw new Exception("Invalid value");
                    }
                    log.info("Factory object");
                    
                    com.test3.FactoryInterface factoryInterface = 
                            beanInterface.connectToObject();
                    log.info("The int value for bob is : " + 
                            factoryInterface.getInt("bob"));
                    log.info("Test the get method");
                    log.info("Retrieve a list of entries from fred : " + 
                            factoryInterface.getList("fredds list").size());
                    log.info("Test the get method");
                    CalledObjects.called = 0;
                    com.test3.FactoryInterface factoryInterface2 = 
                            beanInterface.addObject("fred");
                    com.test3.FactoryParentInterface parentInter = 
                            beanInterface.getObject("fred");
                    factoryInterface2 = (com.test3.FactoryInterface)
                            PortableRemoteObject.narrow(parentInter,
                            com.test.FactoryInterface.class);
                    if (factoryInterface2 == null) {
                        log.error("The norrow to a factory interface failed");
                       return "failed";
                    }
                    if (CalledObjects.called != 0) {
                       log.error("Test failed landed on back end ");
                       return "failed";
                    }
                    parentInter = 
                            beanInterface.getObject("fred2");
                    factoryInterface2 = (com.test3.FactoryInterface)
                            PortableRemoteObject.narrow(parentInter,
                            com.test3.FactoryInterface.class);
                    if (factoryInterface2 == null) {
                        log.error("The norrow to a factory interface failed");
                        return "failed";
                    }
                    if (CalledObjects.called != 1) {
                        log.error("Test failed call did not land on backed");
                        return "failed";
                    }
                    log.info("The length of int value is : " + 
                            factoryInterface2.getIntArray(
                            new String[] {"test"}).length);
                    
                    beanInterface.removeObject("fred");
                    beanInterface.removeObject("fred2");
                    try {
                        factoryInterface2.getInt("bob");
                        log.error("ERROR The factory object is still valid ");
                        return "failed";
                    } catch (Exception ex) {
                        // ignore
                    }
                    
                    log.info("Call onto the bean : " + result);
                    
                    
                    com.test3.BeanInterface beanInterface2 = 
                            (com.test3.BeanInterface)
                            context.lookup("java:comp/env/bean/testbean3");
                    log.info("[Local Interface]Call onto bean.");
                    result += beanInterface2.helloWorld("This is to a bean");
                    log.info("[Local Interface]Factory object");
                } catch (javax.naming.NameNotFoundException ex) {
                    log.info("Environment not setup for full test : " + 
                            ex.getMessage(),ex);
                } catch (java.lang.ClassCastException ex) {
                    log.info("Class cast problems  : " + 
                            ex.getMessage(),ex);
                    throw new Exception("Failed to cast");
                } catch (Exception ex) {
                    log.error("Failed to call bean : " + 
                            ex.getMessage());
                }
                
                try {
                    Bean2Interface bean2Interface = 
                            (Bean2Interface)PortableRemoteObject.narrow(
                            context.lookup("testbean2"),
                            Bean2Interface.class);
                    log.info("Call onto bean.");
                    String result2 = bean2Interface.helloWorld("This is to a bean");
                    log.info("Call onto the bean : " + result2);
                    
                    log.info("Test the stub code of the bean 2 interface");
                    TopInterface topInterface = bean2Interface.getTopInter("test");
                    log.info("The name : " + topInterface.getName());
                    MiddleInterface middleInterface= (MiddleInterface)
                            PortableRemoteObject.narrow(topInterface,
                            MiddleInterface.class);
                    log.info("The description : " + middleInterface.getDescription());
                    
                    log.info("Set the value in the value2 object");
                    com.extra.bean.ValueObject2 value = new com.extra.bean.ValueObject2();
                    value.setValue("testing value");
                    bean2Interface.takeValue(value);
                    log.info("Set the value");
                    
                    return result2 + " " + result;
                } catch (javax.naming.NameNotFoundException ex) {
                    log.info("Environment not setup for full test : " + 
                            ex.getMessage(),ex);
                } catch (java.lang.ClassCastException ex) {
                    log.info("Environment not setup for full test : " + 
                            ex.getMessage(),ex);
                }
                return result;
            } else {
                log.error("Context not set");
            }
            
            System.out.println("Message from client : " + msg);
            return "To client";
        } catch (Exception ex) {
            log.error("Test failed : " + ex.getMessage(),ex);
            return "Test failed";
        }
    }
}
