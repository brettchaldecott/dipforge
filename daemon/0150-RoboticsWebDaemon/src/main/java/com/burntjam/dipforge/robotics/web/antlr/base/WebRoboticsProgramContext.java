/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base;

import com.burntjam.dipforge.robotics.web.antlr.base.statement.WebRoboticsExpressionStatement;
import com.burntjam.dipforge.robotics.web.antlr.base.statement.WebRoboticsVariableStatement;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;





/**
 *
 * @author ubuntu
 */
public class WebRoboticsProgramContext {
    private String name;
    
    private Map<String,WebRoboticsProgramVar> variables = new HashMap<String,WebRoboticsProgramVar>();
    private Map<String,WebRoboticsProgramContext> children = new HashMap<String,WebRoboticsProgramContext>();
    private List<WebRoboticsExpressionStatement> statements = new ArrayList<WebRoboticsExpressionStatement>();
    private WebRoboticsExpressionStatement currentExpression = null;
    private WebRoboticsProgramContext currentChild = null;
    
    
    public WebRoboticsProgramContext(String name) {
        this.name = name;
    }
    
    public WebRoboticsProgramVar getVariable(String name) {
        return variables.get(name);
    }
    
    public void createVariableExpression(String name) {
        if (currentChild != null) {
            currentChild.createVariableExpression(name);
            return;
        }
        if (currentExpression != null) {
            // ignore should not have passed syntax check
        } else {
            if (!variables.containsKey(name)) {
                variables.put(name, new WebRoboticsProgramVar(name));
            }
            this.currentExpression = WebRoboticsProgramStatementFactory.createVariableStatement(name,this);
            statements.add(this.currentExpression);
        }
    }
    
    public void createExpression(String name) {
        if (currentChild != null) {
            currentChild.createExpression(name);
            return;
        }
        if (currentExpression != null && !(currentExpression instanceof WebRoboticsVariableStatement)) {
            this.currentExpression.createExpression(name);
        } else if ((currentExpression != null && (currentExpression instanceof WebRoboticsVariableStatement)))  {
            WebRoboticsVariableStatement variableStatement = (WebRoboticsVariableStatement)currentExpression;
            if (variableStatement.getValue() != null) {
                variableStatement.createExpression(name);
            } else {
                variableStatement.setValue(
                        WebRoboticsProgramStatementFactory.createExpressionStatement(name,this));
            }
            
        } else {
            this.currentExpression = WebRoboticsProgramStatementFactory.createExpressionStatement(name,this);
            statements.add(this.currentExpression);
        }
    }
    
    
    public void addFunctionCallToExpression(String name) {
        if (currentChild != null) {
            currentChild.createExpression(name);
            return;
        }
        if (currentExpression != null) {
            this.currentExpression.addFuntionCall(name);
        } else {
            // return;
        }
    }
    
    public void setFunctionArgument(WebRoboticsProgramStatement argument) {
        if (currentChild != null) {
            currentChild.setFunctionArgument(argument);
            return;
        }
        if (currentExpression != null) {
            this.currentExpression.setArgument(argument);
        } else {
            // return;
        }
    }
    
    public boolean isDeclared() {
        if (this.currentExpression != null) {
            return this.currentExpression.isDeclared();
        }
        return true;
    }
    
    public void completeStatementDeclaration() {
        if (currentChild != null) {
            currentChild.completeStatementDeclaration();
            return;
        }
        if (currentExpression != null) {
            this.currentExpression.completeDeclartion();
            if (this.currentExpression.isDeclared()) {
                this.currentExpression = null;
            }
        }
    }
    
    public void addChild(String name) {
        currentChild = children.put(name, new WebRoboticsProgramContext(name));
    }
    
    private boolean currentChild() {
        return currentChild != null;
    }
    
    public void releaseChild() {
        if (currentChild != null && currentChild.currentChild()) {
            currentChild.releaseChild();
            return;
        }
        currentChild = null;
    }
    
    
    public void execute() {
        for (WebRoboticsExpressionStatement statement : statements) {
            statement.execute();
        }
    }
}
