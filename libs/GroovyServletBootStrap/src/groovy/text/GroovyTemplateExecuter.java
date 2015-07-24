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
 * GroovyTemplateExecuter.java
 */

package groovy.text;

import groovy.lang.Writable;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * The template executer.
 *
 * @author brett chaldecott
 */
public class GroovyTemplateExecuter {
    private Template template;
    private Writer out;

    public GroovyTemplateExecuter(Template template, Writer out) {
        this.template = template;
        this.out = out;
    }

    /**
     * This method exposes the template make method out of this scope
     *
     * @return The results.
     */
    public void make() throws IOException {
        template.make().writeTo(out);
    }

    public void make(Map binding) throws IOException {
        template.make(binding).writeTo(out);
    }
    
}
