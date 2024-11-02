package SECServlet;

import MySQLDBC.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Mockito.*;

public class AdminLoginServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void testDoPost_ValidAdmin() throws Exception {
        // 设置请求参数
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getSession()).thenReturn(session);

        // 模拟数据库连接和查询
        when(DatabaseConnection.initializeDatabase()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        // 创建 PrintWriter 模拟 response.getWriter()
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // 测试执行 doPost 方法
        AdminLoginServlet servlet = new AdminLoginServlet();
        servlet.doPost(request, response);

        // 验证是否进行了重定向
        verify(response).sendRedirect("admin_home.jsp");
    }

    @Test
    public void testDoPost_InvalidAdmin() throws Exception {
        // 设置请求参数
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("wrongpassword");

        // 模拟数据库连接和查询
        when(DatabaseConnection.initializeDatabase()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // 创建 PrintWriter 模拟 response.getWriter()
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // 测试执行 doPost 方法
        AdminLoginServlet servlet = new AdminLoginServlet();
        servlet.doPost(request, response);

        // 验证是否打印了 "Invalid username or password"
        verify(writer).println("Invalid username or password");
    }
}