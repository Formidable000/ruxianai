package SECServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "DownloadServlet", value = "/DownloadServlet")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String DOWNLOAD_FILE_PATH = request.getParameter("FILE_PATH");

        // 检查文件是否存在
        File file = new File(DOWNLOAD_FILE_PATH);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "文件未找到");
            return;
        }

        // 设置响应头，指定要下载的文件名
        String fileName = file.getName();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // 读取文件并写入响应输出流
        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        // 本次操作不需重定向到 feedback.jsp
    }
}