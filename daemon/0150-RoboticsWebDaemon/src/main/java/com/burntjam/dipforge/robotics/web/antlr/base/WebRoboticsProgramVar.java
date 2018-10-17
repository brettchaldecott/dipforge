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
public class WebRoboticsProgramVar {
    private String name;
    private Object value;
    
    public WebRoboticsProgramVar(String name) {
        this.name = name;
    }
    
    public WebRoboticsProgramVar(String name,
            Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    
    public String toString() {
        return value.toString();
    }
}
