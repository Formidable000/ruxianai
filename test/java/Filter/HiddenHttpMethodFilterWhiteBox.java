package Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

class HiddenHttpMethodFilterWhiteBox {

    private HiddenHttpMethodFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        filter = new HiddenHttpMethodFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void testDoFilter_NoMethodParameter() throws IOException, ServletException {
        when(request.getParameter("_method")).thenReturn(null);
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilter_PUTMethod() throws IOException, ServletException {
        when(request.getParameter("_method")).thenReturn("PUT");
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(any(MethodRequestWrapper.class), eq(response));
    }

    @Test
    void testDoFilter_DELETEMethod() throws IOException, ServletException {
        when(request.getParameter("_method")).thenReturn("DELETE");
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(any(MethodRequestWrapper.class), eq(response));
    }

    @Test
    void testDoFilter_OtherMethod() throws IOException, ServletException {
        when(request.getParameter("_method")).thenReturn("POST");
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilter_IgnoredCase() throws IOException, ServletException {
        when(request.getParameter("_method")).thenReturn("put");
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(any(MethodRequestWrapper.class), eq(response));
    }
}