package njt.mavenproject2.controller;

import njt.mavenproject2.dto.auth.LoginReq;
import njt.mavenproject2.dto.auth.LoginRes;
import njt.mavenproject2.dto.auth.RegisterReq;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.repository.impl.KorisnikRepository;
import njt.mavenproject2.servis.AuthServis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Kontroler zadužen za autentifikaciju korisnika.
 *
 * Omogućava prijavu i registraciju korisnika u sistemu online knjižare.
 * Klasa prima HTTP zahteve na putanji {@code /api/auth}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Servis za autentifikaciju korisnika.
     */
    private final AuthServis authServis;

    /**
     * Repozitorijum za pristup podacima o korisnicima.
     */
    private final KorisnikRepository korisnikRepo;

    /**
     * Kreira kontroler za autentifikaciju.
     *
     * @param authServis servis za autentifikaciju
     * @param korisnikRepo repozitorijum korisnika
     */
    public AuthController(AuthServis authServis, KorisnikRepository korisnikRepo) {
        this.authServis = authServis;
        this.korisnikRepo = korisnikRepo;
    }

    /**
     * Prijavljuje korisnika u sistem.
     *
     * Ako su email i lozinka ispravni, vraća se odgovor sa tokenom i osnovnim
     * podacima o korisniku.
     *
     * @param req zahtev za prijavu korisnika
     * @return odgovor sa podacima o prijavljenom korisniku ili poruka o grešci
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        try {
            Korisnik k = authServis.login(req.email(), req.password());
            String token = "demo-token";
            return ResponseEntity.ok(
                    new LoginRes(
                            token,
                            k.getId(),
                            k.getEmail(),
                            k.getIme(),
                            k.getPrezime(),
                            k.getUloga()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    /**
     * Registruje novog korisnika.
     *
     * Pre kreiranja korisnika proverava se da li je email adresa već zauzeta.
     * Novom korisniku se podrazumevano dodeljuje uloga USER.
     *
     * @param req zahtev za registraciju korisnika
     * @return HTTP odgovor koji označava uspeh ili grešku pri registraciji
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        if (korisnikRepo.findByEmail(req.email()) != null) {
            return ResponseEntity.status(409).body("Email je zauzet.");
        }

        Korisnik k = new Korisnik();
        k.setIme(req.ime());
        k.setPrezime(req.prezime());
        k.setEmail(req.email());
        k.setLozinka(req.password());
        k.setUloga("USER");

        korisnikRepo.save(k);

        return ResponseEntity.status(201).build();
    }
}