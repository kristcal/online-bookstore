package njt.mavenproject2.controller;

import java.util.List;
import njt.mavenproject2.dto.impl.PorudzbinaDto;
import njt.mavenproject2.servis.PorudzbinaServis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Administratorski kontroler zadužen za rad sa porudžbinama.
 *
 * Omogućava administratoru pregled svih porudžbina, pregled jedne
 * porudžbine, promenu statusa i brisanje porudžbine.
 * Klasa prima HTTP zahteve na putanji {@code /api/admin/porudzbine}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/admin/porudzbine")
@CrossOrigin(origins = "http://localhost:3000")
public class PorudzbinaAdminController {

    /**
     * Servis za rad sa porudžbinama.
     */
    private final PorudzbinaServis servis;

    /**
     * Kreira administratorski kontroler za porudžbine.
     *
     * @param servis servis za rad sa porudžbinama
     */
    public PorudzbinaAdminController(PorudzbinaServis servis) {
        this.servis = servis;
    }

    /**
     * Vraća listu svih porudžbina.
     *
     * @return lista svih porudžbina
     */
    @GetMapping
    public ResponseEntity<List<PorudzbinaDto>> sve() {
        return ResponseEntity.ok(servis.findAll());
    }

    /**
     * Pronalazi jednu porudžbinu prema identifikatoru.
     *
     * @param id identifikator porudžbine
     * @return pronađena porudžbina
     * @throws Exception ukoliko porudžbina nije pronađena
     */
    @GetMapping("/{id}")
    public ResponseEntity<PorudzbinaDto> jedna(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(servis.findById(id));
    }

    /**
     * Menja status porudžbine.
     *
     * @param id identifikator porudžbine
     * @param noviStatus novi status porudžbine
     * @return porudžbina sa izmenjenim statusom
     * @throws Exception ukoliko promena statusa nije moguća
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<PorudzbinaDto> promeniStatus(
            @PathVariable Long id,
            @RequestBody String noviStatus
    ) throws Exception {
        PorudzbinaDto dto = servis.promeniStatus(id, noviStatus);
        return ResponseEntity.ok(dto);
    }

    /**
     * Briše porudžbinu prema identifikatoru.
     *
     * @param id identifikator porudžbine
     * @return prazan odgovor sa statusom 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisi(@PathVariable Long id) {
        servis.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}