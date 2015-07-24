/*
 * 0200-ProjectManager: The project manager implentation.
 * Copyright (C) 2015 2015 Burntjam
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
 * LogTailerManager.java
 */



package com.rift.dipforge.project.logs;

import java.io.File;
import java.util.ArrayList;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The log tailer manager
 * 
 * @author brett chaldecott
 */
public class LogTailerManager {
    
    // class constants
    private final static int BUFFER_SIZE = 256;
    private final static String LINE_SEPERATOR = System.getProperty("line.separator");
    
    /**
     * The log tailer
     */
    public class LogTailerListener implements TailerListener {

        private int currentBegin = 0;
        private List<String> lines = new ArrayList<>();
        
        /**
         * The default constructor
         */
        public LogTailerListener() {
        }
        
        
        /**
         * The init method.
         * @param tailer 
         */
        @Override
        public void init(Tailer tailer) {
            
        }

        /**
         * If the file is not found.
         */
        @Override
        public void fileNotFound() {
            // nothing to be done
        }

        @Override
        public void fileRotated() {
            // nothing to be done
        }

        @Override
        public synchronized void handle(String line) {
            if (this.lines.size() >= BUFFER_SIZE) {
                this.lines.remove(0);
                this.currentBegin++;
            }
            this.lines.add(line);
        }

        @Override
        public void handle(Exception excptn) {
            
        }
        
        
        /**
         * This method returns the tail logger.
         * 
         * @param pos The position in the log file to read from
         * @return The read segment from the file.
         */
        public synchronized LogTail tailLog(int pos) {
            boolean gap = false;
            int currentPos = pos;
            if (pos <= currentBegin) {
                currentPos = 0;
            } else {
                currentPos = pos - this.currentBegin;
            }
            StringBuffer buffer = new StringBuffer();
            for (;currentPos < this.lines.size();currentPos++) {
                buffer.append(this.lines.get(currentPos)).
                        append(LINE_SEPERATOR);
            }
            return new LogTail(gap,this.currentBegin + this.lines.size(), 
                buffer.toString());
        }
        
    }
    
    
    /**
     * This object is the log tailer
     */
    public class LogTailer {
        
        // class constants
        private static final long DELAY = 1000;
        
        
        private File file;
        private Tailer tailer;
        private LogTailerListener listener;
        
        /**
         * The constructor for the log tailer.
         * 
         * @param fileName the name of the file
         */
        public LogTailer(String fileName) {
            file = new File(directory,fileName);
            
            listener = new LogTailerListener();
            tailer = new Tailer(file, listener, DELAY);
            Thread thread = new Thread(tailer);
            thread.setDaemon(true); // optional
            thread.start();
        }
        
        
        /**
         * This method is called to tail the log file.
         * 
         * @param pos The position to tail from.
         * @return The log tail object
         */
        public LogTail tailLog(int pos) {
            return this.listener.tailLog(pos);
        }
        
        
        /**
         * This method called to stop the tailer thread.
         */
        public void stop() {
            tailer.stop();
        }
    }
    
    
    // the singleton method
    private static LogTailerManager singleton = null;
    
    // private member variables.
    private boolean shutdown = false;
    private Map<String,LogTailer> tailers = new HashMap<>();
    private File directory;
    
    
    /**
     * The private constructor for the log tailer manager.
     * 
     * @param directory The directory manager path.
     */
    private LogTailerManager(File directory) {
        this.directory = directory;
    }
    
    
    /**
     * This method returns the reference to the log trailer.
     * 
     * @param directory The path to the directory
     * @return The reference to the log trailer.
     */
    public synchronized static LogTailerManager createInstance(File directory) {
        return singleton = new LogTailerManager(directory);
    }
    
    
    /**
     * This method returns the log tailer manager singleton.
     * 
     * @return This method returns a reference to the log tailer object.
     */
    public synchronized static LogTailerManager getInstance() {
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the log trailer
     * 
     * @param name The name of the file.
     * @return The reference to the file.
     */
    public synchronized LogTailer getLogTailer(String name) {
        if (shutdown) {
            return null;
        }
        LogTailer result = tailers.get(name);
        if (result == null) {
            tailers.put(name, result = new LogTailer(name));
        }
        return result;
    }
    
    
    /**
     * This method is called to shut down the tailers
     */
    public synchronized void shutdown () {
        
    }
}
