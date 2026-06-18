package njt.mavenproject2.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti DTO klase {@link LoginReq}.
 *
 * Testira kreiranje objekta i pristup vrednostima record komponenti.
 *
 * @author Korisnik
 */
class LoginReqTest {

	/**
	 * Proverava kreiranje LoginReq objekta i pristup njegovim atributima.
	 */
    @Test
    void testLoginReq() {
        LoginReq req = new LoginReq(
                "test@test.com",
                "123456"
        );

        assertEquals("test@test.com", req.email());
        assertEquals("123456", req.password());
    }
}