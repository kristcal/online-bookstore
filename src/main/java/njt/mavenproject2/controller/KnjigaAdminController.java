package njt.mavenproject2.controller;

import njt.mavenproject2.dto.impl.KnjigaDto;
import njt.mavenproject2.servis.KnjigaServis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Kontroler zadužen za administraciju knjiga.
 *
 * Omogućava administratoru kreiranje, izmenu i brisanje knjiga.
 * Klasa prima HTTP zahteve na putanji {@code /api/admin/knjige}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/admin/knjige")
@CrossOrigin(origins = "http://localhost:3000")
public class KnjigaAdminController {

    /**
     * Servis za rad sa knjigama.
     */
    private final KnjigaServis servis;

    /**
     * Kreira administratorski kontroler za knjige.
     *
     * @param servis servis za rad sa knjigama
     */
    public KnjigaAdminController(KnjigaServis servis) {
        this.servis = servis;
    }

    /**
     * Kreira novu knjigu.
     *
     * @param dto DTO objekat sa podacima o knjizi
     * @return kreirana knjiga
     * @throws Exception ukoliko dođe do greške pri kreiranju knjige
     */
    @PostMapping
    public ResponseEntity<KnjigaDto> create(@RequestBody KnjigaDto dto) throws Exception {
        return ResponseEntity.ok(servis.create(dto));
    }

    /**
     * Ažurira postojeću knjigu.
     *
     * @param id identifikator knjige
     * @param dto DTO objekat sa izmenjenim podacima o knjizi
     * @return ažurirana knjiga
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @PutMapping("/{id}")
    public ResponseEntity<KnjigaDto> update(@PathVariable Long id, @RequestBody KnjigaDto dto)
            throws Exception {
        return ResponseEntity.ok(servis.update(id, dto));
    }

    /**
     * Briše knjigu prema identifikatoru.
     *
     * @param id identifikator knjige
     * @return prazan odgovor sa statusom 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servis.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}