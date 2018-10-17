/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base;

import com.burntjam.dipforge.robotics.web.antlr.base.engine.WebRoboticsSeleniumEngine;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author ubuntu
 */
public class WebRoboticsProgram {

    private WebRoboticsProgramContext context = new WebRoboticsProgramContext("root");
    
    public WebRoboticsProgram() {
        
    }
    
    public void addVariable(String name) {
        context.createVariableExpression(name);
    }
    
    public void createExpression(String name) {
        context.createExpression(name);
    }
    
    public void addFunctionCallToExpression(String name) {
        context.addFunctionCallToExpression(name);
    }
    
    public void setFunctionArgument(WebRoboticsProgramStatement statement) {
        context.setFunctionArgument(statement);
    }
    
    public void completeStatementDeclaration() {
        context.completeStatementDeclaration();
    }
    
    public void addChild(String name) {
        context.addChild(name);
    }
    
    public void releaseChild() {
        context.releaseChild();
    }
    
    
    public void execute() {
        WebRoboticsSeleniumEngine.initContext();
        try {
            context.execute();
        } finally {
            WebRoboticsSeleniumEngine.finContext();
        }
    }
    
}
