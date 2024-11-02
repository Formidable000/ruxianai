package SECServlet;

import Entity.StringUtil;
import MySQLDBC.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet(name = "FeedbackServlet", value = "/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String feedback = request.getParameter("feedback");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        try {
            Connection con = DatabaseConnection.initializeDatabase();
            String query = "INSERT INTO Feedback (username, feedback) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, feedback);
            stmt.executeUpdate();

            // 获取生成的 PDF 文件路径
            String pdfPath = (String) session.getAttribute("pdfPath");
            if (pdfPath != null) {
                // 提交反馈成功后，重定向到下载对应 PDF 文件
                response.sendRedirect("DownloadServlet?FILE_PATH=" + StringUtil.addPercentBeforeBackslash(pdfPath,"\\","/"));
            } else {
                // 如果 PDF 文件路径未找到，重定向到错误页面或主页
                response.sendRedirect("user_home.jsp?status=pdf_path_not_found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // On exception, redirect to user_home.jsp with error status
            response.sendRedirect("user_home.jsp?status=feedback_error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}