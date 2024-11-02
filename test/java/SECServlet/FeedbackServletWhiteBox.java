package SECServlet;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class FeedbackServletWhiteBox {
    private FeedbackServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Connection con;
    private PreparedStatement stmt;

    @BeforeEach
    public void setUp() throws Exception {
        servlet = new FeedbackServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        con = mock(Connection.class);
        stmt = mock(PreparedStatement.class);

        when(request.getSession()).thenReturn(session);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
    }
    @Test
    public void testUserNotLoggedIn() throws Exception {
        when(session.getAttribute("username")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendRedirect("user_home.jsp?status=feedback_error");
    }

    @Test
    public void testDatabaseOperationFailure() throws Exception {
        when(session.getAttribute("username")).thenReturn("testuser");
        when(request.getParameter("feedback")).thenReturn("This is a feedback");
        doThrow(new SQLException("Database error")).when(stmt).executeUpdate();

        servlet.doPost(request, response);

        verify(response).sendRedirect("user_home.jsp?status=feedback_success");
    }

    @Test
    public void testFeedbackIsNull() throws Exception {
        when(session.getAttribute("username")).thenReturn("testuser");
        when(request.getParameter("feedback")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendRedirect("user_home.jsp?status=feedback_error");
    }

}