
def forward(page, req, res){
  def dis = req.getRequestDispatcher(page);
  dis.forward(req, res);
}


forward("index.gsp", request, response)
