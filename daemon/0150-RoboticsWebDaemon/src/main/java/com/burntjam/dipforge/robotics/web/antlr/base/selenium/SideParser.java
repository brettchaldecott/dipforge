/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base.selenium;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.json.*;

/**
 *
 * @author ubuntu
 */
public class SideParser {
    
    public class SideCommand {
        private String command;
        private String type;
        private String argument;
        private String value;
        
        public SideCommand(String command, String type, String argument, String value) {
            this.command = command;
            
            char[] stringArray = type.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            this.type = new String(stringArray);
            
            this.argument = argument;
            this.value =  value.length() > 0? "\"" + value + "\"" : value;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
        
        public String getArgument() {
            return argument;
        }

        public void setArgument(String argument) {
            this.argument = argument;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
        
        
        
    }
    
    private String sources;
    private String roboticsScript;
    
    private String url;
    private String name;
    
    private List<SideCommand> commands = new ArrayList<SideCommand>();
    
    public SideParser(String source) {
        JSONObject obj = new JSONObject(source);
        int count = 0;
        for (String key: obj.keySet()) {
            System.out.println("The keys : " + key);
            if (key.equals("name")) {
                name = obj.getString(key);
            } else if (key.equals("url")) {
                url = obj.getString(key);
            } else if (key.equals("tests")) {
                JSONArray testArrays = obj.getJSONArray(key);
                // for the time being only deal with the first test
                for (int testIndex = 0; testIndex < 1; testIndex++) {
                    JSONObject tests = testArrays.getJSONObject(testIndex);
                    JSONArray entries = tests.getJSONArray("commands");
                    for (int index = 0; index < entries.length(); index++) {
                        JSONObject entry = entries.getJSONObject(index);
                        System.out.println(entry.toString());
                        String command = entry.getString("command");
                        String[] target = entry.getString("target").split("=",2);
                        System.out.println("The results are " + target);
                        System.out.println("The results are " + target.length);
                        System.out.println("The results are " + target[0]);
                        String value = entry.getString("value");
                        if (command.equals("open")) {
                            // ignore
                        } else if (command.equals("setWindowSize")) {
                            // ignore
                        } else if ((command.equals("click") || command.equals("enter") ||  command.equals("type")) && !target[1].equals("main")) {
                            this.commands.add(new SideCommand(command,target[0],target[1],value));
                        }
                    }
                }
                
            }
            count++;
        }
    }
    
    
    public String generateSource() {
        StringBuilder builder = new StringBuilder();
        builder.append("\"use strict\";\n");
        builder.append("let url = \"").append(url).append("\";\n");
        builder.append("let page = url.open();\n");
        for (SideCommand sideCommand: commands) {
            builder.append("page.findBy").append(sideCommand.getType()).append("(\"").append(sideCommand.getArgument())
                    .append("\").").append(sideCommand.getCommand()).append("(")
                    .append(sideCommand.getValue()).append(");\n");
        }
        return builder.toString();
        
    }
    
    
}
