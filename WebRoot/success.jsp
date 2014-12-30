<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML">
<html>
<head>
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<title>Fast_Success</title>
</head>

<body>
	<h1>Login_Success</h1>
	<h2><a href="<%=path%>/path">PathAction</a></h2>
	<h2><a href="<%=path%>/hello">HelloAction</a></h2>
	<h2><a href="<%=path%>/logout">LogoutAction</a></h2>
</body>
</html>
