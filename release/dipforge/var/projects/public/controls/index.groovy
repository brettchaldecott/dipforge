import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;

def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}


//forward("index.gsp", request, response)

println "<html><body>fred said</body></html>"
