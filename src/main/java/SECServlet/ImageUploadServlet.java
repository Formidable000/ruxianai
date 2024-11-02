package SECServlet;

import MySQLDBC.DatabaseConnection;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet(name = "ImageUploadServlet", value = "/ImageUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ImageUploadServlet extends HttpServlet {

    private static final String SAVE_DIR = "G:\\Scode\\getimage";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = (String) request.getSession().getAttribute("username");

        File fileSaveDir = new File(SAVE_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String fileName = null;
        for (Part part : request.getParts()) {
            fileName = extractFileName(part);
            part.write(SAVE_DIR + File.separator + fileName);
        }

        String filePath = SAVE_DIR + File.separator + fileName;

        try {
            Connection con = DatabaseConnection.initializeDatabase();

            // Get user id
            PreparedStatement userStmt = con.prepareStatement("SELECT id FROM Users WHERE username=?");
            userStmt.setString(1, username);
            var rs = userStmt.executeQuery();
            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt("id");
            }

            if (userId != -1) {
                String query = "INSERT INTO Images (user_id, image_path) VALUES (?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, userId);
                stmt.setString(2, filePath);
                stmt.executeUpdate();
                response.sendRedirect("GenerateReportServlet");
            } else {
                request.setAttribute("errorMessage", "用户未找到。");
                request.getRequestDispatcher("upload_image.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "图像上传失败，若多次失败请联系管理员。");
            request.getRequestDispatcher("upload_image.jsp").forward(request, response);
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}