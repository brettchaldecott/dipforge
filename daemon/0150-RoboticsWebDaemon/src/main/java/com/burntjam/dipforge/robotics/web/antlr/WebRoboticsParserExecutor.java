/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr;

import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsLexer;
import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsParser;
import java.io.File;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 *
 * @author brett chaldecott
 */
public class WebRoboticsParserExecutor {
    
    private WebRoboticsLexer lexer;
    private WebRoboticsParser parser;
    private WebRoboticsParser.ProgramContext programContext;
    
    protected WebRoboticsParserExecutor(File path) throws Exception {
        System.out.println("Is file : " + path.isFile());
        lexer = new WebRoboticsLexer(CharStreams.fromPath(path.toPath()));
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        parser = new WebRoboticsParser(tokens);
        programContext = parser.program();
        System.out.println("From file The context is : " + programContext.toString());
        
        System.out.println("The web robotics is : " + parser.getWebRoboticsProgram());
    }
    
    protected WebRoboticsParserExecutor(String source) throws Exception {
        lexer = new WebRoboticsLexer(CharStreams.fromString(source));
        
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        parser = new WebRoboticsParser(tokens);
        programContext = parser.program();
        System.out.println("The context is : " + programContext.toString());
        
        System.out.println("The web robotics is : " + parser.getWebRoboticsProgram());
        
    }
    
    
    public void execute() {
        System.out.println("Execute the parser");
        parser.getWebRoboticsProgram().execute();
        System.out.println("Execute the web robotics");
    }
    
}
