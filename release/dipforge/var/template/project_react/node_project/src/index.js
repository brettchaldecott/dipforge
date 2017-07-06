var Router = ReactRouter.Router;
var Route = ReactRouter.Route;
var browserHistory = ReactRouter.browserHistory;


console.log("We will now render")
ReactDOM.render(
  <Router history={browserHistory}>
    <Route path="**/index.html" component={Hello}/>
  </Router>,
  document.getElementById('root')
);
console.log("After render")
