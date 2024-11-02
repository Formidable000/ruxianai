package SECServlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Mockito.*;

public class DiagnosisServletTest {
    private DiagnosisServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Connection con;
    private PreparedStatement stmt;

    @BeforeEach
    public void setUp() throws Exception {
        servlet = new DiagnosisServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        con = mock(Connection.class);
        stmt = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
    }

    @Test
    public void testValidDiagnosisUpdate() throws Exception {
        when(request.getParameter("username")).thenReturn("123456");
        when(request.getParameter("imageId")).thenReturn("1");
        when(request.getParameter("diagnosis")).thenReturn("Healthy");
        servlet.doPost(request, response);
        verify(response).sendRedirect("admin_home.jsp?status=success");
    }

    @Test
    public void testInvalidImageId() throws Exception {
        when(request.getParameter("username")).thenReturn("123456");
        when(request.getParameter("imageId")).thenReturn("999");
        when(request.getParameter("diagnosis")).thenReturn("Healthy");
        servlet.doPost(request, response);
        verify(response).sendRedirect("admin_home.jsp?status=invalid_image_id_or_username");
    }

    @Test
    public void testEmptyDiagnosis() throws Exception {
        when(request.getParameter("username")).thenReturn("1");
        when(request.getParameter("imageId")).thenReturn("1");
        when(request.getParameter("diagnosis")).thenReturn("");
        servlet.doPost(request, response);
        verify(response).sendRedirect("admin_home.jsp?status=invalid_image_id_or_username");
    }
}