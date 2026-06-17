package njt.mavenproject2.servis;

import java.util.List;
import java.util.stream.Collectors;
import njt.mavenproject2.dto.impl.ZanrDto;
import njt.mavenproject2.entity.impl.Zanr;
import njt.mavenproject2.mapper.impl.ZanrMapper;
import njt.mavenproject2.repository.impl.ZanrRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servis zadužen za rad sa žanrovima.
 *
 * Omogućava pronalaženje, kreiranje, izmenu i brisanje žanrova.
 * Podaci se razmenjuju preko klase {@link ZanrDto}, dok se mapiranje
 * između DTO i entitetske klase vrši pomoću klase {@link ZanrMapper}.
 *
 * @author Korisnik
 */
@Service
public class ZanrServis {

    /**
     * Repozitorijum za pristup podacima o žanrovima.
     */
    private final ZanrRepository zanrRepository;

    /**
     * Mapper za konverziju između entiteta Zanr i DTO objekta.
     */
    private final ZanrMapper zanrMapper;

    /**
     * Kreira servis za rad sa žanrovima.
     *
     * @param repo repozitorijum žanrova
     * @param mapper mapper za konverziju žanrova
     */
    public ZanrServis(ZanrRepository repo, ZanrMapper mapper) {
        this.zanrRepository = repo;
        this.zanrMapper = mapper;
    }

    /**
     * Vraća listu svih žanrova.
     *
     * @return lista svih žanrova u obliku DTO objekata
     */
    public List<ZanrDto> findAll() {
        return zanrRepository.findAll()
                .stream()
                .map(zanrMapper::toDo)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi žanr prema identifikatoru.
     *
     * @param id identifikator žanra
     * @return pronađeni žanr u obliku DTO objekta
     * @throws Exception ukoliko žanr sa zadatim identifikatorom ne postoji
     */
    public ZanrDto findById(Long id) throws Exception {
        return zanrMapper.toDo(zanrRepository.findById(id));
    }

    /**
     * Kreira novi žanr.
     *
     * @param dto DTO objekat sa podacima o žanru
     * @return kreirani žanr u obliku DTO objekta
     */
    @Transactional
    public ZanrDto create(ZanrDto dto) {
        Zanr z = zanrMapper.toEntity(dto);
        zanrRepository.save(z);
        return zanrMapper.toDo(z);
    }

    /**
     * Ažurira podatke o žanru.
     *
     * @param dto DTO objekat sa izmenjenim podacima o žanru
     * @return ažurirani žanr u obliku DTO objekta
     */
    @Transactional
    public ZanrDto update(ZanrDto dto) {
        Zanr z = zanrMapper.toEntity(dto);
        zanrRepository.save(z);
        return zanrMapper.toDo(z);
    }

    /**
     * Briše žanr prema identifikatoru.
     *
     * @param id identifikator žanra koji se briše
     */
    @Transactional
    public void deleteById(Long id) {
        zanrRepository.deleteById(id);
    }
}