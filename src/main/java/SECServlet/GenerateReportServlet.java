package SECServlet;

import Entity.DiagnosisResult;
import Entity.StringUtil;
import MySQLDBC.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.*;

@WebServlet(name = "GenerateReportServlet", value = "/GenerateReportServlet")
public class GenerateReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Generate Report</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>诊断结果</h1>");

        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement stmt = con.prepareStatement("SELECT image_path, diagnosis FROM Images WHERE user_id=(SELECT id FROM Users WHERE username=?) ORDER BY id DESC");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            List<DiagnosisResult> diagnosisResults = new ArrayList<>();
            while (rs.next()) {
                DiagnosisResult result = new DiagnosisResult();
                result.setImageName(rs.getString("image_path"));
                result.setDiagnosis(rs.getString("diagnosis"));
                diagnosisResults.add(result);
            }

            if (diagnosisResults.isEmpty()) {
                out.println("<p>您的诊断结果还未生成, 请稍后再试.</p>");
                out.println("<a href=\"user_home.jsp\">返回用户界面</a>");
            } else {
                for (DiagnosisResult diagnosisResult : diagnosisResults) {
                    String imageName = diagnosisResult.getImageName();
                    String diagnosis = diagnosisResult.getDiagnosis();

                    String imageName_ = StringUtil.addPercentBeforeBackslash(imageName,"\\","/");

                    out.println("<p><a href=\"ViewImageServlet?image=" + imageName_ + "\">" + imageName + "</a>: " + diagnosis + "</p>");
                }
                out.println("<form action=\"payment.jsp\" method=\"get\">");
                out.println("<input type=\"submit\" value=\"下载PDF\"/>");
                out.println("</form>");
                out.println("<h3>如果点击未响应，说明该图没有诊断结果诊断结果<h3>");
                out.println("<a href=\"user_home.jsp\">返回用户界面</a>");
            }

            session.setAttribute("diagnosisResults", diagnosisResults);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("user_home.jsp?status=error");
        } finally {
            out.println("<script  color=\"255,182,193\" opacity='1' zIndex=\"-1\" count=\"100\" src=\"https://cdn.bootcss.com/canvas-nest.js/2.0.4/canvas-nest.js\" type=\"text/javascript\"></script>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
}