package Filter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;

public class HiddenHttpMethodFilterTest {

    @Test
    public void testDoFilter() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ServletResponse response = Mockito.mock(ServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);

        Mockito.when(request.getParameter("_method")).thenReturn("DELETE");
        Mockito.when(request.getMethod()).thenReturn("POST");

        HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(Mockito.any(MethodRequestWrapper.class), Mockito.eq(response));
    }
}