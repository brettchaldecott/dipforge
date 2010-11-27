/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.crm.result;

/**
 * The meta data about a column in the result set.
 * 
 * @author brett chaldecott
 */
public class CRMColumnMetaData implements java.io.Serializable {
    // private member variables
    private String name;
    private String type;

    /**
     * The default constructor.
     */
    public CRMColumnMetaData() {
    }
    
    
    /**
     * This constructor sets the name and type values.
     * @param name The name of the crm entry.
     * @param type The type of the crm entry.
     */
    public CRMColumnMetaData(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    
    /**
     * This method returns the name of the crm column.
     * 
     * @return This method returns the name of the column.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method sets the name of the column.
     * 
     * @param name The name of the column
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * This method sets the type of the column.
     * 
     * @return The string containing the type information of the column.
     */
    public String getType() {
        return type;
    }
    
    
    /**
     * This method sets the type of the column.
     * 
     * @param type The type of entry.
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "|name = " + name + ", type = " + type + "|" ;
    }
    
    
    
}
