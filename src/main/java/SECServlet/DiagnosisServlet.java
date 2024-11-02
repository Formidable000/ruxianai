package SECServlet;

import MySQLDBC.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DiagnosisServlet", value = "/DiagnosisServlet")
public class DiagnosisServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String imageId = request.getParameter("imageId");
        String diagnosis = request.getParameter("diagnosis");

        try {
            Connection con = DatabaseConnection.initializeDatabase();
            String query = "UPDATE Images SET diagnosis=? WHERE id=? AND user_id=(SELECT id FROM Users WHERE username=?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, diagnosis);
            stmt.setInt(2, Integer.parseInt(imageId));
            stmt.setString(3, username);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // 保存诊断成功后跳转回 admin_home.jsp
                response.sendRedirect("admin_home.jsp?status=success");
            } else {
                // 如果 imageId 无效，也可以跳转回 admin_home.jsp 并传递错误信息
                response.sendRedirect("admin_home.jsp?status=invalid_image_id_or_username");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 遇到异常时也可以跳转回 admin_home.jsp，并传递错误状态
            response.sendRedirect("admin_home.jsp?status=error");
        }
    }
}