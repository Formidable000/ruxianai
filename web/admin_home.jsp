<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/4
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String status = request.getParameter("status");
    if ("success".equals(status)) {
        out.println("<p>诊断结果保存成功.</p>");
    } else if ("invalid_image_id".equals(status)) {
        out.println("<p>请输入数据库中的图片ID.</p>");
    } else if ("error".equals(status)) {
        out.println("<p>在保存结果时发生了一个错误.</p>");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>admin Home</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h2>欢迎, <%= session.getAttribute("username") %></h2>
<a href="diagnosis.jsp">开始诊断</a><br>
<a href="admin_feedback_management.jsp">看看用户又有哪些新的反馈吧</a><br>
<a href="admin_user_management.jsp">看看又有哪些新用户吧</a><br>
<a href="admin_diagnosis_management.jsp">看看哪些用户的诊断结果太多了</a><br>
<a href="logout.jsp">该下班了</a><br>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>