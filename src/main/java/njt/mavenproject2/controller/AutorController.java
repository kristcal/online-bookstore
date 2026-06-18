package njt.mavenproject2.controller;

import java.util.List;
import java.util.stream.Collectors;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.repository.impl.AutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Kontroler zadužen za prikaz autora.
 *
 * Omogućava dohvat svih autora iz sistema online knjižare.
 * Klasa prima HTTP zahteve na putanji {@code /api/autori}.
 *
 * @author Korisnik
 */
@RestController
@RequestMapping("/api/autori")
@CrossOrigin(origins = "http://localhost:3000")
public class AutorController {

    /**
     * Repozitorijum za pristup podacima o autorima.
     */
    private final AutorRepository repo;

    /**
     * Kreira kontroler za rad sa autorima.
     *
     * @param repo repozitorijum autora
     */
    public AutorController(AutorRepository repo) {
        this.repo = repo;
    }

    /**
     * Vraća listu svih autora.
     *
     * @return odgovor sa listom autora
     */
    @GetMapping
    public ResponseEntity<List<AutorDto>> getAll() {
        List<AutorDto> list = repo.findAll()
                .stream()
                .map(a -> new AutorDto(a.getId(), a.getIme(), a.getPrezime()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    /**
     * Minimalistički DTO za prikaz autora.
     *
     * @param id identifikator autora
     * @param ime ime autora
     * @param prezime prezime autora
     */
    public static record AutorDto(
            Long id,
            String ime,
            String prezime
    ) {
    }
}