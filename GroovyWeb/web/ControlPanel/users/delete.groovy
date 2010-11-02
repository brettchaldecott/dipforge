/*
 * Mon Feb 22 05:26:19 SAST 2010
 * delete.groovy
 * @author admin
 */

package ControlPanel.users


import com.rift.coad.rdf.semantic.Session
import com.rift.coad.rdf.semantic.SPARQLResultRow
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil
import com.rift.coad.web.rdf.RDFConfigure
import java.util.List
import com.rift.coad.rdf.objmapping.person.User
import com.rift.coad.request.wrapper.RequestWrapper
import com.rift.coad.lib.common.RandomGuid
import com.rift.coad.rdf.objmapping.base.password.ClearTextPassword



def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}


String username = request.getParameter("username")


Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/person#User> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/person#Username> ?Username ." +
                    "FILTER ((?IdForDataType = \"com.rift.coad.rdf.objmapping.person.User\") && " + 
                    "(?Username = \"${username}\"))}").execute();

boolean found = false
for (SPARQLResultRow row : entries) {
    User userEntry = row.get(0).cast(User.class)
    if (userEntry.getAssociatedObject() != null) {
        continue
    }
    found = true;
    // deal with user to delete
    session.remove(userEntry)
    RequestWrapper wrapper = new RequestWrapper("delete", userEntry)
    wrapper.makeRequest(session)
}

if (!found) {
    // deal with duplicate error
    request.setAttribute("delete-error","Username not found")
}

entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/person#User> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?IdForDataType ." +
                    "FILTER (?IdForDataType = \"com.rift.coad.rdf.objmapping.person.User\")}").execute();
List<String> users = new ArrayList<String>();
for (SPARQLResultRow row : entries) {
    User userEntry = row.get(0).cast(User.class)
    if (userEntry.getAssociatedObject() != null) {
        continue
    }
    users.add(userEntry.getUsername());
}

request.setAttribute("usernames", users)


forward("view.gsp", request, response)