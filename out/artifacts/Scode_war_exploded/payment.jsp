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
    <title>支付页面</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="triangle"></div>
<div class="triangle" style="top: 200px; left: 100px;"></div>
<div class="triangle" style="top: 400px; left: 200px;"></div>
<h2>完成支付</h2>
<form action="PaymentServlet" method="post">
    <label>支付金额：</label><br>
    <input type="text" name="payment"><br>
    <input type="submit" value="提交支付">
</form>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>