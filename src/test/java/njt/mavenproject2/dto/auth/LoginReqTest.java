package njt.mavenproject2.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoginReqTest {

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