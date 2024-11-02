package SECServlet;

import MySQLDBC.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OnlyFeedbackServlet", value = "/OnlyFeedbackServlet")
public class OnlyFeedbackServlet extends HttpServlet {
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

            // Feedback submitted successfully, redirect to user_home.jsp
            response.sendRedirect("user_home.jsp?status=OnlyFeedback_success");
        } catch (Exception e) {
            e.printStackTrace();
            // On exception, redirect to user_home.jsp with error status
            response.sendRedirect("user_home.jsp?status=OnlyFeedback_error");
        }
    }
}
