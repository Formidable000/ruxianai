package SECServlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class FeedbackServletTest {

    @Test
    public void testDoPost() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PrintWriter writer = Mockito.mock(PrintWriter.class);

        Mockito.when(request.getParameter("feedback")).thenReturn("Great service!");
        Mockito.when(response.getWriter()).thenReturn(writer);

        FeedbackServlet servlet = new FeedbackServlet();
        servlet.doPost(request, response);

        Mockito.verify(response).sendRedirect(Mockito.anyString());
    }
}