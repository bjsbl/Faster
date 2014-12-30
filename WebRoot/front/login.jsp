<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML">
<html>
<head>
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<title>Fast_Login</title>
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
	<div class="container">
		<form action="<%=path %>/login" method="post">
			userName:<input type="text" name="userName" /><br/>
			password<input type="password" name="password" /><br/>
			<input type="submit" />&nbsp;&nbsp;<input type="reset" />
		</form>
		<%=request.getAttribute("error_code") %>
	</div>
	<script type="text/javascript" src="<%=path%>/bootstrap/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
