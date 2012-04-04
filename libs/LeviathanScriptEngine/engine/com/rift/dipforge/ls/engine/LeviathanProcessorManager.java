/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  Rift IT Contracting
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * LeviathanProcessorManager.java
 */
// package path
package com.rift.dipforge.ls.engine;

import com.rift.dip.leviathan.LeviathanLexer;
import com.rift.dip.leviathan.LeviathanParser;
import com.rift.dipforge.ls.engine.internal.TypeManagerLookup;
import com.rift.dipforge.ls.parser.obj.LsAnnotation;
import com.rift.dipforge.ls.parser.obj.Workflow;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

/**
 * The implementation of the leviathan processor manager. Responsible for
 * managing an individual process.
 *
 * @author brett chaldecott
 */
public class LeviathanProcessorManager {

    // private member variables
    private LeviathanProcessor processor;

    /**
     * The constructor for
     *
     * @param path
     * @throws EngineException
     */
    protected LeviathanProcessorManager(File path) throws EngineException {
        processor = new LeviathanProcessor(path);
    }

    
    /**
     * This constructor initializes the leviathan processor file the content.
     *
     * @param content the content.
     * @throws EngineException
     */
    protected LeviathanProcessorManager(String content, Map parameters)
            throws EngineException {
        try {
            LeviathanLexer lex = new LeviathanLexer(
                    new ANTLRStringStream(content));
            CommonTokenStream tokens = new CommonTokenStream(lex);
            LeviathanParser parser = new LeviathanParser(tokens);
            Workflow flow = parser.workflow();
            
            List<LsAnnotation> annotations = flow.getAnnotations();
            Map envParameters = new HashMap();
            for (LsAnnotation annotation: annotations) {
                TypeManager manager = 
                        TypeManagerLookup.getInstance().getManager(annotation);
                manager.processAnnotation(flow, annotation, parameters, envParameters);
            }
            
            processor = new LeviathanProcessor(flow,envParameters);
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException(
                    "The reference to the leviathan processor manager : "
                    + ex.getMessage());
        }
    }

    
    /**
     * The method that is called to retrieve the processor.
     * 
     * @return The reference to the processor
     */
    public LeviathanProcessor getProcessor() {
        return processor;
    }
    
    
    
}
