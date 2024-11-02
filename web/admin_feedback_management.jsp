<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/5
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Entity.Feedback" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Feedback Management</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h2>用户反馈管理</h2>
<%
    String status = request.getParameter("status");
    if ("update_success".equals(status)) {
        out.println("<p>反馈结果更新成功.</p>");
    } else if ("delete_success".equals(status)) {
        out.println("<p>反馈结果删除成功.</p>");
    } else if ("error".equals(status)) {
        out.println("<p>出现了一个错误，请稍后再试.</p>");
    }

    ArrayList<Feedback> feedbackList = (ArrayList<Feedback>) request.getAttribute("feedbackList");
%>

<form action="FeedbackManagementServlet" method="post">
    <input type="hidden" name="action" value="search">
    <label for="searchQuery">通过用户名来查询该用户的反馈:</label>
    <input type="text" id="searchQuery" name="searchQuery" >
    <button type="submit">搜索</button>
</form>

<table class="centered-table">
    <tr>
        <th>用户名</th>
        <th>反馈</th>
        <th>功能</th>
    </tr>
    <%
        if (feedbackList != null) {
            for (Feedback feedback : feedbackList) {
    %>
    <tr>
        <form action="FeedbackManagementServlet" method="post">
            <td><%= feedback.getUsername() %></td>
            <td><input type="text" name="feedback" value="<%= feedback.getFeedback() %>" ></td>
            <td>
                <input type="hidden" name="feedbackId" value="<%= feedback.getId() %>">
                <button type="submit" name="action" value="update">更新</button>
                <button type="submit" name="action" value="delete">删除</button>
            </td>
        </form>
    </tr>
    <%
            }
        }
    %>
</table>
<a href="admin_home.jsp">返回管理控制台</a>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>
