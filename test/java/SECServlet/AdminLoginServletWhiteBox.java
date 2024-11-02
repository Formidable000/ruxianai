package SECServlet;


import MySQLDBC.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AdminLoginServletWhiteBox {

    private AdminLoginServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new AdminLoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    void testDoPost_Success() throws Exception {
        // 模拟请求参数
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getSession()).thenReturn(session);

        // 模拟数据库连接和查询
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockRs.next()).thenReturn(true);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);

        // 使用 MockedStatic 替换实际的数据库连接
        try (MockedStatic<DatabaseConnection> mockedStatic = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::initializeDatabase).thenReturn(mockConn);

            // 执行测试方法
            servlet.doPost(request, response);

            // 验证行为
            verify(session).setAttribute("username", "admin");
            verify(response).sendRedirect("admin_home.jsp");
        }
    }

    @Test
    void testDoPost_Failure() throws Exception {
        // 模拟请求参数
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("admin_login.jsp")).thenReturn(dispatcher);

        // 模拟数据库连接和查询
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockRs.next()).thenReturn(false);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);

        // 使用 MockedStatic 替换实际的数据库连接
        try (MockedStatic<DatabaseConnection> mockedStatic = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::initializeDatabase).thenReturn(mockConn);

            // 执行测试方法
            servlet.doPost(request, response);

            // 验证行为
            verify(request).setAttribute("errorMessage", "Invalid username password");
            verify(dispatcher).forward(request, response);
        }
    }

    @Test
    void testDoPost_DatabaseError() throws Exception {
        // 模拟请求参数
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("admin_login.jsp")).thenReturn(dispatcher);

        // 模拟数据库连接异常
        try (MockedStatic<DatabaseConnection> mockedStatic = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::initializeDatabase).thenThrow(new RuntimeException("Database connection error"));

            // 执行测试方法
            servlet.doPost(request, response);

            // 验证行为
            verify(request).setAttribute("errorMessage", "An error occurred. Please try again.");
            verify(dispatcher).forward(request, response);
        }
    }

    @Test
    void testDoPost_EmptyUsernameAndPassword() throws Exception {
        // 模拟请求参数为空
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("admin_login.jsp")).thenReturn(dispatcher);

        // 模拟数据库连接和查询
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockRs.next()).thenReturn(false);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);

        // 使用 MockedStatic 替换实际的数据库连接
        try (MockedStatic<DatabaseConnection> mockedStatic = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::initializeDatabase).thenReturn(mockConn);

            // 执行测试方法
            servlet.doPost(request, response);

            // 验证行为
            verify(request).setAttribute("errorMessage", "Invalid username password");
            verify(dispatcher).forward(request, response);
        }
    }
}