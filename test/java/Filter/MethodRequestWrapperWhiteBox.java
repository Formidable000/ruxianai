package Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MethodRequestWrapperWhiteBox {

    private HttpServletRequest originalRequest;

    @BeforeEach
    public void setUp() {
        originalRequest = mock(HttpServletRequest.class);
    }

    @Test
    public void testGetMethodReturnOverriddenMethod() {
        when(originalRequest.getMethod()).thenReturn("GET");

        MethodRequestWrapper requestWrapper = new MethodRequestWrapper(originalRequest, "POST");

        assertEquals("POST", requestWrapper.getMethod());
    }

    @Test
    public void testGetMethodReturnOriginalMethodWhenNotOverridden() {
        when(originalRequest.getMethod()).thenReturn("GET");

        MethodRequestWrapper requestWrapper = new MethodRequestWrapper(originalRequest, "GET");

        assertEquals("GET", requestWrapper.getMethod());
    }

    @Test
    public void testOriginalRequestMethodNotCalledWhenOverridden() {
        MethodRequestWrapper requestWrapper = new MethodRequestWrapper(originalRequest, "DELETE");

        assertEquals("DELETE", requestWrapper.getMethod());
        verify(originalRequest, never()).getMethod();
    }

    @Test
    public void testOriginalRequestMethodOtherFeatures() {
        when(originalRequest.getHeader("User-Agent")).thenReturn("JUnit Test");
        when(originalRequest.getParameter("param")).thenReturn("value");

        MethodRequestWrapper requestWrapper = new MethodRequestWrapper(originalRequest, "POST");

        assertEquals("JUnit Test", requestWrapper.getHeader("User-Agent"));
        assertEquals("value", requestWrapper.getParameter("param"));
    }
}