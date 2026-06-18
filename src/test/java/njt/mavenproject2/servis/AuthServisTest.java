package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.repository.impl.KorisnikRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Test klasa za proveru funkcionalnosti klase {@link AuthServis}.
 *
 * Testira različite scenarije prijave korisnika,
 * uključujući uspešnu prijavu, pogrešne kredencijale,
 * nepostojeći nalog i rad sa hashovanim lozinkama.
 *
 * @author Korisnik
 */
class AuthServisTest {

    /**
     * Mock repozitorijum korisnika.
     */
    private KorisnikRepository repo;

    /**
     * Servis za autentifikaciju koji se testira.
     */
    private AuthServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(KorisnikRepository.class);
        servis = new AuthServis(repo);
    }

    /**
     * Proverava uspešnu prijavu korisnika sa običnom lozinkom.
     *
     * @throws Exception ukoliko dođe do greške tokom prijave
     */
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

    /**
     * Proverava ponašanje sistema kada korisnik ne postoji.
     */
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

    /**
     * Proverava ponašanje sistema kada je uneta pogrešna lozinka.
     */
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

    /**
     * Proverava uspešnu prijavu korisnika sa hashovanom lozinkom.
     *
     * @throws Exception ukoliko dođe do greške tokom prijave
     */
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

    /**
     * Proverava ponašanje sistema kada je uneta pogrešna
     * hashovana lozinka.
     */
    @Test
    void testLoginPogresnaHashovanaLozinka() {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String hash = encoder.encode("ispravnaLozinka");

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("test@test.com");
        korisnik.setLozinka(hash);

        when(repo.findByEmail("test@test.com"))
                .thenReturn(korisnik);

        Exception e = assertThrows(
                Exception.class,
                () -> servis.login(
                        "test@test.com",
                        "pogresnaLozinka"));

        assertEquals("Pogrešna lozinka.", e.getMessage());

        verify(repo).findByEmail("test@test.com");
    }

    /**
     * Proverava ponašanje sistema kada korisnik nema
     * postavljenu lozinku.
     */
    @Test
    void testLoginLozinkaUNaloguNull() {

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("test@test.com");
        korisnik.setLozinka(null);

        when(repo.findByEmail("test@test.com"))
                .thenReturn(korisnik);

        Exception e = assertThrows(
                Exception.class,
                () -> servis.login(
                        "test@test.com",
                        "123456"));

        assertEquals("Pogrešna lozinka.", e.getMessage());

        verify(repo).findByEmail("test@test.com");
    }
}