<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<h1>Hello Fast Filter</h1>
	<%=request.getAttribute("hello")%>
	<form action="<%=path%>/com/app/main" method="post">
		name:<input type="text" name="name" /><br/>
		order:<input type="text" name="orderIndex" /><br/>
		id:<input type="text" name="id" /><br/>
		<input type="submit" value="login" />
	</form>
</body>
</html>