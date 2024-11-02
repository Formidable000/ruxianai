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
    if ("empty_fields".equals(status)) {
        out.println("<p>请填写所有字段。</p>");
    } else if ("username_taken".equals(status)) {
        out.println("<p>用户名已被占用。</p>");
    } else if ("error".equals(status)) {
        out.println("<p>注册时发生错误，请稍后再试。</p>");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h2>用户注册</h2>
<form action="UserRegisterServlet" method="post">
    <label>用户名:</label>
    <input type="text" name="username" required><br>
    <label>密码:</label>
    <input type="password" id="password" name="password" required><br>
    <span id="strength" class="strength"></span><br>
    <input type="submit" value="注册">
</form>
<a href="index.jsp">返回首页</a>

<script>
    const password = document.getElementById('password');
    const strength = document.getElementById('strength');

    password.addEventListener('input', () => {
        const value = password.value;
        let strengthValue = '';
        let strengthClass = '';

        if (value.length < 6) {
            strengthValue = '弱';
            strengthClass = 'weak';
        } else if (value.length < 10) {
            strengthValue = '中等';
            strengthClass = 'medium';
        } else {
            strengthValue = '强';
            strengthClass = 'strong';
        }

        strength.textContent = strengthValue;
        strength.className = 'strength ' + strengthClass;
    });
</script>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>