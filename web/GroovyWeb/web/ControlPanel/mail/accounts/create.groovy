/*
 * Mon Feb 22 09:03:12 SAST 2010
 * create.groovy
 * @author admin
 */

package ControlPanel.mail.accounts

import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.Resource
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.request.wrapper.RequestWrapper
import com.rift.coad.lib.common.RandomGuid
import com.rift.coad.rdf.objmapping.base.password.ClearTextPassword
import com.rift.coad.rdf.objmapping.service.IPService
import com.rift.coad.rdf.objmapping.inventory.Network


def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}


String mailbox = request.getParameter("mailbox")
String domain = request.getParameter("domain")
String password = request.getParameter("password")


Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

IPService mailAccount = session.getType(IPService.class, "com.rift.coad.rdf.objmapping.service.MailAccount")

mailAccount.setId(mailbox + "@" + domain)
mailAccount.setHostname(domain)
mailAccount.setName(mailbox)
mailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").setValue(domain)
mailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.password.ClearTextPassword,"password").setId(RandomGuid.getInstance().getGuid())
mailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.password.ClearTextPassword,"password").setValue(password)
mailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"quota").setValue(0)

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#IPService> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/service#IPID> ?IPID . " +
                    "FILTER ((?IdForDataType = \"com.rift.coad.rdf.objmapping.service.MailAccount\") && " + 
                    "(?IPID = \"${mailbox}@${domain}\"))}").execute();

boolean found = false
for (SPARQLResultRow row : entries) {
    IPService account = row.get(0).cast(IPService.class)
    if (account.getAssociatedObject() != null) {
        continue
    }
    found = true;
    break
}

// find domain resource
entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/inventory#Network> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/inventory#NetworkId> ?NetworkId . " + 
                    "FILTER ((?IdForDataType = \"com.rift.coad.rdf.objmapping.inventory.MailDomain\") && " +
                    "(?NetworkId = \"${domain}\"))}").execute();
Resource domainResource = null
for (SPARQLResultRow row : entries) {
    domainResource = row.get(0).getResource()
    Network network = domainResource.get(Network.class)
    if (network.getAssociatedObject() != null) {
        continue
    }
    break;
}

if (domainResource == null) {
    // deal with duplicate error
    request.setAttribute("error","Domain Not Found")
    request.setAttribute("mailbox",mailbox)
    request.setAttribute("domain",domain)
    request.setAttribute("password",password)
} else if (found) {
    // deal with duplicate error
    request.setAttribute("error","Duplicate Mailbox")
    request.setAttribute("mailbox",mailbox)
    request.setAttribute("domain",domain)
    request.setAttribute("password",password)
} else {
    // deal with account
    domainResource.addProperty("http://www.coadunation.net/schema/rdf/1.0/mail#Account",mailAccount);
    RequestWrapper wrapper = new RequestWrapper("create", mailAccount)
    wrapper.makeRequest(session)
}


entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#IPService> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.service.MailAccount\")}").execute();
List<String> accounts = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    IPService account = row.get(0).cast(IPService.class)
    if (account.getAssociatedObject() != null) {
        continue
    }
    accounts.add(account.getId());
}

request.setAttribute("mail_account", accounts)

entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
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

request.setAttribute("mail_domains", domains)


forward("view.gsp", request, response)