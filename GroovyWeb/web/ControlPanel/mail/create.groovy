/*
 * Wed Feb 10 12:40:30 SAST 2010
 * create.groovy
 * @author admin
 */

package ControlPanel.mail

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

String domainName = request.getParameter("domain")

Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

Network service = session.getType(Network.class, "com.rift.coad.rdf.objmapping.inventory.MailDomain")

service.setId(domainName)
service.setName(domainName)
service.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").setValue(domainName)

session.persist(service)

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Network> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.inventory.MailDomain\")}").execute();
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


request.setAttribute("mail_domains", domains)
request.setAttribute("domain", domainName)



forward("view.gsp", request, response)




