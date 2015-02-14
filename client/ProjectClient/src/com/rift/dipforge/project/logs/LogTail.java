/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.dipforge.project.logs;

/**
 *
 * @author brett chaldecott
 */
public class LogTail implements java.io.Serializable {
    
    private boolean gap;
    private int endLine;
    private String lines;

    /**
     * Default constructor
     */
    public LogTail() {
    }

    
    /**
     * The constructor of the log tail
     * 
     * @param gap TRUE if there was a gap in the log
     * @param endLine The end log line
     * @param lines The string containing the log lines.
     */
    public LogTail(boolean gap, int endLine, String lines) {
        this.gap = gap;
        this.endLine = endLine;
        this.lines = lines;
    }

    /**
     * The getter for the gap flag.
     * 
     * @return The getter for the gap
     */
    public boolean isGap() {
        return gap;
    }

    
    /**
     * The setter for the gap.
     * 
     * @param gap TRUE if there is a gap, FALSE if there is not.
     */
    public void setGap(boolean gap) {
        this.gap = gap;
    }

    
    /**
     * The getter for the end line count.
     * 
     * @return The end line count.
     */
    public int getEndLine() {
        return endLine;
    }

    
    /**
     * The setter for the end line count.
     * 
     * @param endLine The end line count.
     */
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    
    /**
     * The getter for the lines.
     * 
     * @return The lines.
     */
    public String getLines() {
        return lines;
    }

    
    /**
     * The setter for the lines.
     * 
     * @param lines The lines.
     */
    public void setLines(String lines) {
        this.lines = lines;
    }
    
}
