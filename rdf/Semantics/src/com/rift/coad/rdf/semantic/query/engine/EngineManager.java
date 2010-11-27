/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  Rift IT Contracting
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
 * QueryEngine.java
 */


package com.rift.coad.rdf.semantic.query.engine;

import com.hp.hpl.jena.rdf.model.Model;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This object manages the query engines.
 *
 * @author brett chaldecott
 */
public class EngineManager {

    // class singleton
    private static EngineManager singleton = null;

    // private member variables
    private ConcurrentMap<Model,QueryEngine> engines = new ConcurrentHashMap<Model,QueryEngine>();

    /**
     * The default constructor.
     */
    private EngineManager() {
    }


    /**
     * This method returns an instance of the engine manager.
     *
     * @return The reference to the engine manager.
     */
    public static synchronized EngineManager getInstance() {
        if (singleton == null) {
            singleton = new EngineManager();
        }
        return singleton;
    }

    
    /**
     * This method adds a new engine.
     * 
     * @param model The reference to the model.
     * @param engine The engine
     */
    public void addEngine(Model model, QueryEngine engine) {
        this.engines.put(model, engine);
    }
    
    
    /**
     * This method removes the specified engine.
     * @param model The model identifiying the engine
     */
    public void removeEngine(Model model) {
        this.engines.remove(this);
    }


    /**
     * This method retrieves the query engine identified by the model or the
     * default engine.
     *
     * @param model The model
     * @return The resulting query engine.
     */
    public QueryEngine getEngine(Model model) {
        if (engines.containsKey(model)) {
            return engines.get(model);
        }
        return new DefaultEngine(model);
    }
}
