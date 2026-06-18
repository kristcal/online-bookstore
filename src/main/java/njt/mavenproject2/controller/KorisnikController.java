package njt.mavenproject2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import njt.mavenproject2.dto.impl.KorisnikDto;
import njt.mavenproject2.servis.KorisnikServis;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Kontroler zadužen za rad sa korisnicima.
 *
 * Omogućava prikaz, kreiranje, izmenu i brisanje korisnika.
 * Klasa prima HTTP zahteve na putanji {@code /api/korisnik}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/korisnik")
public class KorisnikController {

    /**
     * Servis za rad sa korisnicima.
     */
    private final KorisnikServis service;

    /**
     * Kreira kontroler za rad sa korisnicima.
     *
     * @param service servis za rad sa korisnicima
     */
    public KorisnikController(KorisnikServis service) {
        this.service = service;
    }

    /**
     * Vraća listu svih korisnika.
     *
     * @return lista svih korisnika
     */
    @GetMapping
    public ResponseEntity<List<KorisnikDto>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /**
     * Pronalazi korisnika prema identifikatoru.
     *
     * @param id identifikator korisnika
     * @return pronađeni korisnik
     */
    @GetMapping("/{id}")
    public ResponseEntity<KorisnikDto> getById(@PathVariable @NotNull Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Kreira novog korisnika.
     *
     * @param dto DTO objekat sa podacima o korisniku
     * @return kreirani korisnik
     */
    @PostMapping
    public ResponseEntity<KorisnikDto> create(@Valid @RequestBody @NotNull KorisnikDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    /**
     * Ažurira postojećeg korisnika.
     *
     * @param id identifikator korisnika
     * @param dto DTO objekat sa izmenjenim podacima
     * @return ažurirani korisnik
     */
    @PutMapping("/{id}")
    public ResponseEntity<KorisnikDto> update(@PathVariable Long id,
            @Valid @RequestBody KorisnikDto dto) {
        try {
            return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Briše korisnika prema identifikatoru.
     *
     * @param id identifikator korisnika
     * @return poruka o uspešnom brisanju
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>("Korisnik obrisan.", HttpStatus.OK);
    }
}