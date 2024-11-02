package SECServlet;

import Entity.Feedback;
import MySQLDBC.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FeedbackManagementServlet", value = "/FeedbackManagementServlet")
public class FeedbackManagementServlet extends HttpServlet {

    private static final String FETCH_FEEDBACK_QUERY = "SELECT id, username, feedback FROM Feedback";
    private static final String SEARCH_FEEDBACK_QUERY = "SELECT id, username, feedback FROM Feedback WHERE username LIKE ?";
    private static final String UPDATE_FEEDBACK_QUERY = "UPDATE Feedback SET feedback=? WHERE id=?";
    private static final String DELETE_FEEDBACK_QUERY = "DELETE FROM Feedback WHERE id=?";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        try (Connection con = DatabaseConnection.initializeDatabase();
             PreparedStatement stmt = con.prepareStatement(FETCH_FEEDBACK_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setUsername(rs.getString("username"));
                feedback.setFeedback(rs.getString("feedback"));
                feedbackList.add(feedback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("feedbackList", feedbackList);
        request.getRequestDispatcher("admin_feedback_management.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String feedbackId = request.getParameter("feedbackId");
        String feedback = request.getParameter("feedback");
        String searchQuery = request.getParameter("searchQuery");

        if ("search".equals(action) && searchQuery != null) {
            ArrayList<Feedback> feedbackList = new ArrayList<>();
            try (Connection con = DatabaseConnection.initializeDatabase();
                 PreparedStatement stmt = con.prepareStatement(SEARCH_FEEDBACK_QUERY)) {

                stmt.setString(1, "%" + searchQuery + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Feedback fb = new Feedback();
                        fb.setId(rs.getInt("id"));
                        fb.setUsername(rs.getString("username"));
                        fb.setFeedback(rs.getString("feedback"));
                        feedbackList.add(fb);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("feedbackList", feedbackList);
            request.getRequestDispatcher("admin_feedback_management.jsp").forward(request, response);
            return;
        }

        try (Connection con = DatabaseConnection.initializeDatabase()) {
            if ("update".equals(action) && feedbackId != null) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_FEEDBACK_QUERY)) {
                    stmt.setString(1, feedback);
                    stmt.setInt(2, Integer.parseInt(feedbackId));
                    stmt.executeUpdate();
                    response.sendRedirect("FeedbackManagementServlet?status=update_success");
                }
            } else if ("delete".equals(action) && feedbackId != null) {
                try (PreparedStatement stmt = con.prepareStatement(DELETE_FEEDBACK_QUERY)) {
                    stmt.setInt(1, Integer.parseInt(feedbackId));
                    stmt.executeUpdate();
                    response.sendRedirect("FeedbackManagementServlet?status=delete_success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("FeedbackManagementServlet?status=error");
        }
    }
}