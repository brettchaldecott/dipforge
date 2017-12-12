/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep.engine;

import java.io.File;

/**
 *
 * @author brett chaldecott
 */
public class Constants {
    public final static String PROJECTS_DIR = String.format("%svar%sprojects%s",File.separator,File.separator,File.separator);
    public final static String DIPFORGE_PROJECT = "dipforge";
    public final static String INCLUDE_PATH = "packages";
    public final static String SERVICES_PATH = "services";
}
