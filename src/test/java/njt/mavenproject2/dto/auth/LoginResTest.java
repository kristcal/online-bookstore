package njt.mavenproject2.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti DTO klase {@link LoginRes}.
 *
 * Testira kreiranje objekta i pristup vrednostima record komponenti.
 *
 * @author Korisnik
 */
class LoginResTest {

	/**
	 * Proverava kreiranje LoginRes objekta i pristup njegovim atributima.
	 */
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