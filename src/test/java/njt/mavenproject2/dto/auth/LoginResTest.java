package njt.mavenproject2.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoginResTest {

    @Test
    void testLoginRes() {

        LoginRes res = new LoginRes(
                "jwt-token",
                1L,
                "test@test.com",
                "Pera",
                "Peric",
                "ADMIN"
        );

        assertEquals("jwt-token", res.token());
        assertEquals(1L, res.id());
        assertEquals("test@test.com", res.email());
        assertEquals("Pera", res.ime());
        assertEquals("Peric", res.prezime());
        assertEquals("ADMIN", res.uloga());
    }
}