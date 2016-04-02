<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Hello World</title>
</head>
<body>
   <h2><c:out value="${message}" /></h2>
   <h3><%=(String)request.getAttribute("message")%></h3>
   <h3><%=request.getParameter("message")%></h3>
</body>
</html>