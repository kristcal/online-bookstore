package njt.mavenproject2.controller;

import jakarta.validation.Valid;
import java.util.List;
import njt.mavenproject2.dto.impl.KnjigaAutorDto;
import njt.mavenproject2.servis.KnjigaAutorServis;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Kontroler zadužen za rad sa autorima određene knjige.
 *
 * Omogućava prikaz autora knjige, dodavanje autora knjizi i uklanjanje
 * veze između knjige i autora.
 * Klasa prima HTTP zahteve na putanji {@code /api/knjiga/{knjigaId}/autori}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/knjiga/{knjigaId}/autori")
public class KnjigaAutorController {

    /**
     * Servis za rad sa vezama između knjiga i autora.
     */
    private final KnjigaAutorServis servis;

    /**
     * Kreira kontroler za rad sa autorima knjige.
     *
     * @param servis servis za veze između knjiga i autora
     */
    public KnjigaAutorController(KnjigaAutorServis servis) {
        this.servis = servis;
    }

    /**
     * Vraća listu autora za određenu knjigu.
     *
     * @param knjigaId identifikator knjige
     * @return lista autora knjige
     */
    @GetMapping
    public ResponseEntity<List<KnjigaAutorDto>> list(@PathVariable Long knjigaId) {
        return ResponseEntity.ok(servis.listForKnjiga(knjigaId));
    }

    /**
     * Dodaje autora određenoj knjizi.
     *
     * @param knjigaId identifikator knjige
     * @param dto DTO objekat sa podacima o autoru i ulozi
     * @return kreirana veza između knjige i autora
     */
    @PostMapping
    public ResponseEntity<KnjigaAutorDto> add(@PathVariable Long knjigaId,
            @Valid @RequestBody KnjigaAutorDto dto) {
        try {
            return new ResponseEntity<>(
                    servis.addAutorToKnjiga(knjigaId, dto),
                    HttpStatus.CREATED
            );
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Uklanja autora sa knjige.
     *
     * @param knjigaId identifikator knjige
     * @param kaId identifikator veze između knjige i autora
     * @return prazan odgovor
     */
    @DeleteMapping("/{kaId}")
    public ResponseEntity<Void> remove(@PathVariable Long knjigaId, @PathVariable Long kaId) {
        servis.remove(kaId);
        return ResponseEntity.ok().build();
    }
}