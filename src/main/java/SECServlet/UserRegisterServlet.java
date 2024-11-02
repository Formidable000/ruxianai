package SECServlet;

import MySQLDBC.DatabaseConnection;

import java.io.IOException;
import javax.servlet.ServletException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserRegisterServlet", value = "/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {

    private static final String CHECK_USERNAME_QUERY = "SELECT * FROM Users WHERE username=?";
    private static final String INSERT_USER_QUERY = "INSERT INTO Users (username, password, role) VALUES (?, ?, 'user')";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("register.jsp?status=empty_fields");
            return;
        }

        // 在实际应用中对密码进行哈希处理
        // String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection con = DatabaseConnection.initializeDatabase();
             PreparedStatement checkStmt = con.prepareStatement(CHECK_USERNAME_QUERY);
             PreparedStatement stmt = con.prepareStatement(INSERT_USER_QUERY)) {

            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                response.sendRedirect("register.jsp?status=username_taken");
            } else {
                stmt.setString(1, username);
                // 保存哈希后的密码在实际应用中
                stmt.setString(2, password);
                stmt.executeUpdate();
                response.sendRedirect("login.jsp?status=registration_success");
            }

        } catch (SQLException | ClassNotFoundException e) {
            // 应用日志记录到实际的日志系统
            response.sendRedirect("register.jsp?status=error");
            e.printStackTrace();  // 实际应用中替换为日志记录
        }
    }
}