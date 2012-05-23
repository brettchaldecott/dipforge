
import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.request.RequestBrokerDaemon

def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}

def daemon = ConnectionManager.getInstance().getConnection(
    		RequestBrokerDaemon.class,"request/RequestBrokerDaemon")
def requests = daemon.listRequests()

//forward("index.gsp", request, response)

println "<html><body>${requests}</body></html>"
