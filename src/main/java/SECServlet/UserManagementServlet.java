package SECServlet;

import Entity.User;
import MySQLDBC.DatabaseConnection;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet(name = "UserManagementServlet", value = "/UserManagementServlet")
public class UserManagementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle fetching all users
        List<User> userList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            String query = "SELECT * FROM user.users";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                userList.add(user);
            }
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("admin_user_management.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin_user_management.jsp?status=error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");

        if (method == null || method.isEmpty()) {
            // Handle create user
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            try {
                Connection con = DatabaseConnection.initializeDatabase();
                String query = "INSERT INTO user.users (username, password, role) VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password); // Ideally, hash the password
                stmt.setString(3, role);
                stmt.executeUpdate();
                response.sendRedirect("UserManagementServlet?status=success");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("UserManagementServlet?status=error");
            }
        } else if ("PUT".equalsIgnoreCase(method)) {
            doPut(request, response);
        } else if ("DELETE".equalsIgnoreCase(method)) {
            doDelete(request, response);
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle update user
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            Connection con = DatabaseConnection.initializeDatabase();
            String query = "UPDATE user.users SET password=?, role=? WHERE username=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, password); // Ideally, hash the password
            stmt.setString(2, role);
            stmt.setString(3, username);
            stmt.executeUpdate();
            response.sendRedirect("UserManagementServlet?status=success");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("UserManagementServlet?status=error");
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle delete user
        String username = request.getParameter("username");

        try {
            Connection con = DatabaseConnection.initializeDatabase();
            String query = "DELETE FROM user.users WHERE username=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.executeUpdate();
            response.sendRedirect("UserManagementServlet?status=success");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("UserManagementServlet?status=error");
        }
    }
}
