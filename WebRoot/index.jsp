<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML">
<html>
<head>
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<title>Fast_Title</title>
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h3>FindOutLoggerSystem</h3>
				<h4>2014-12-19</h4>
			</div>
			<div class="col-md-12">
				<form action="<%=path%>/user/reg" method="post" role="form">
					<div class="form-group">
						<label for="username">UserName</label> <input type="text" class="form-control" id="username" placeholder="Please input username" />
					</div>
					<div class="form-group">
						<label for="password">Password</label> <input type="password" class="form-control" id="password" placeholder="Please input password" />
					</div>
					<div class="form-group">
						<label class="checkbox" for="remberMe">
							<input type="checkbox" value="" id="remberMe" required>UnChecked
						</label>
						<label class="checkbox" for="remberMe1">
							<input type="checkbox" value=""  data-toggle="checkbox" id="remberMe1" required>UnChecked
						</label>
					</div>
				</form>
			</div>
			
			<div class="col-md-12">
				<button type="submit" class="btn btn-default" >LoginSystem</button>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=path%>/bootstrap/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
