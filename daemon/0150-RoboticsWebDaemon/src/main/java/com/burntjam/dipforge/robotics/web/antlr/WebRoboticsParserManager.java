/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr;

import java.io.File;




/**
 * The implementation of the web robotics parser manager.
 * 
 * @author brett chaldecott
 */
public class WebRoboticsParserManager {
    
    public static WebRoboticsParserExecutor createExecutor(String source) throws Exception {
        System.out.println("The create executor is being called");
        return new WebRoboticsParserExecutor(source);
    }

    public static WebRoboticsParserExecutor createExecutor(File path) throws Exception {
        System.out.println("The create executor is being called");
        return new WebRoboticsParserExecutor(path);
    }
    
}
