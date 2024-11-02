<%--
  Created by IntelliJ IDEA.
  User: Formidable
  Date: 2024/6/4
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Image</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script type="text/javascript">
        function validateFileInput() {
            var fileInput = document.getElementsByName('file')[0];
            var filePath = fileInput.value;
            var allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;
            if (!allowedExtensions.exec(filePath)) {
                alert('Please upload file having extensions .jpg/.jpeg/.png only.');
                fileInput.value = '';
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="triangle"></div>
<div class="triangle" style="top: 200px; left: 100px;"></div>
<div class="triangle" style="top: 400px; left: 200px;"></div>
<form action="ImageUploadServlet" method="post" enctype="multipart/form-data" onsubmit="return validateFileInput()">
    <label>选择一个图片:</label>
    <input type="file" name="file" accept=".png, .jpg, .jpeg" required><br>
    <input type="submit" value="上传">
</form>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<p style="color: red;"><%= errorMessage %></p>
<%
    }
%>
<script  color="255,182,193" opacity='1' zIndex="-1" count="100" src="https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js" type="text/javascript"></script>
</body>
</html>
