package njt.mavenproject2.controller;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import njt.mavenproject2.dto.impl.KnjigaDto;
import njt.mavenproject2.servis.KnjigaServis;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Kontroler zadužen za rad sa knjigama.
 *
 * Omogućava prikaz, kreiranje, izmenu, brisanje i pretragu knjiga.
 * Klasa prima HTTP zahteve na putanji {@code /api/knjige}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/knjige")
@CrossOrigin(origins = "http://localhost:3000")
public class KnjigaController {

    /**
     * Servis za rad sa knjigama.
     */
    private final KnjigaServis service;

    /**
     * Kreira kontroler za rad sa knjigama.
     *
     * @param service servis za rad sa knjigama
     */
    public KnjigaController(KnjigaServis service) {
        this.service = service;
    }

    /**
     * Vraća listu svih knjiga.
     *
     * @return lista svih knjiga
     */
    @GetMapping
    public ResponseEntity<List<KnjigaDto>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /**
     * Pronalazi knjigu prema identifikatoru.
     *
     * @param id identifikator knjige
     * @return pronađena knjiga
     */
    @GetMapping("/{id}")
    public ResponseEntity<KnjigaDto> getById(@PathVariable @NotNull Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Kreira novu knjigu.
     *
     * @param dto DTO objekat sa podacima o knjizi
     * @return kreirana knjiga
     * @throws Exception ukoliko dođe do greške pri kreiranju
     */
    @PostMapping
    public ResponseEntity<KnjigaDto> create(@RequestBody KnjigaDto dto) throws Exception {
        return ResponseEntity.ok(service.create(dto));
    }

    /**
     * Ažurira postojeću knjigu.
     *
     * @param id identifikator knjige
     * @param dto DTO objekat sa izmenjenim podacima
     * @return ažurirana knjiga
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @PutMapping("/{id}")
    public ResponseEntity<KnjigaDto> update(@PathVariable Long id, @RequestBody KnjigaDto dto)
            throws Exception {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Briše knjigu prema identifikatoru.
     *
     * @param id identifikator knjige
     * @return prazan odgovor sa statusom 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Vraća knjige određenog žanra.
     *
     * @param zanrId identifikator žanra
     * @return lista knjiga iz zadatog žanra
     */
    @GetMapping("/genre/{zanrId}")
    public ResponseEntity<List<KnjigaDto>> byGenre(@PathVariable Long zanrId) {
        return ResponseEntity.ok(service.findByGenre(zanrId));
    }

    /**
     * Vraća knjige čija je cena manja ili jednaka zadatoj vrednosti.
     *
     * @param max maksimalna cena knjige
     * @return lista knjiga jeftinijih od zadate vrednosti
     */
    @GetMapping("/cheap")
    public ResponseEntity<List<KnjigaDto>> cheap(@RequestParam(defaultValue = "1000") Double max) {
        return ResponseEntity.ok(service.findCheaperThan(max));
    }

    /**
     * Pretražuje knjige prema tekstu, žanru i maksimalnoj ceni.
     *
     * @param q tekst za pretragu
     * @param zanrId identifikator žanra
     * @param max maksimalna cena
     * @return lista pronađenih knjiga
     */
    @GetMapping("/search")
    public ResponseEntity<List<KnjigaDto>> search(
            @RequestParam String q,
            @RequestParam(required = false) Long zanrId,
            @RequestParam(required = false) Double max) {
        return ResponseEntity.ok(service.search(q, zanrId, max));
    }
}