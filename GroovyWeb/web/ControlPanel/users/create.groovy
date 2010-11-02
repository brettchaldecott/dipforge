/*
 * Fri Feb 19 14:39:30 SAST 2010
 * create.groovy
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
String firstname = request.getParameter("firstname")
String surname = request.getParameter("surname")
String contact = request.getParameter("contact")
String password = request.getParameter("password")
String profile = request.getParameter("profile")


Session session = SemanticUtil.getInstance(RDFConfigure.class).getSession();

User user = session.getType(User.class, "com.rift.coad.rdf.objmapping.person.User")

user.setId(username)
user.setFirstNames(firstname)
user.setSurname(surname)
user.setUsername(username)
user.setPassword(new ClearTextPassword(RandomGuid.getInstance().getGuid(),password))
user.getAttribute(com.rift.coad.rdf.objmapping.base.Email.class,"contact").setValue(contact)
user.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"profile").setValue(profile)

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
    
}

if (found) {
    // deal with duplicate error
    request.setAttribute("error","Duplicate Username")
    request.setAttribute("username",username)
    request.setAttribute("firstname",firstname)
    request.setAttribute("surname",surname)
    request.setAttribute("contact",contact)
    request.setAttribute("password",password)
    request.setAttribute("profile",profile)
} else {
    // deal with new user
    session.persist(user)
    RequestWrapper wrapper = new RequestWrapper("create", user)
    wrapper.makeRequest(session)
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