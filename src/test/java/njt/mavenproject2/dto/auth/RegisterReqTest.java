package njt.mavenproject2.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegisterReqTest {

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