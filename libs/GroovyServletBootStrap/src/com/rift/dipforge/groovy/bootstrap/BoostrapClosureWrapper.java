/*
 * GroovyServletBootStrap: The groovy servlet boot strap object.
 * Copyright (C) 2011  2015 Burntjam
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
 * GroovyEnvironmentManager.java
 */

// package path
package com.rift.dipforge.groovy.bootstrap;

import groovy.lang.Closure;
import groovy.servlet.ServletBinding;
import groovy.servlet.ServletCategory;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.codehaus.groovy.runtime.GroovyCategorySupport;

/**
 * This object wraps the closure.
 *
 * @author brett chaldecott
 */
public class BoostrapClosureWrapper {

    // private member variables
    private GroovyScriptEngine groovyScriptEngine;


    /**
     * The constructor of the closure wrapper.
     *
     * @param groovyScriptEngine The groovy script engine.
     */
    public BoostrapClosureWrapper(GroovyScriptEngine groovyScriptEngine) {
        this.groovyScriptEngine = groovyScriptEngine;
    }


    /**
     * This method executes the servlet binding.
     *
     * @param scriptUri The script uri to call.
     * @param binding The servlet binding.
     * @throws Throwable
     */
    public void run(final String scriptUri, final ServletBinding binding) throws Throwable {
        Closure closure = new Closure(groovyScriptEngine) {

            public Object call() {
                try {
                    return ((GroovyScriptEngine) getDelegate()).run(scriptUri, binding);
                } catch (ResourceException e) {
                    throw new RuntimeException(e);
                } catch (ScriptException e) {
                    throw new RuntimeException(e);
                }
            }

        };
        GroovyCategorySupport.use(ServletCategory.class, closure);
    }

}
