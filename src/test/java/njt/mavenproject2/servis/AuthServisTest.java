package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.repository.impl.KorisnikRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AuthServisTest {

    private KorisnikRepository repo;
    private AuthServis servis;

    @BeforeEach
    void setUp() {
        repo = mock(KorisnikRepository.class);
        servis = new AuthServis(repo);
    }

    @Test
    void testLoginUspehPlainText() throws Exception {

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("test@test.com");
        korisnik.setLozinka("123456");

        when(repo.findByEmail("test@test.com"))
                .thenReturn(korisnik);

        Korisnik rezultat =
                servis.login("test@test.com", "123456");

        assertNotNull(rezultat);
        assertEquals("test@test.com", rezultat.getEmail());

        verify(repo).findByEmail("test@test.com");
    }

    @Test
    void testLoginKorisnikNePostoji() {

        when(repo.findByEmail("nepostoji@test.com"))
                .thenReturn(null);

        Exception e = assertThrows(
                Exception.class,
                () -> servis.login(
                        "nepostoji@test.com",
                        "123456"));

        assertEquals(
                "Nalog sa ovim emailom ne postoji.",
                e.getMessage());

        verify(repo).findByEmail("nepostoji@test.com");
    }

    @Test
    void testLoginPogresnaLozinka() {

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("test@test.com");
        korisnik.setLozinka("123456");

        when(repo.findByEmail("test@test.com"))
                .thenReturn(korisnik);

        Exception e = assertThrows(
                Exception.class,
                () -> servis.login(
                        "test@test.com",
                        "pogresna"));

        assertEquals(
                "Pogrešna lozinka.",
                e.getMessage());
    }

    @Test
    void testLoginHashovanaLozinka() throws Exception {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String hash = encoder.encode("tajna123");

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("test@test.com");
        korisnik.setLozinka(hash);

        when(repo.findByEmail("test@test.com"))
                .thenReturn(korisnik);

        Korisnik rezultat =
                servis.login(
                        "test@test.com",
                        "tajna123");

        assertNotNull(rezultat);
        assertEquals("test@test.com", rezultat.getEmail());

        verify(repo).findByEmail("test@test.com");
    }
}