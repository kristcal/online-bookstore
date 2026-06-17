package njt.mavenproject2.servis;

import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.repository.impl.KorisnikRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servis zadužen za autentifikaciju korisnika.
 *
 * Omogućava prijavu korisnika proverom email adrese i lozinke.
 * Podržava proveru lozinki koje su sačuvane kao običan tekst
 * ili kao BCrypt hash vrednosti.
 *
 * @author Korisnik
 */
@Service
public class AuthServis {

	/**
     * Repozitorijum za pristup podacima o korisnicima.
     */
    private final KorisnikRepository repo;
    
    /**
     * BCrypt enkoder za proveru hashovanih lozinki.
     */
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Kreira servis za autentifikaciju.
     *
     * @param repo repozitorijum korisnika
     */
    public AuthServis(KorisnikRepository repo) {
        this.repo = repo;
    }

    /**
     * Prijavljuje korisnika na osnovu email adrese i lozinke.
     *
     * Najpre pronalazi korisnika prema email adresi, a zatim
     * proverava ispravnost lozinke. Podržane su obične i
     * BCrypt hashovane lozinke.
     *
     * @param email email adresa korisnika
     * @param password lozinka korisnika
     * @return pronađeni korisnik ukoliko je autentifikacija uspešna
     * @throws Exception ukoliko korisnik ne postoji ili je lozinka neispravna
     */
    public Korisnik login(String email, String password) throws Exception {
        Korisnik k = repo.findByEmail(email);
        if (k == null) {
            throw new Exception("Nalog sa ovim emailom ne postoji.");
        }

        // Ako su lozinke već hashovane, koristi encoder; ako nisu, poredi plain tekst.
        if (isHashed(k.getLozinka())) {
            if (!encoder.matches(password, k.getLozinka())) {
                throw new Exception("Pogrešna lozinka.");
            }
        } else {
            if (!password.equals(k.getLozinka())) {
                throw new Exception("Pogrešna lozinka.");
            }
        }

        return k;
    }

    /**
     * Proverava da li je lozinka sačuvana u BCrypt formatu.
     *
     * @param pw lozinka koja se proverava
     * @return true ako je lozinka hashovana, false u suprotnom
     */
    private boolean isHashed(String pw) {
        return pw != null && pw.startsWith("$2a$");
    }
}
