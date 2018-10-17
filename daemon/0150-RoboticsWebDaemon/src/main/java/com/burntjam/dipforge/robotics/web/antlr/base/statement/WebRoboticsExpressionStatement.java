/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base.statement;

import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgramContext;
import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgramStatement;
import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgramVar;
import com.burntjam.dipforge.robotics.web.antlr.base.engine.WebRoboticsSeleniumEngine;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author ubuntu
 */
public class WebRoboticsExpressionStatement extends WebRoboticsProgramStatement {
    
    private String variableName;
    protected WebRoboticsProgramContext context;
    private String function;
    private WebRoboticsExpressionStatement functionCall;
    private WebRoboticsProgramStatement argument;
    private boolean declared = false;
    
    public WebRoboticsExpressionStatement(String variableName, WebRoboticsProgramContext context) {
        super(Type.EXPRESSION);
        this.variableName = variableName;
        this.context = context;
    }
    
    public WebRoboticsExpressionStatement(String variableName, WebRoboticsProgramContext context, String function) {
        super(Type.EXPRESSION);
        this.variableName = variableName;
        this.context = context;
        this.function = function;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
    
    @Override
    public Object getValue() {
        if (this.context != null) {
            return getValue(this.context.getVariable(this.variableName));
        } else {
            return getValue(null);
        }
    }
    
    public Object getValue(Object value) {
        System.out.println ("Call on variable: " + this.variableName);
        Object result;
        if (this.function != null) {
            System.out.println ("Call on function: " + this.function);
            Object argument = null;
            if (this.argument != null) {
                argument = this.argument.getValue();
                System.out.print("Call onto selenium : " + argument);
            }
            
            if (this.function.equals("open")) {
                value = WebRoboticsSeleniumEngine.getContext().openUrl(
                        (String)((WebRoboticsProgramVar)value).getValue());
            } else if (this.function.equals("findById")) {
                value = new WebRoboticsProgramVar(this.function,
                        castDriverValue(value).findElement(By.id(argument.toString())));
            } else if (this.function.equals("findByCss")) {
                System.out.println("Trying to split the string : " + argument.toString());
                System.out.println("Found the following number of elements : " + argument.toString().split(Pattern.quote(".")).length);
                value = new WebRoboticsProgramVar(this.function,
                        castDriverValue(value).findElement(By.cssSelector(argument.toString())));
            } else if (this.function.equals("findByName")) {
                value = new WebRoboticsProgramVar(this.function,
                        castDriverValue(value).findElement(By.name(argument.toString())));
            } else if (this.function.equals("findByXpath")) {
                value = new WebRoboticsProgramVar(this.function,
                        castDriverValue(value).findElement(By.xpath(argument.toString())));
            } else if (this.function.equals("click")) {
                castElementValue(value).click();
                try {
                    //Thread.sleep(1000);
                } catch (Exception ex) {
                    // ignore
                }
                
                System.out.println("The variable name is :" + this.variableName);
                value = this.context.getVariable(this.variableName);
            } else if (this.function.equals("enter")) {
                castElementValue(value).submit();
                try {
                    //Thread.sleep(1000);
                } catch (Exception ex) {
                    // ignore
                }

                System.out.println("The variable name is :" + this.variableName);
                value = this.context.getVariable(this.variableName);
            } else if (this.function.equals("type")) {
                System.out.println("Type the argument : " + argument);
                castElementValue(value).sendKeys((CharSequence)argument);
                System.out.println("The variable name is :" + this.variableName);
                value = this.context.getVariable(this.variableName);
            }
            
        }
        if (this.functionCall != null) {
            return this.functionCall.getValue(value);
        } else {
            return value;
        }
        
    }
    
    public void execute() {
        getValue();
    }
    
    
    public void addFuntionCall(String name) {
        if (functionCall != null) {
            functionCall.addFuntionCall(name);
            return;
        }
        if (this.argument != null && this.argument instanceof WebRoboticsExpressionStatement && !((WebRoboticsExpressionStatement)this.argument).declared) {
            ((WebRoboticsExpressionStatement)this.argument).addFuntionCall(name);
            return;
        }
        functionCall = new WebRoboticsExpressionStatement(variableName, context, name);
    }
    
    public void createExpression(String name) {
        if (functionCall != null) {
            functionCall.createExpression(name);
            return;
        }
        if (this.argument != null && this.argument instanceof WebRoboticsExpressionStatement && !((WebRoboticsExpressionStatement)this.argument).declared) {
            ((WebRoboticsExpressionStatement)this.argument).createExpression(name);
            return;
        }
        this.argument = new WebRoboticsExpressionStatement(variableName, context, name);
    }
    
    public void setArgument(WebRoboticsProgramStatement argument) {
        if (functionCall != null) {
            functionCall.setArgument(argument);
            return;
        }
        if (this.argument != null && this.argument instanceof WebRoboticsExpressionStatement && !((WebRoboticsExpressionStatement)this.argument).declared) {
            ((WebRoboticsExpressionStatement)this.argument).setArgument(argument);
            return;
        }
        this.argument = argument;
    }
    
    public boolean isDeclared() {
        return this.declared;
    }
    
    public void completeDeclartion() {
        System.out.println("Complete the declaration : " + this.variableName + "." + this.function);
        if (this.argument != null && this.argument instanceof WebRoboticsExpressionStatement && !((WebRoboticsExpressionStatement)this.argument).declared) {
            System.out.println("Complete the declaration [" + this.variableName + "." + this.function + "] completed arguments");
            ((WebRoboticsExpressionStatement)this.argument).completeDeclartion();
            return;
        }
        if (functionCall != null && !functionCall.declared) {
            System.out.println("Complete the declaration [" + this.variableName + "." + this.function + "] sub functions called");
            functionCall.completeDeclartion();
            if (!functionCall.declared) {
                return;
            }
        }
        System.out.println("Complete the declaration [" + this.variableName + "." + this.function + "] completely declared");
        this.declared = true;
    }
    
    private WebDriver castDriverValue(Object value)  {
        return (WebDriver)((WebRoboticsProgramVar)value).getValue();
    }
    
    private WebElement castElementValue(Object value)  {
        return (WebElement)((WebRoboticsProgramVar)value).getValue();
    }
}
