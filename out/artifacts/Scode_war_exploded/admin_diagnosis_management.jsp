<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/16
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Entity.Diagnosis" %>
<%
    String status = request.getParameter("status");
    String username = request.getParameter("username");

    if ("empty_username".equals(status)) {
        out.println("<p>请填写用户名。</p>");
    } else if ("delete_success".equals(status)) {
        out.println("<p>诊断结果删除成功。</p>");
    } else if ("add_success".equals(status)) {
        out.println("<p>诊断结果添加成功。</p>");
    } else if ("update_success".equals(status)) {
        out.println("<p>诊断结果更新成功。</p>");
    } else if ("user_not_found".equals(status)) {
        out.println("<p>未找到该用户名。</p>");
    } else if ("error".equals(status)) {
        out.println("<p>操作时发生错误，请稍后再试。</p>");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>诊断结果和反馈管理</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="triangle"></div>
<div class="triangle" style="top: 200px; left: 100px;"></div>
<div class="triangle" style="top: 400px; left: 200px;"></div>
<h1>诊断结果和反馈管理</h1>
<form action="DiagnosisManagementServlet" method="post">
    <label>用户名：</label>
    <input type="text" name="username" value="<%= username != null ? username : "" %>" required>
    <input type="submit" value="搜索">
</form>

<%
    List<Diagnosis> diagnosisList = (List<Diagnosis>) request.getAttribute("diagnosisList");
    if (diagnosisList != null && !diagnosisList.isEmpty()) {
%>
<h2>诊断结果列表</h2>
<table class="centered-table">
    <tr>
        <th>图片路径</th>
        <th>诊断</th>
        <th>操作</th>
    </tr>
    <%
        for (Diagnosis diagnosis : diagnosisList) {
    %>
    <tr>
        <form action="DiagnosisManagementServlet" method="post">
        <td><input type="text" name="imagePath" value="<%= diagnosis.getImagePath() %>" required></td>
        <td><input type="text" name="diagnosis" value="<%= diagnosis.getDiagnosis() %>" required></td>
        <td>
            <input type="hidden" name="imageId" value="<%= diagnosis.getId() %>">
            <input type="hidden" name="username" value="<%= username %>">
            <button type="submit" name="action" value="update">更新</button>
            <button type="submit" name="action" value="delete">删除</button>
        </td>
        </form>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>

<h2>添加新的诊断结果</h2>
<form action="DiagnosisManagementServlet" method="post">
    <label>用户名：</label>
    <input type="text" name="username" required><br>
    <label>图片路径：</label>
    <input type="text" name="imagePath" required><br>
    <label>诊断：</label>
    <input type="text" name="diagnosis" required><br>
    <input type="hidden" name="action" value="add">
    <input type="submit" value="添加">
</form>

<a href="admin_home.jsp">返回管理控制台</a>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>
