<html>
<head>
<title>Hello World</title>
</head>
<body>
   <h2>Ho</h2>
   <h3><%=(String)request.getAttribute("message")%></h3>
   <h3><%=request.getParameter("message")%></h3>
</body>
</html>