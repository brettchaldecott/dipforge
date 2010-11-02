<html>
  <head><title>A Simple GSP</title></head>
  <body>
    <b><% println "hello gsp" %></b>
    <p>
    <% def wrd = "Groovy"
       wrd.each{ letter ->
     %>
     <h1> <%= letter %> <br/>
     <%} %>
    </p>
  </body>
</html>
