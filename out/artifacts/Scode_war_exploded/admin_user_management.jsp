<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/5
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, Entity.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin User Management</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h2>用户管理</h2>

<form action="UserManagementServlet" method="post">
    <h3>添加用户</h3>
    用户名: <input type="text" name="username" required><br>
    密码: <input type="password" name="password" required><br>
    身份: <input type="text" name="role" required><br>
    <input type="submit" value="添加用户">
</form>

<h3 class="center-text">所有用户</h3>
<table class="centered-table">
    <tr>
        <th>用户名</th>
        <th>身份</th>
        <th>功能</th>
    </tr>
    <%
        List<User> userList = (List<User>) request.getAttribute("userList");
        if (userList != null && !userList.isEmpty()) {
            for (User user : userList) {
    %>
    <tr>
        <td><%= user.getUsername() %></td>
        <td><%= user.getRole() %></td>
        <td>
            <form action="UserManagementServlet" method="post" style="display:inline;">
                <input type="hidden" name="_method" value="PUT">
                <input type="hidden" name="username" value="<%= user.getUsername() %>">
                用户密码: <input type="password" name="password" required>
                用户身份: <input type="text" name="role" required value="<%= user.getRole() %>">
                <input type="submit" value="更新">
            </form>
            <form action="UserManagementServlet" method="post" style="display:inline;">
                <input type="hidden" name="_method" value="DELETE">
                <input type="hidden" name="username" value="<%= user.getUsername() %>">
                <input type="submit" value="删除">
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3">没有找到用户.</td>
    </tr>
    <%
        }
    %>
</table>

<a href="admin_home.jsp">返回管理控制台</a>

<%
    String status = request.getParameter("status");
    if ("success".equals(status)) {
        out.println("<p>Operation succeeded.</p>");
    } else if ("error".equals(status)) {
        out.println("<p>An error occurred while performing the operation.</p>");
    }
%>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>