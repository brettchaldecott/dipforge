/*
 * Fri Feb 19 08:13:26 SAST 2010
 * create.groovy
 * @author admin
 */

package ControlPanel.dns.records

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.Resource
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.request.wrapper.RequestWrapper



def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

String domainName = request.getParameter("domain")
String prefix = request.getParameter("prefix")
String ttl = request.getParameter("ttl")
String type = request.getParameter("type")
String suffix = request.getParameter("suffix")

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

Network dnsRecord = session.getType(Network.class, "com.rift.coad.rdf.objmapping.inventory.DNSRecord")

dnsRecord.setId(prefix + "." + domainName)
if (prefix.length() != 0) {
    dnsRecord.setName(prefix + "." + domainName)
} else {
    dnsRecord.setName(domainName)
}
dnsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").setValue(domainName)
dnsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"suffix").setValue(suffix)
dnsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"ttl").setValue(Long.parseLong(ttl))
dnsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"type").setValue(type)


List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Network> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/inventory#NetworkId> ?NetworkId . " +
                    "FILTER ((?IdForDataType = \"com.rift.coad.rdf.objmapping.inventory.DNSDomain\") && " +
                    "(?NetworkId = \"${domainName}\"))}").execute();

boolean found = false
Network network = null
Resource domainResource
for (SPARQLResultRow row : entries) {
    domainResource = row.get(0).getResource()
    Network currentNetwork = row.get(0).cast(Network.class)
    if (currentNetwork.getAssociatedObject() != null) {
        continue
    }
    network = currentNetwork
    found = true;
    break
}

if (!found) {
    request.setAttribute("dns_domain", domainName)
    forward("unknown.gsp", request, response)
    return
}

// persist
Resource dnsResource = domainResource.addProperty("http://www.coadunation.net/schema/rdf/1.0/dns#Record",dnsRecord);


// make the request
RequestWrapper wrapper = new RequestWrapper("create", dnsRecord)
wrapper.makeRequest(session)

// records
List<Network> records = new ArrayList<Network>();
records.add(network.getAttribute(com.rift.coad.rdf.objmapping.inventory.Network.class,"ns"))
records.add(network.getAttribute(com.rift.coad.rdf.objmapping.inventory.Network.class,"nsa"))

// walk the extra resource
List<Resource> recordsResource = domainResource.listProperties("http://www.coadunation.net/schema/rdf/1.0/dns#Record")
for (Resource recordResource : recordsResource) {
    records.add(recordResource.get(com.rift.coad.rdf.objmapping.inventory.Network.class))
}

// setup the result
request.setAttribute("dns_domain", domainName)
request.setAttribute("domain",network)
request.setAttribute("records",records)
forward("view.gsp", request, response)
