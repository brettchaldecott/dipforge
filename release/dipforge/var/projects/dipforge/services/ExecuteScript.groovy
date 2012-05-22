/*
 * dipforge: The core libraries.
 * Copyright (C) Wed Dec 07 09:16:38 SAST 2011 owner 
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
 * ExecuteScript.groovy
 * @author brett chaldecott
 */

// imports
import com.dipforge.semantic.RDF_readonly;
import com.rift.coad.change.request.RequestData;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
 
def className = method.getClassName()
def methodName = method.getMethodName()

def params = []
for (int index = 0; index < parameters.size(); index++) {
    def param = parameters.get(index)
    def name = method.getParameters().get(index).getName()
    if (param instanceof RequestData) {
        param = RDF_readonly.getFromXML(param.getData(), param.getDataType() + "/" + param.getId())
    }
    params.add(param)
}

def objectRef = this.class.classLoader.loadClass( className, true, false )?.newInstance()
def result = objectRef."${methodName}"( *params )

if (!(method.getReturnType() == null || 
        XSDDataDictionary.isBasicTypeByURI(method.getReturnType()))) {
    result = new RequestData(result.getId(), result.builder.classDef.getURI().toString(),
                result.toXML(), result.builder.classDef.getLocalName())
}

out = result