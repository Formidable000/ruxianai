<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/4
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>
<form action="AdminLoginServlet" method="post">
    <label>管理员用户名:</label>
    <input type="text" name="username" ><br>
    <label>密码:</label>
    <input type="password" name="password" required><br>
    <input type="submit" value="登录">
</form>
<a href="logout.jsp">返回主页</a><br>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>