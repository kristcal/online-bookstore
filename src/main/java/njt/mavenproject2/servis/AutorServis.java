package njt.mavenproject2.servis;

import java.util.List;
import njt.mavenproject2.dto.impl.AutorDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.mapper.impl.AutorMapper;
import njt.mavenproject2.repository.impl.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servis zadužen za rad sa autorima.
 *
 * Omogućava pronalaženje, kreiranje, izmenu i brisanje autora. Podaci se
 * razmenjuju preko klase {@link AutorDto}, dok se mapiranje između DTO i
 * entitetske klase vrši pomoću klase {@link AutorMapper}.
 *
 * @author Korisnik
 */
@Service
public class AutorServis {

    /**
     * Repozitorijum za pristup podacima o autorima.
     */
    private final AutorRepository repo;

    /**
     * Mapper za konverziju između entiteta Autor i DTO objekta.
     */
    private final AutorMapper mapper;

    /**
     * Kreira servis za rad sa autorima.
     *
     * @param repo repozitorijum autora
     * @param mapper mapper za konverziju autora
     */
    public AutorServis(AutorRepository repo, AutorMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Vraća listu svih autora.
     *
     * @return lista svih autora u obliku DTO objekata
     */
    public List<AutorDto> findAll() {
        return repo.findAll().stream().map(mapper::toDo).toList();
    }

    /**
     * Pronalazi autora prema identifikatoru.
     *
     * @param id identifikator autora
     * @return pronađeni autor u obliku DTO objekta
     * @throws Exception ukoliko autor sa zadatim identifikatorom ne postoji
     */
    public AutorDto findById(Long id) throws Exception {
        return mapper.toDo(repo.findById(id));
    }

    /**
     * Kreira novog autora.
     *
     * @param dto DTO objekat sa podacima o autoru
     * @return kreirani autor u obliku DTO objekta
     */
    @Transactional
    public AutorDto create(AutorDto dto) {
        return saveAndMap(dto);
    }

    /**
     * Ažurira podatke o autoru.
     *
     * @param dto DTO objekat sa izmenjenim podacima o autoru
     * @return ažurirani autor u obliku DTO objekta
     */
    @Transactional
    public AutorDto update(AutorDto dto) {
        return saveAndMap(dto);
    }

    /**
     * Čuva autora i vraća rezultat u obliku DTO objekta.
     *
     * @param dto DTO objekat sa podacima o autoru
     * @return sačuvani autor u obliku DTO objekta
     */
    private AutorDto saveAndMap(AutorDto dto) {
        Autor a = mapper.toEntity(dto);
        repo.save(a);
        return mapper.toDo(a);
    }

    /**
     * Briše autora prema identifikatoru.
     *
     * @param id identifikator autora koji se briše
     */
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
