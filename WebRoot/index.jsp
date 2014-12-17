<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML">
<html>
<head>
<title>My JSP 'index.jsp' starting page</title>
</head>

<body>
	<form action="<%=path%>/user/reg" method="post">
		<table>
			<tr>
				<td>用户名</td>
				<td><input type="text" name="username" />
				<td>密码</td>
				<td><input type="password" name="password" /></td>
				<td colspan="2"><input type="submit" value="注册" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
