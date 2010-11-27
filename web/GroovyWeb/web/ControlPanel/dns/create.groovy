/*
 * Wed Feb 17 07:43:42 SAST 2010
 * create.groovy
 * @author admin
 */

package ControlPanel.dns

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.request.wrapper.RequestWrapper



def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

String domainName = request.getParameter("domain") + "."
String contact = request.getParameter("contact")
String ttl = request.getParameter("ttl")
String serial = request.getParameter("serial")
String ipaddress = request.getParameter("ipaddress")

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

Network service = session.getType(Network.class, "com.rift.coad.rdf.objmapping.inventory.DNSDomain")

service.setId(domainName)
service.setName(domainName)
service.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").setValue(domainName)
service.getAttribute(com.rift.coad.rdf.objmapping.base.Email.class,"contact").setValue(contact)
service.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"ttl").setValue(Long.parseLong(ttl))
service.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"serial").setValue(Long.parseLong(serial))
Network nsRecord = service.getAttribute(com.rift.coad.rdf.objmapping.inventory.Network.class,"ns")
nsRecord.setId(domainName)
nsRecord.setName(domainName)
nsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"suffix").setValue("ns1."+domainName)
nsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"ttl").setValue(Long.parseLong(ttl))
nsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"type").setValue("NS")
Network nsARecord = service.getAttribute(com.rift.coad.rdf.objmapping.inventory.Network.class,"nsa")
nsARecord.setId("ns1." + domainName)
nsARecord.setName("ns1." + domainName)
nsARecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"suffix").setValue(ipaddress)
nsARecord.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"ttl").setValue(Long.parseLong(ttl))
nsARecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"type").setValue("A")


session.persist(service)

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Network> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.inventory.DNSDomain\")}").execute();
List<String> domains = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    Network network = row.get(0).cast(Network.class)
    if (network.getAssociatedObject() != null) {
        continue
    }
    domains.add(network.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue());
}

RequestWrapper wrapper = new RequestWrapper("create", service)
wrapper.makeRequest(session)


request.setAttribute("dns_domains", domains)



forward("view.gsp", request, response)
