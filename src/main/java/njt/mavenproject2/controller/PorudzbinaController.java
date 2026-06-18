package njt.mavenproject2.controller;

import jakarta.validation.Valid;
import java.util.List;
import njt.mavenproject2.dto.impl.PorudzbinaDto;
import njt.mavenproject2.servis.PorudzbinaServis;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Kontroler zadužen za rad sa porudžbinama.
 *
 * Omogućava pregled, kreiranje, izmenu, brisanje porudžbina
 * i pregled porudžbina određenog korisnika.
 * Klasa prima HTTP zahteve na putanji {@code /api/porudzbine}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/porudzbine")
public class PorudzbinaController {

    /**
     * Servis za rad sa porudžbinama.
     */
    private final PorudzbinaServis service;

    /**
     * Kreira kontroler za rad sa porudžbinama.
     *
     * @param service servis za rad sa porudžbinama
     */
    public PorudzbinaController(PorudzbinaServis service) {
        this.service = service;
    }

    /**
     * Vraća listu svih porudžbina.
     *
     * @return lista svih porudžbina
     */
    @GetMapping
    public ResponseEntity<List<PorudzbinaDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Pronalazi porudžbinu prema identifikatoru.
     *
     * @param id identifikator porudžbine
     * @return pronađena porudžbina ili poruka o grešci
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Kreira novu porudžbinu.
     *
     * @param req DTO objekat sa podacima o porudžbini
     * @return kreirana porudžbina ili poruka o grešci
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@Valid @RequestBody PorudzbinaDto req) {
        try {
            PorudzbinaDto res = service.create(req);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Ažurira postojeću porudžbinu.
     *
     * @param id identifikator porudžbine
     * @param req DTO objekat sa izmenjenim podacima
     * @return ažurirana porudžbina ili poruka o grešci
     */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PorudzbinaDto req) {
        try {
            PorudzbinaDto res = service.update(id, req);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Briše porudžbinu prema identifikatoru.
     *
     * @param id identifikator porudžbine
     * @return poruka o uspešnom brisanju
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Porudžbina obrisana.");
    }

    /**
     * Vraća porudžbine određenog korisnika.
     *
     * @param korisnikId identifikator korisnika
     * @return lista porudžbina korisnika
     */
    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<List<PorudzbinaDto>> moje(@PathVariable Long korisnikId) {
        return ResponseEntity.ok(service.findByKorisnik(korisnikId));
    }
}