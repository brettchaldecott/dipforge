/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base.statement;

import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgramContext;
import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgramStatement;

/**
 *
 * @author ubuntu
 */
public class WebRoboticsVariableStatement extends WebRoboticsExpressionStatement {
    
    public WebRoboticsProgramStatement value;
    
    public WebRoboticsVariableStatement(String variableName, WebRoboticsProgramContext context) {
        super(variableName,context);
    }
    
    public void setValue(WebRoboticsProgramStatement value) {
        this.value = value;
    }

    @Override
    public void completeDeclartion() {
        if (value instanceof WebRoboticsExpressionStatement && !castToExpession().isDeclared()) {
            castToExpession().completeDeclartion();
            return;
        } else if (value != null) {
            super.completeDeclartion();
            return;
        }
    }

    @Override
    public boolean isDeclared() {
        if (value instanceof WebRoboticsExpressionStatement && !castToExpession().isDeclared()) {
            return false;
        } else if (value != null) {
            return super.isDeclared();
        }
        return false;
    }

    @Override
    public void setArgument(WebRoboticsProgramStatement argument) {
        if (value instanceof WebRoboticsExpressionStatement) {
            castToExpession().setArgument(argument);
        } else {
            this.value = argument;
        }
    }

    @Override
    public void createExpression(String name) {
        if (value instanceof WebRoboticsExpressionStatement) {
            castToExpession().createExpression(name);
        }
    }

    @Override
    public void addFuntionCall(String name) {
        if (value instanceof WebRoboticsExpressionStatement) {
            castToExpession().addFuntionCall(name);
        }
    }
    
    
    public WebRoboticsProgramStatement getValue() {
        return this.value;
    }
    
    public void execute() {
        if (this.value != null) {
            System.out.println("Execute the variable assignement : " + this.getVariableName());
            this.context.getVariable(this.getVariableName()).setValue(
                    this.value.getValue());
            System.out.println("After assigning value to variable [" + this.getVariableName() + "] :"
                + this.context.getVariable(this.getVariableName()).getValue());
        }
    }
    
    private WebRoboticsExpressionStatement castToExpession() {
        return (WebRoboticsExpressionStatement)this.value;
    }
}
