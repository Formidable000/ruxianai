package SECServlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class UserLoginServletWhiteBox {
    private UserLoginServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    @BeforeEach
    public void setUp() throws Exception {
        servlet = new UserLoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        con = mock(Connection.class);
        stmt = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        when(request.getSession()).thenReturn(session);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
    }
    @Test
    public void testSuccessfulLogin() throws Exception {
        when(request.getParameter("username")).thenReturn("123456");
        when(request.getParameter("password")).thenReturn("123456");
        when(rs.next()).thenReturn(true);

        servlet.doPost(request, response);

        verify(request.getSession()).setAttribute("username", "123456");
        verify(response).sendRedirect("user_home.jsp");
    }
    @Test
    public void testLoginFailure() throws Exception {
        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        when(rs.next()).thenReturn(false);

        servlet.doPost(request, response);

        verify(response).sendRedirect("login.jsp?status=error");
    }
    @Test
    public void testDatabaseOperationFailure() throws Exception {
        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("password")).thenReturn("password123");
        when(stmt.executeQuery()).thenThrow(new SQLException("Database error"));

        servlet.doPost(request, response);

        verify(response).sendRedirect("login.jsp?status=error");
    }
    @Test
    public void testNullUsernameOrPassword() throws Exception {
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendRedirect("login.jsp?status=error");
    }
}