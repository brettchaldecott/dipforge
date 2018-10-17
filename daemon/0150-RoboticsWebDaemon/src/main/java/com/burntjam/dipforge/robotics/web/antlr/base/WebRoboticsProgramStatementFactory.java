/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base;

import com.burntjam.dipforge.robotics.web.antlr.base.statement.WebRoboticsExpressionStatement;
import com.burntjam.dipforge.robotics.web.antlr.base.statement.WebRoboticsLiteralStatement;
import com.burntjam.dipforge.robotics.web.antlr.base.statement.WebRoboticsVariableStatement;
import java.math.BigDecimal;

/**
 *
 * @author ubuntu
 */
public class WebRoboticsProgramStatementFactory {
   
    public static WebRoboticsProgramStatement createLiteralStatement(Object value) {
        return new WebRoboticsLiteralStatement(value);
    }
    
    
    public static WebRoboticsProgramStatement createBoolLiteralStatement(String value) {
        return new WebRoboticsLiteralStatement(Boolean.parseBoolean(value));
    }
    
    public static WebRoboticsProgramStatement createStringLiteralStatement(String value) {
        return new WebRoboticsLiteralStatement(value);
    }
    
    public static WebRoboticsProgramStatement createNumericLiteralStatement(String value) {
        return new WebRoboticsLiteralStatement(new BigDecimal(value));
    }
    
    public static WebRoboticsExpressionStatement createExpressionStatement(String value, WebRoboticsProgramContext context) {
        return new WebRoboticsExpressionStatement(value,context);
    }
    
    public static WebRoboticsExpressionStatement createVariableStatement(String value, WebRoboticsProgramContext context) {
        return new WebRoboticsVariableStatement(value,context);
    }
}
