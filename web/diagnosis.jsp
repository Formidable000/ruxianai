<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/4
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Diagnosis</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h2>诊断界面</h2>
<form action="DiagnosisServlet" method="post">
    <label>用户名:</label><br>
    <input type="text" name="username" required><br>
    <label>数据库中的Image ID:</label><br>
    <input type="text" name="imageId" required><br>
    <label>诊断结果:</label><br>
    <textarea name="diagnosis" required></textarea><br>
    <input type="submit" value="提交诊断结果">
</form>
<a href="admin_home.jsp">返回主页</a><br>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>