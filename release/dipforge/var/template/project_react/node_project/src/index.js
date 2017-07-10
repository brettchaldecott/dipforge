import { HashRouter } from 'react-router-dom'

var ReactDOM = require('react-dom')

console.log("We will now render")
ReactDOM.render(
  <HashRouter>
    <Hello/>
  </HashRouter>,
  document.getElementById('root')
);
console.log("After render")
