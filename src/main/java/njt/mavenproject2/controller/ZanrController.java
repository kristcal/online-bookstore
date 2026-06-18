package njt.mavenproject2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import njt.mavenproject2.dto.impl.ZanrDto;
import njt.mavenproject2.servis.ZanrServis;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Kontroler zadužen za rad sa žanrovima.
 *
 * Omogućava prikaz, kreiranje, izmenu i brisanje žanrova.
 * Klasa prima HTTP zahteve na putanji {@code /api/zanrovi}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/zanrovi")
@CrossOrigin(origins = "http://localhost:3000")
public class ZanrController {

    /**
     * Servis za rad sa žanrovima.
     */
    private final ZanrServis service;

    /**
     * Kreira kontroler za rad sa žanrovima.
     *
     * @param service servis za rad sa žanrovima
     */
    public ZanrController(ZanrServis service) {
        this.service = service;
    }

    /**
     * Vraća listu svih žanrova.
     *
     * @return lista svih žanrova
     */
    @GetMapping
    public ResponseEntity<List<ZanrDto>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /**
     * Pronalazi žanr prema identifikatoru.
     *
     * @param id identifikator žanra
     * @return pronađeni žanr
     */
    @GetMapping("/{id}")
    public ResponseEntity<ZanrDto> getById(@PathVariable @NotNull Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Kreira novi žanr.
     *
     * @param dto DTO objekat sa podacima o žanru
     * @return kreirani žanr
     */
    @PostMapping
    public ResponseEntity<ZanrDto> create(@Valid @RequestBody @NotNull ZanrDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    /**
     * Ažurira postojeći žanr.
     *
     * @param id identifikator žanra
     * @param dto DTO objekat sa izmenjenim podacima
     * @return ažurirani žanr
     */
    @PutMapping("/{id}")
    public ResponseEntity<ZanrDto> update(@PathVariable Long id,
            @Valid @RequestBody ZanrDto dto) {
        dto.setId(id);
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

    /**
     * Briše žanr prema identifikatoru.
     *
     * @param id identifikator žanra
     * @return poruka o uspešnom brisanju
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>("Zanr deleted.", HttpStatus.OK);
    }
}