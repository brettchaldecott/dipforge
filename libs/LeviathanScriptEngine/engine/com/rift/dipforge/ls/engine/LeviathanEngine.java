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
 * LeviathanEngine.java
 */
// package path
package com.rift.dipforge.ls.engine;

import com.rift.dipforge.ls.engine.LeviathanConstants.Status;
import com.rift.dipforge.ls.engine.internal.TypeManagerLookup;
import com.rift.dipforge.ls.engine.internal.type.JavaReflectionTypeManager;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The implementation of the leviathan script engine.
 *
 * @author brett chaldecott
 */
public class LeviathanEngine {

    // class singleton
    private static LeviathanEngine engine = null;
    // private member variables
    private Map<String, LeviathanProcessorManager> processors = new 
            ConcurrentHashMap<String, LeviathanProcessorManager>();
    private File storageDirectory;
    private LeviathanConstants.Status status = LeviathanConstants.Status.INIT;

    /**
     * The constructor that sets up the leviathan engine.
     *
     * @param property The property list.
     */
    private LeviathanEngine(LeviathanConfig config) throws EngineException {
        try {
            TypeManagerLookup lookup = TypeManagerLookup.getInstance();
            for (TypeManager manager : config.getTypeManagers()) {
                lookup.registerTypeManager(manager);
            }
            lookup.registerTypeManager(new JavaReflectionTypeManager());
            storageDirectory = new File((String)config.getProperties().get(
                    LeviathanConstants.STORAGE_PATH));
            if (!storageDirectory.isDirectory()) {
                throw new EngineException("Invalid directory path : " + 
                        storageDirectory.getPath());
            }
            for (File file: storageDirectory.listFiles()) {
                LeviathanProcessorManager manager = new LeviathanProcessorManager(file);
                this.processors.put(manager.getProcessor().getGUID(), manager);
            }
            this.status = LeviathanConstants.Status.RUNNING;
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException(
                    "Failed to initialize the leviathan engine : " +
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method returns a reference to a new leviathan engine.
     *
     * @return The reference to the new leviathan engine.
     */
    public synchronized static LeviathanEngine buildEngine(LeviathanConfig config)
            throws EngineException {
        if (engine != null) {
            throw new EngineException("The engine is already created");
        }
        return engine = new LeviathanEngine(config);
    }

    
    /**
     * This method returns the reference to the leviathan engine.
     *
     * @return The reference to the leviathan engine.
     */
    public static LeviathanEngine getInstance() {
        return engine;
    }

    
    /**
     * This method returns a reference to the leviathan processor manager
     *
     * @param source The source
     * @param properties The list of properties
     * @return This method returns the newly initialized processor
     * @throws EngineException
     */
    public LeviathanProcessorManager initProcess(String source, Map properties)
            throws EngineException {
        try {
            LeviathanProcessorManager manager = 
                    new LeviathanProcessorManager(source,properties);
            this.processors.put(manager.getProcessor().getGUID(), manager);
            return manager;
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to init the process : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a list of processors
     * 
     * @return The list of processors
     */
    public List<String> listProcessors() {
        List<String> result = new ArrayList<String>();
        result.addAll(this.processors.keySet());
        return result;
    }
    
    
    /**
     * This method returns a reference to the specified process.
     * @param id The id of the processor.
     * @return The reference to the processor
     * @throws EngineException 
     */
    public LeviathanProcessorManager getProcess(String id) throws EngineException {
       return this.processors.get(id);
    }
    
    
    /**
     * This method is called to remove a process.
     * 
     * @param id The id of the process to remove.
     */
    public void removeProcess(String id) throws EngineException {
        this.processors.remove(id);
    }
    
    
    /**
     * This method returns the status of the leviathan engine.
     * 
     * @return The current status of the engine.
     */
    public synchronized Status getStatus() {
        return status;
    }
    
    
    /**
     * This method sets the status.
     * 
     * @param status The status
     */
    private synchronized void setStatus(Status status) {
        this.status = status;
    }
    
    
    /**
     * This method is called to shut down the leviathan engine.
     */
    public void shutdown() throws EngineException {
        setStatus(Status.SHUTDOWN);
        // empty directory
        for (File file : this.storageDirectory.listFiles()) {
            try {
                file.delete();
            } catch (Exception ex) {
                System.out.println("Failed to delete the item [" + 
                        file.getPath() + "] : " + ex.getMessage());
            }
        }
        List<String> processorIds = listProcessors();
        for (String id: processorIds) {
            LeviathanProcessorManager manager = this.processors.get(id);
            manager.getProcessor().persist(this.storageDirectory);
        }
    }
    
}
