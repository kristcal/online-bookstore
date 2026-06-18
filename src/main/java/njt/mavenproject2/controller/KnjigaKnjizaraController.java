package njt.mavenproject2.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import njt.mavenproject2.dto.impl.KnjigaKnjizaraDto;
import njt.mavenproject2.servis.KnjigaKnjizaraServis;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Kontroler zadužen za rad sa dostupnošću knjiga u knjižarama.
 *
 * Omogućava prikaz dostupnosti knjige, dodavanje knjige u ponudu knjižare,
 * izmenu količine i uklanjanje knjige iz ponude.
 * Klasa prima HTTP zahteve na putanji {@code /api/knjiga/{knjigaId}/ponuda}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/knjiga/{knjigaId}/ponuda")
public class KnjigaKnjizaraController {

    /**
     * Servis za rad sa dostupnošću knjiga u knjižarama.
     */
    private final KnjigaKnjizaraServis servis;

    /**
     * Kreira kontroler za rad sa ponudom knjiga u knjižarama.
     *
     * @param servis servis za dostupnost knjiga u knjižarama
     */
    public KnjigaKnjizaraController(KnjigaKnjizaraServis servis) {
        this.servis = servis;
    }

    /**
     * Vraća dostupnost određene knjige po knjižarama.
     *
     * @param knjigaId identifikator knjige
     * @return lista dostupnosti knjige
     */
    @GetMapping
    public ResponseEntity<List<KnjigaKnjizaraDto>> list(@PathVariable Long knjigaId) {
        return ResponseEntity.ok(servis.listForKnjiga(knjigaId));
    }

    /**
     * Dodaje ili postavlja dostupnost knjige u knjižari.
     *
     * @param knjigaId identifikator knjige
     * @param dto DTO objekat sa podacima o knjižari i količini
     * @return kreirana dostupnost knjige
     */
    @PostMapping
    public ResponseEntity<KnjigaKnjizaraDto> add(@PathVariable Long knjigaId,
            @Valid @RequestBody KnjigaKnjizaraDto dto) {
        try {
            return new ResponseEntity<>(servis.addOrSet(knjigaId, dto), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Ažurira količinu knjige u knjižari.
     *
     * @param knjigaId identifikator knjige
     * @param kkId identifikator veze između knjige i knjižare
     * @param body mapa koja sadrži novu količinu
     * @return ažurirana dostupnost knjige
     */
    @PutMapping("/{kkId}")
    public ResponseEntity<KnjigaKnjizaraDto> updateQty(@PathVariable Long knjigaId,
            @PathVariable Long kkId,
            @RequestBody Map<String, Integer> body) {
        try {
            Integer kolicina = body.get("kolicina");
            return ResponseEntity.ok(servis.updateKolicina(kkId, kolicina));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * Uklanja knjigu iz ponude knjižare.
     *
     * @param knjigaId identifikator knjige
     * @param kkId identifikator veze između knjige i knjižare
     * @return prazan odgovor
     */
    @DeleteMapping("/{kkId}")
    public ResponseEntity<Void> remove(@PathVariable Long knjigaId, @PathVariable Long kkId) {
        servis.remove(kkId);
        return ResponseEntity.ok().build();
    }
}