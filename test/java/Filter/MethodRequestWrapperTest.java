package Filter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodRequestWrapperTest {

    @Test
    public void testGetMethod() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        MethodRequestWrapper wrapper = new MethodRequestWrapper(request, "PUT");
        assertEquals("PUT", wrapper.getMethod());
    }
}