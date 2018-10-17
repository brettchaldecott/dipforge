/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base;

/**
 *
 * @author ubuntu
 */
public abstract class WebRoboticsProgramStatement  {
    
    public enum Type {
        LITERAL,
        EXPRESSION,
        FUNCTION
    };
    
    private Type type;
    
    public WebRoboticsProgramStatement(Type type) {
        this.type = type;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public abstract Object getValue();
    
    public abstract void execute();
}
