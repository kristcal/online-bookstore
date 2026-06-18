package njt.mavenproject2.controller;

import jakarta.validation.Valid;
import java.util.List;
import njt.mavenproject2.dto.impl.StavkaPorudzbineDto;
import njt.mavenproject2.servis.StavkaPorudzbineServis;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Kontroler zadužen za rad sa stavkama porudžbine.
 *
 * Omogućava prikaz, dodavanje, izmenu i brisanje stavki određene porudžbine.
 * Klasa prima HTTP zahteve na putanji
 * {@code /api/porudzbina/{porudzbinaId}/stavke}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/porudzbina/{porudzbinaId}/stavke")
public class PorudzbinaStavkeController {

    /**
     * Servis za rad sa stavkama porudžbine.
     */
    private final StavkaPorudzbineServis servis;

    /**
     * Kreira kontroler za rad sa stavkama porudžbine.
     *
     * @param servis servis za rad sa stavkama porudžbine
     */
    public PorudzbinaStavkeController(StavkaPorudzbineServis servis) {
        this.servis = servis;
    }

    /**
     * Vraća listu stavki određene porudžbine.
     *
     * @param porudzbinaId identifikator porudžbine
     * @return lista stavki porudžbine
     */
    @GetMapping
    public ResponseEntity<List<StavkaPorudzbineDto>> list(@PathVariable Long porudzbinaId) {
        return ResponseEntity.ok(servis.listForPorudzbina(porudzbinaId));
    }

    /**
     * Dodaje novu stavku u porudžbinu.
     *
     * @param porudzbinaId identifikator porudžbine
     * @param dto DTO objekat sa podacima o stavki
     * @return kreirana stavka porudžbine
     */
    @PostMapping
    public ResponseEntity<StavkaPorudzbineDto> add(@PathVariable Long porudzbinaId,
            @Valid @RequestBody StavkaPorudzbineDto dto) {
        try {
            return new ResponseEntity<>(servis.add(porudzbinaId, dto), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Ažurira postojeću stavku porudžbine.
     *
     * @param porudzbinaId identifikator porudžbine
     * @param stavkaId identifikator stavke
     * @param dto DTO objekat sa izmenjenim podacima
     * @return ažurirana stavka porudžbine
     */
    @PutMapping("/{stavkaId}")
    public ResponseEntity<StavkaPorudzbineDto> update(@PathVariable Long porudzbinaId,
            @PathVariable Long stavkaId,
            @Valid @RequestBody StavkaPorudzbineDto dto) {
        try {
            return ResponseEntity.ok(servis.update(stavkaId, dto));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Briše stavku porudžbine.
     *
     * @param porudzbinaId identifikator porudžbine
     * @param stavkaId identifikator stavke
     * @return prazan odgovor sa statusom 204
     */
    @DeleteMapping("/{stavkaId}")
    public ResponseEntity<Void> delete(@PathVariable Long porudzbinaId,
            @PathVariable Long stavkaId) {
        servis.remove(stavkaId);
        return ResponseEntity.noContent().build();
    }
}