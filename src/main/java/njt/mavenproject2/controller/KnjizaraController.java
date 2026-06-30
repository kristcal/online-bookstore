package njt.mavenproject2.controller;

import java.util.List;
import njt.mavenproject2.repository.impl.KnjizaraRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Kontroler zadužen za prikaz knjižara.
 *
 * Omogućava dohvat svih knjižara iz sistema online knjižare.
 * Klasa prima HTTP zahteve na putanji {@code /api/knjizare}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/knjizare")
@CrossOrigin(origins = "http://localhost:3000")
public class KnjizaraController {

    /**
     * Repozitorijum za pristup podacima o knjižarama.
     */
    private final KnjizaraRepository repo;

    /**
     * Kreira kontroler za rad sa knjižarama.
     *
     * @param repo repozitorijum knjižara
     */
    public KnjizaraController(KnjizaraRepository repo) {
        this.repo = repo;
    }

    /**
     * Vraća listu svih knjižara.
     *
     * @return odgovor sa listom knjižara
     */
    @GetMapping
    public ResponseEntity<List<KnjizaraDto>> getAll() {
        List<KnjizaraDto> list = repo.findAll()
                .stream()
                .map(k -> new KnjizaraDto(k.getId(), k.getNaziv(), k.getLokacija()))
                .toList();

        return ResponseEntity.ok(list);
    }

    /**
     * Minimalistički DTO za prikaz knjižare.
     *
     * @param id identifikator knjižare
     * @param naziv naziv knjižare
     * @param lokacija lokacija knjižare
     */
    public static record KnjizaraDto(
            Long id,
            String naziv,
            String lokacija
    ) {
    }
}