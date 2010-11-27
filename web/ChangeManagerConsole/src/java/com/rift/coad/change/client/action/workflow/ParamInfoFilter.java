/*
 * ChangeControlManager: The manager for the change events.
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
 * ParamInfoFilter.java
 */

package com.rift.coad.change.client.action.workflow;

import java.util.ArrayList;
import java.util.List;

/**
 * The filter object.
 * 
 * @author brett chaldecott
 */
public class ParamInfoFilter {
    
    /**
     * This method filters the flow parameters based on the type information.
     *
     * @param flowParameters The list of flow parameters.
     * @param type The type of flow parameters.
     * @return The returns list.
     */
    public static List<ParamInfo> filterParameters(List<ParamInfo> flowParameters, String type) {
        List<ParamInfo> filteredParameters = new ArrayList<ParamInfo>();
        for (ParamInfo param : flowParameters) {
            if (param.getType().equals(type)) {
                filteredParameters.add(param);
            }
        }
        return filteredParameters;
    }


    /**
     * This method converts the parameters to an array.
     *
     * @param parameters The parameters to perform the conversion on.
     * @return The list of parameters.
     */
    public static String[] convertParameterToArray(List<ParamInfo> parameters) {
        String[] result = new String[parameters.size()];
        for (int index = 0; index < parameters.size(); index++) {
            result[index] = parameters.get(index).getName();
        }
        return result;
    }


    /**
     * This method finds the parameter identified by the param name.
     *
     * @param parameters The list of parameters.
     * @param param The name of the parameter.
     * @return NULL or the reference to the identified parameter.
     */
    public static ParamInfo getParameter(List<ParamInfo> parameters, String param) {
        for (ParamInfo paramInfo : parameters) {
            if (paramInfo.getName().equals(param)) {
                return paramInfo;
            }
        }
        return null;
    }
}
