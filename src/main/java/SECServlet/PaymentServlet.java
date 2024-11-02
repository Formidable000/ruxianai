package SECServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Entity.DiagnosisResult;
import Entity.PDFGenerator;
import Entity.StringUtil;
import MySQLDBC.DatabaseUtil;
import com.itextpdf.text.DocumentException;

@WebServlet(name = "PaymentServlet", value = "/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String payment = request.getParameter("payment");

        if (payment == null || payment.isEmpty()) {
            response.sendRedirect("GenerateReportServlet?status=payment_error");
            return;
        }

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        List<DiagnosisResult> diagnosisResults = null;
        try {
            diagnosisResults = DatabaseUtil.getDiagnosisResultsByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("GenerateReportServlet?status=database_error");
            return;
        }

        if (diagnosisResults == null || diagnosisResults.isEmpty()) {
            response.sendRedirect("GenerateReportServlet?status=no_result");
            return;
        }

        String pdfPath = null;
        try {
            pdfPath = PDFGenerator.generateDiagnosisReport(diagnosisResults, username);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            response.sendRedirect("GenerateReportServlet?status=error_generating_pdf");
            return;
        }

        // 保存 pdfPath 到 session 以供后续使用
        session.setAttribute("pdfPath", pdfPath);
        response.sendRedirect("feedback.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}