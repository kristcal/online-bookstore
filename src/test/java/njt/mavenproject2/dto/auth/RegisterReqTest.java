package njt.mavenproject2.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti DTO klase {@link RegisterReq}.
 *
 * Testira kreiranje objekta i pristup vrednostima record komponenti.
 *
 * @author Korisnik
 */
class RegisterReqTest {

	/**
	 * Proverava kreiranje RegisterReq objekta i pristup njegovim atributima.
	 */
    @Test
    void testRegisterReq() {

        RegisterReq req = new RegisterReq(
                "Pera",
                "Peric",
                "test@test.com",
                "123456"
        );

        assertEquals("Pera", req.ime());
        assertEquals("Peric", req.prezime());
        assertEquals("test@test.com", req.email());
        assertEquals("123456", req.password());
    }
}