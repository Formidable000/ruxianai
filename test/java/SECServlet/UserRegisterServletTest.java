package SECServlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Mockito.*;

public class UserRegisterServletTest {
    private UserRegisterServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Connection con;
    private PreparedStatement checkStmt;
    private PreparedStatement stmt;

    @BeforeEach
    public void setUp() throws Exception {
        servlet = new UserRegisterServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        con = mock(Connection.class);
        checkStmt = mock(PreparedStatement.class);
        stmt = mock(PreparedStatement.class);

        when(con.prepareStatement(anyString())).thenReturn(checkStmt, stmt);
    }

    @Test
    public void testValidRegistration() throws Exception {
        when(request.getParameter("username")).thenReturn("111");
        when(request.getParameter("password")).thenReturn("password");
        when(checkStmt.executeQuery()).thenReturn(mock(ResultSet.class));
        servlet.doPost(request, response);
        verify(response).sendRedirect("login.jsp?status=registration_success");
    }

    @Test
    public void testUsernameTaken() throws Exception {
        when(request.getParameter("username")).thenReturn("existing_user");
        when(request.getParameter("password")).thenReturn("password");
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true);
        when(checkStmt.executeQuery()).thenReturn(rs);
        servlet.doPost(request, response);
        verify(response).sendRedirect("register.jsp?status=username_taken");
    }
}