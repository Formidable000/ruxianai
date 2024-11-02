package SECServlet;

import Entity.StringUtil;
import MySQLDBC.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "ViewImageServlet", value = "/ViewImageServlet")
public class ViewImageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getParameter("image");
        String imageName_ = StringUtil.addPercentBeforeBackslash(imageName,"/","\\");
        String imageName_output = StringUtil.PathChanged(imageName,"getimage");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement stmt = con.prepareStatement("SELECT diagnosis FROM Images WHERE image_path=?");
            stmt.setString(1, imageName_);
            ResultSet rs = stmt.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Image Details</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">");
            out.println("</head>");
            out.println("<body>");

            if (rs.next()) {
                String diagnosis = rs.getString("diagnosis");
                out.println("<h1>图片: " + imageName_ + "</h1>");
                out.println("<h3>诊断结果: " + diagnosis + "</h3>");
                out.println("<img src='" + imageName_output + "' alt='Image'/>"); // Assuming images are stored in 'uploads' directory
                out.println("<br><a href='GenerateReportServlet'>Back</a>");
            } else {
                out.println("<p>No diagnosis found for this image.</p>");
                out.println("<a href='GenerateReportServlet'>Back</a>");
            }
            out.println("<script  color=\"255,182,193\" opacity='1' zIndex=\"-1\" count=\"100\" src=\"https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js\" type=\"text/javascript\"></script>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("GenerateReportServlet?status=error");
        } finally {
            out.close();
        }
    }
}
