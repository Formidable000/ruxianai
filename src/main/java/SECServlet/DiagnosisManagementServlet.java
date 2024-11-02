package SECServlet;

import Entity.Diagnosis;
import MySQLDBC.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DiagnosisManagementServlet", value = "/DiagnosisManagementServlet")
public class DiagnosisManagementServlet extends HttpServlet {

    private static final String GET_USER_ID_QUERY = "SELECT id FROM Users WHERE username=?";
    private static final String GET_DIAGNOSIS_QUERY = "SELECT id, image_path, diagnosis FROM Images WHERE user_id=?";
    private static final String DELETE_DIAGNOSIS_QUERY = "DELETE FROM Images WHERE id=?";
    private static final String ADD_DIAGNOSIS_QUERY = "INSERT INTO Images (user_id, image_path, diagnosis) VALUES (?, ?, ?)";
    private static final String UPDATE_DIAGNOSIS_QUERY = "UPDATE Images SET image_path=?, diagnosis=? WHERE id=?";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String action = request.getParameter("action");
        String imageId = request.getParameter("imageId");
        String imagePath = request.getParameter("imagePath");
        String diagnosis = request.getParameter("diagnosis");

        if (username == null || username.isEmpty()) {
            response.sendRedirect("admin_diagnosis_management.jsp?status=empty_username");
            return;
        }

        try (Connection con = DatabaseConnection.initializeDatabase();
             PreparedStatement getUserStmt = con.prepareStatement(GET_USER_ID_QUERY);
             PreparedStatement getDiagnosisStmt = con.prepareStatement(GET_DIAGNOSIS_QUERY);
             PreparedStatement deleteDiagnosisStmt = con.prepareStatement(DELETE_DIAGNOSIS_QUERY);
             PreparedStatement addDiagnosisStmt = con.prepareStatement(ADD_DIAGNOSIS_QUERY);
             PreparedStatement updateDiagnosisStmt = con.prepareStatement(UPDATE_DIAGNOSIS_QUERY)) {

            getUserStmt.setString(1, username);
            ResultSet rs = getUserStmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");

                if ("delete".equals(action) && imageId != null) {
                    deleteDiagnosisStmt.setInt(1, Integer.parseInt(imageId));
                    deleteDiagnosisStmt.executeUpdate();
                    response.sendRedirect("admin_diagnosis_management.jsp?status=delete_success&username=" + username);
                } else if ("add".equals(action) && imagePath != null && diagnosis != null) {
                    addDiagnosisStmt.setInt(1, userId);
                    addDiagnosisStmt.setString(2, imagePath);
                    addDiagnosisStmt.setString(3, diagnosis);
                    addDiagnosisStmt.executeUpdate();
                    response.sendRedirect("admin_diagnosis_management.jsp?status=add_success&username=" + username);
                } else if ("update".equals(action) && imageId != null && imagePath != null && diagnosis != null) {
                    updateDiagnosisStmt.setString(1, imagePath);
                    updateDiagnosisStmt.setString(2, diagnosis);
                    updateDiagnosisStmt.setInt(3, Integer.parseInt(imageId));
                    updateDiagnosisStmt.executeUpdate();
                    response.sendRedirect("admin_diagnosis_management.jsp?status=update_success&username=" + username);
                } else {
                    getDiagnosisStmt.setInt(1, userId);
                    rs = getDiagnosisStmt.executeQuery();
                    List<Diagnosis> diagnosisList = new ArrayList<>();
                    while (rs.next()) {
                        diagnosisList.add(new Diagnosis(rs.getInt("id"), rs.getString("image_path"), rs.getString("diagnosis")));
                    }
                    request.setAttribute("diagnosisList", diagnosisList);
                    request.getRequestDispatcher("admin_diagnosis_management.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("admin_diagnosis_management.jsp?status=user_not_found");
            }

        } catch (SQLException | ClassNotFoundException e) {
            response.sendRedirect("admin_diagnosis_management.jsp?status=error");
            e.printStackTrace();
        }
    }
}
