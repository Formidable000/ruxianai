<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/4
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String status = request.getParameter("status");
    if ("no_images_found".equals(status)) {
        out.println("<p>没有找到该图片的诊断结果.</p>");
    } else if ("error".equals(status)) {
        out.println("<p>在获取诊断结果时发生了一个错误. 请稍后再试.</p>");
    }
    if ("feedback_success".equals(status)) {
        out.println("<p>反馈提交和下载PDF成功.</p>");
    } else if ("feedback_error".equals(status)) {
        out.println("<p>在提交反馈或者下载时出现了一个错误.请稍后再试.</p>");
    }
    if ("OnlyFeedback_success".equals(status)) {
        out.println("<p>反馈提交成功.</p>");
    } else if ("OnlyFeedback_error".equals(status)) {
        out.println("<p>在提交反馈或者下载时出现了一个错误.请稍后再试.</p>");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>User Home</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="triangle"></div>
<div class="triangle" style="top: 200px; left: 100px;"></div>
<div class="triangle" style="top: 400px; left: 200px;"></div>
<h2>欢迎, <%= session.getAttribute("username") %></h2>
<a href="upload_image.jsp">上传图片</a><br>
<a href="GenerateReportServlet">查询诊断结果</a><br>
<a href="only_feedback.jsp">提交反馈</a><br>
<a href="logout.jsp">登出</a>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>
