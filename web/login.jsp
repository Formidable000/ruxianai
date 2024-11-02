<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/4
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String status = request.getParameter("status");
    if ("registration_success".equals(status)) {
        out.println("<p>注册成功, 请登录.</p>");
    }
    if ("error".equals(status)) {
        out.println("<p>登录时发生错误，请稍后再试。</p>");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>User Login</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h2>用户登录</h2>
<form action="UserLoginServlet" method="post">
    <label>用户名:</label>
    <input type="text" name="username" required><br>
    <label>密码:</label>
    <input type="password" name="password" required><br>
    <input type="submit" value="登录">
</form>
<a href="index.jsp">返回首页</a><br>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>
