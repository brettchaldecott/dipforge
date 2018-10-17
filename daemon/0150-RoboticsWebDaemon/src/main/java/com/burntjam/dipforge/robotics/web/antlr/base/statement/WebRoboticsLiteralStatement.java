/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base.statement;

import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgramStatement;

/**
 *
 * @author ubuntu
 */
public class WebRoboticsLiteralStatement extends WebRoboticsProgramStatement {
    private Object value;

    public WebRoboticsLiteralStatement(Object value) {
        super(Type.LITERAL);
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return value;
    }
    
    public void execute() {
        
    }
}
