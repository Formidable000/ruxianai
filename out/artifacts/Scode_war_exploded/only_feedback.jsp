<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/17
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Feedback</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h1>给点建议，偶捏该</h1>
<h2>请输入英文!</h2>
<form action="OnlyFeedbackServlet" method="post">
    <label>反馈:</label><br>
    <textarea name="feedback"></textarea><br>
    <h3>点击提交按钮下一步</h3>
    <input type="submit" value="提交反馈">
</form>
<a href="user_home.jsp">返回主页</a><br>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>