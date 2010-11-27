/*
 * Mon Feb 22 10:52:39 SAST 2010
 * delete.groovy
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


String account = request.getParameter("account")
String domain = account.split("[@]")[1]


Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#IPService> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/service#IPID> ?IPID . " +
                    "FILTER ((?IdForDataType = \"com.rift.coad.rdf.objmapping.service.MailAccount\") && " + 
                    "(?IPID = \"${account}\"))}").execute();

boolean found = false
IPService mailAccount
Resource mailAccountResource
for (SPARQLResultRow row : entries) {
    mailAccountResource = row.get(0).getResource()
    mailAccount = mailAccountResource.get(IPService.class)
    if (mailAccount.getAssociatedObject() != null) {
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
    // domain not found error
    request.setAttribute("delete-error","Domain Not Found")
    request.setAttribute("mailbox",mailbox)
    request.setAttribute("domain",domain)
    request.setAttribute("password",password)
} else if (!found) {
    // not found error
    request.setAttribute("delete-error","Duplicate Mailbox")
    request.setAttribute("mailbox",mailbox)
    request.setAttribute("domain",domain)
    request.setAttribute("password",password)
} else {
    // deal with account
    domainResource.removeProperty("http://www.coadunation.net/schema/rdf/1.0/mail#Account",mailAccountResource);
    session.remove(mailAccount)
    RequestWrapper wrapper = new RequestWrapper("delete", mailAccount)
    wrapper.makeRequest(session)
}

entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/service#IPService> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.service.MailAccount\")}").execute();
List<String> accounts = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    mailAccount = row.get(0).cast(IPService.class)
    if (mailAccount.getAssociatedObject() != null) {
        continue
    }
    accounts.add(mailAccount.getId());
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