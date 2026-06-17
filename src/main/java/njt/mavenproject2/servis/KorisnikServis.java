package njt.mavenproject2.servis;

import java.util.List;
import java.util.stream.Collectors;
import njt.mavenproject2.dto.impl.KorisnikDto;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.mapper.impl.KorisnikMapper;
import njt.mavenproject2.repository.impl.KorisnikRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servis zadužen za rad sa korisnicima.
 *
 * Omogućava pronalaženje, kreiranje, izmenu i brisanje korisnika.
 * Podaci se razmenjuju preko klase {@link KorisnikDto}, dok se mapiranje
 * između DTO i entitetske klase vrši pomoću klase {@link KorisnikMapper}.
 *
 * @author Korisnik
 */
@Service
public class KorisnikServis {

    /**
     * Repozitorijum za pristup podacima o korisnicima.
     */
    private final KorisnikRepository repo;

    /**
     * Mapper za konverziju između entiteta Korisnik i DTO objekta.
     */
    private final KorisnikMapper mapper;

    /**
     * Kreira servis za rad sa korisnicima.
     *
     * @param repo repozitorijum korisnika
     * @param mapper mapper za konverziju korisnika
     */
    public KorisnikServis(KorisnikRepository repo, KorisnikMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Vraća listu svih korisnika.
     *
     * @return lista svih korisnika u obliku DTO objekata
     */
    public List<KorisnikDto> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDo)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi korisnika prema identifikatoru.
     *
     * @param id identifikator korisnika
     * @return pronađeni korisnik u obliku DTO objekta
     * @throws Exception ukoliko korisnik sa zadatim identifikatorom ne postoji
     */
    public KorisnikDto findById(Long id) throws Exception {
        return mapper.toDo(repo.findById(id));
    }

    /**
     * Kreira novog korisnika.
     *
     * @param dto DTO objekat sa podacima o korisniku
     * @return kreirani korisnik u obliku DTO objekta
     */
    @Transactional
    public KorisnikDto create(KorisnikDto dto) {
        Korisnik k = mapper.toEntity(dto);
        repo.save(k);
        return mapper.toDo(k);
    }

    /**
     * Ažurira podatke o korisniku.
     *
     * @param id identifikator korisnika koji se ažurira
     * @param dto DTO objekat sa izmenjenim podacima o korisniku
     * @return ažurirani korisnik u obliku DTO objekta
     * @throws Exception ukoliko korisnik sa zadatim identifikatorom ne postoji
     */
    @Transactional
    public KorisnikDto update(Long id, KorisnikDto dto) throws Exception {
        Korisnik existing = repo.findById(id);

        existing.setIme(dto.getIme());
        existing.setPrezime(dto.getPrezime());
        existing.setEmail(dto.getEmail());
        existing.setLozinka(dto.getLozinka());

        repo.save(existing);
        return mapper.toDo(existing);
    }

    /**
     * Briše korisnika prema identifikatoru.
     *
     * @param id identifikator korisnika koji se briše
     */
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}