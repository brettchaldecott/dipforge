/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * TieGeneratorTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment.rmi;

import junit.framework.*;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.ClassUtil;
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.common.ResourceReader;
import com.rift.coad.lib.common.TextFile;
import com.rift.coad.lib.common.JarUtil;
import com.rift.coad.lib.deployment.CoadunationParser;
import com.rift.coad.lib.deployment.BeanInfo;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.thirdparty.ant.JavaC;
import com.rift.coad.lib.thirdparty.ant.RMIC;

/**
 *
 * @author mincemeat
 */
public class TieGeneratorTest extends TestCase {
    
    public TieGeneratorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(TieGeneratorTest.class);
        
        return suite;
    }
    
    
    /**
     * This method will generate a temporary file using the source file.
     *
     * @return The path to the newly created temporary file.
     */
    private File createTmpDir(File source, File tmpDirBase) throws Exception {
        File tmpDir = new File(tmpDirBase,
                RandomGuid.getInstance().getGuid());
        if (tmpDir.mkdirs() == false) {
            throw new Exception("Failed to create the director ["
                    + tmpDir.getAbsolutePath() + "]");
        }
        JarUtil.extract(source,tmpDir);
        return tmpDir;
    }
    
    /**
     * Test of generate method, of class com.rift.coad.lib.deployment.rmi.TieGenerator.
     */
    public void testGenerate() throws Exception {
        System.out.println("generate");
        
        // load the deployment loader
        File tmpDir = createTmpDir(new File(System.getProperty("test.jar")), 
                new File(System.getProperty("test.tmp.dir")));
        
        // parse the coadunation file
        TextFile textFile = new TextFile(new File(tmpDir,
                DeploymentLoader.META_FILE));
        CoadunationParser coadunationParser = new CoadunationParser(
                textFile.getTextDocument());
        
        BeanInfo bean = (BeanInfo)coadunationParser.getDeploymentInfo().
                getBeans().get("com.test.BeanImpl");
        BeanInfo bean2 = (BeanInfo)coadunationParser.getDeploymentInfo().
                getBeans().get("com.test3.BeanImpl");
        File tmpSource = new File(tmpDir,RandomGuid.getInstance().getGuid());
        tmpSource.mkdir();
        TieGenerator instance = new TieGenerator(tmpDir,tmpSource,bean);
        instance.generate();
        TieGenerator instance2 = new TieGenerator(tmpDir,tmpSource,bean2);
        instance2.generate();
        
        // test the compiling of these classes
        File[] classPath = new File[] { tmpDir, 
                new File("./dist/CoadunationLib.jar"),
                new File("../CoadunationBase/dist/CoadunationBase.jar"),
                new File("../CoadInclude/dist/CoadunationInclude.jar"),
                new File("../CoadunationInclude/dist/CoadunationInclude.jar")};
        JavaC javaC = new JavaC(classPath,tmpSource,tmpSource);
        System.out.println("Compiling dir [" + tmpSource.getAbsolutePath() + "]");
        javaC.compileClasses();
        
        RMIC rmic = new RMIC(classPath,tmpSource,"**/*.class",tmpSource);
        System.out.println("Compiling dir [" + tmpSource.getAbsolutePath() + "]");
        rmic.parse();
    }
    
    
}
