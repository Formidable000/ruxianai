package Entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testGetSetUsername() {
        User user = new User();
        user.setUsername("john_doe");
        assertEquals("john_doe", user.getUsername());
    }

    @Test
    public void testGetSetRole() {
        User user = new User();
        user.setRole("admin");
        assertEquals("admin", user.getRole());
    }
}