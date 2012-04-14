/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * GeneratorRunner.java
 */


// package path
package com.rift.coad.rdf.semantic.jdo.generator;

// imports

import com.rift.coad.rdf.semantic.ontology.DefaultOntologyManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologyManager;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;




/**
 * This object is a command line
 *
 * @author brett chaldecott
 */
public class GeneratorRunner {

    // class static variables
    private static Logger log = Logger.getLogger(GeneratorRunner.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            if (args.length < 3) {
                System.out.println("Invalid Arguments expected: " + args[0] + " <target file> [classe...]");
                System.exit(1);
            }
            List<Class> types = new ArrayList<Class>();
            for (int index = 2; index < args.length; index++) {
                types.add(Class.forName(args[index]));
            }
            OntologyManager manager = DefaultOntologyManagerFactory.init();
            OntologySession session = manager.getSession();
            ClassOntologyGenerator generator = new ClassOntologyGenerator(
                session, types);
            generator.processTypes();
            File targetFile = new File(args[1]);
            FileOutputStream out = new FileOutputStream(targetFile);
            out.write(session.dumpXML().getBytes());
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            log.error("The failed because : " + ex.getMessage(),ex);
            System.exit(1);
        }
    }

}
