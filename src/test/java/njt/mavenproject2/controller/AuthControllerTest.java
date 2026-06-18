package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import njt.mavenproject2.dto.auth.LoginReq;
import njt.mavenproject2.dto.auth.RegisterReq;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.repository.impl.KorisnikRepository;
import njt.mavenproject2.servis.AuthServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/**
 * Test klasa za proveru funkcionalnosti klase {@link AuthController}.
 *
 * Testira prijavu korisnika, neuspešnu prijavu, registraciju novog korisnika
 * i pokušaj registracije sa email adresom koja je već zauzeta.
 *
 * @author Korisnik
 */
class AuthControllerTest {

    private AuthServis authServis;
    private KorisnikRepository korisnikRepo;
    private AuthController controller;

    /**
     * Inicijalizuje mock objekte i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        authServis = mock(AuthServis.class);
        korisnikRepo = mock(KorisnikRepository.class);

        controller = new AuthController(authServis, korisnikRepo);
    }

    /**
     * Proverava uspešnu prijavu korisnika.
     *
     * @throws Exception ukoliko servis za autentifikaciju baci izuzetak
     */
    @Test
    void testLoginUspeh() throws Exception {

        Korisnik k = new Korisnik();
        k.setId(1L);
        k.setEmail("test@test.com");
        k.setIme("Pera");
        k.setPrezime("Peric");
        k.setUloga("USER");

        when(authServis.login("test@test.com", "123456"))
                .thenReturn(k);

        LoginReq req = new LoginReq(
                "test@test.com",
                "123456"
        );

        ResponseEntity<?> response =
                controller.login(req);

        assertEquals(200, response.getStatusCode().value());

        verify(authServis)
                .login("test@test.com", "123456");
    }

    /**
     * Proverava neuspešnu prijavu korisnika zbog pogrešnih podataka.
     *
     * @throws Exception ukoliko servis za autentifikaciju baci izuzetak
     */
    @Test
    void testLoginNeuspeh() throws Exception {

        when(authServis.login(anyString(), anyString()))
                .thenThrow(new Exception("Pogrešna lozinka."));

        LoginReq req =
                new LoginReq("test@test.com", "pogresna");

        ResponseEntity<?> response =
                controller.login(req);

        assertEquals(401, response.getStatusCode().value());
        assertEquals(
                "Pogrešna lozinka.",
                response.getBody());
    }

    /**
     * Proverava uspešnu registraciju novog korisnika.
     */
    @Test
    void testRegisterUspeh() {

        when(korisnikRepo.findByEmail("test@test.com"))
                .thenReturn(null);

        RegisterReq req = new RegisterReq(
                "Pera",
                "Peric",
                "test@test.com",
                "123456"
        );

        ResponseEntity<?> response =
                controller.register(req);

        assertEquals(201, response.getStatusCode().value());

        verify(korisnikRepo).save(any(Korisnik.class));
    }

    /**
     * Proverava pokušaj registracije sa email adresom koja je već zauzeta.
     */
    @Test
    void testRegisterEmailZauzet() {

        Korisnik postojeci = new Korisnik();

        when(korisnikRepo.findByEmail("test@test.com"))
                .thenReturn(postojeci);

        RegisterReq req = new RegisterReq(
                "Pera",
                "Peric",
                "test@test.com",
                "123456"
        );

        ResponseEntity<?> response =
                controller.register(req);

        assertEquals(409, response.getStatusCode().value());
        assertEquals(
                "Email je zauzet.",
                response.getBody());

        verify(korisnikRepo, never())
                .save(any());
    }
}