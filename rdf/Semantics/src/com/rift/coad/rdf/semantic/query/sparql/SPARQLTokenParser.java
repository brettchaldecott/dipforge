/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  2015 Burntjam
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
 * BasicSPARQLQuery.java
 */

// package path
package com.rift.coad.rdf.semantic.query.sparql;

// private member variables
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Iterator;


/**
 * This object is responsible for parsing the SPARQL statement passed in.
 *
 * @author brett chaldecott
 */
public class SPARQLTokenParser {

    // private member variables
    private String source;
    private Map<String,String> keyValueTable = new HashMap<String,String>();
    private Map<Integer,String> indexValueTable = new HashMap<Integer,String>();

    /**
     * The constructor responsible for parsing the source string.
     *
     * @param source The source string to parse.
     */
    public SPARQLTokenParser(String source) throws SPARQLException {
        this.source = preParseQuery(source);
    }


    /**
     * This method is used to set a string key value pair in the parser.
     *
     * @param key The key to set the value for.
     * @param value The string value to place in the query.
     * @throws com.rift.coad.rdf.semantic.query.sparql.SPARQLException
     */
    public void setKeyValue(String key, String value) throws SPARQLException {
        if (!this.keyValueTable.containsKey(key)) {
            throw new SPARQLException(String.format(
                    "The key [%s] has not been set", key));
        }
        keyValueTable.put(key, value);
    }


    /**
     * This method is responsible for setting the index value in the query.
     *
     * @param index The index to replace.
     * @param value The value to set for the index.
     * @throws com.rift.coad.rdf.semantic.query.sparql.SPARQLException
     */
    public void setIndexValue(int index, String value) throws SPARQLException {
        if (!this.indexValueTable.containsKey(new Integer(index))) {
            throw new SPARQLException(String.format(
                    "The index [%d] is out of range",index));
        }
        indexValueTable.put(new Integer(index), value);
    }


    /**
     * This method is responsible for generating a parsed query.
     *
     * @return The string containing the result.
     */
    public String generateQuery() throws SPARQLException {
        String result = this.source;
        for (Iterator<String> iter = this.keyValueTable.keySet().iterator();
        iter.hasNext();) {
            String key = iter.next();
            String value = this.keyValueTable.get(key);
            if (value == null) {
                throw new SPARQLException(String.format(
                        "The key [%s] has not been set",key));
            }
            result = result.replaceAll("[$]{1}[{]{1}" + key + "[}]{1}",
                    "\"" + value + "\"");
        }

        for (Iterator<Integer> iter = this.indexValueTable.keySet().iterator();
        iter.hasNext();) {
            Integer key = iter.next();
            String value = this.indexValueTable.get(key);
            if (value == null) {
                throw new SPARQLException(String.format(
                        "The index [%d] has not been set",key.intValue()));
            }
            result = result.replaceAll("[$]{1}[{]{1}" + key.intValue() + "[}]{1}",
                    "\"" + value + "\"");
        }

        return result;
    }

    /**
     * This method is called to pre-parse the query and generate the appropriate lookup tables.
     *
     * @param query The string to perform the pre-parsing on.
     */
    private String preParseQuery(String query) throws SPARQLException {
        StringTokenizer token = new StringTokenizer(query,"$");
        StringBuffer result = new StringBuffer();
        // deal with the first token
        result.append(token.nextToken());
        while (token.hasMoreTokens()) {
            String value = token.nextToken();
            if (value.indexOf("{") == 0) {
                String key = value.substring(value.indexOf("{"), value.indexOf("}") + 1);
                if (!key.matches("[{]{1}[A-Z-a-z]{1}[A-Za-z0-9]*[}]{1}")) {
                    throw new SPARQLException(String.format(
                            "Provided with an invalid token [%s]",key));
                }
                key = key.substring(1, key.length() - 1);
                keyValueTable.put(key, null);
                result.append("$").append(value);
            } else {
                Integer index = this.indexValueTable.size();
                result.append(String.format("${%d}",index.intValue())).append(value);
                this.indexValueTable.put(index, null);
            }
        }
        return result.toString();
    }
}
