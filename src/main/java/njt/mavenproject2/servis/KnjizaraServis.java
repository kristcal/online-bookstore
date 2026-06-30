package njt.mavenproject2.servis;

import java.util.List;
import njt.mavenproject2.dto.impl.KnjizaraDto;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.mapper.impl.KnjizaraMapper;
import njt.mavenproject2.repository.impl.KnjizaraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servis zadužen za rad sa knjižarama.
 *
 * Omogućava pronalaženje, kreiranje, izmenu i brisanje knjižara.
 * Podaci se razmenjuju preko klase {@link KnjizaraDto}, dok se mapiranje
 * između DTO i entitetske klase vrši pomoću klase {@link KnjizaraMapper}.
 *
 * @author Korisnik
 */
@Service
public class KnjizaraServis {

    /**
     * Repozitorijum za pristup podacima o knjižarama.
     */
    private final KnjizaraRepository knjizaraRepository;

    /**
     * Mapper za konverziju između entiteta Knjizara i DTO objekta.
     */
    private final KnjizaraMapper knjizaraMapper;

    /**
     * Kreira servis za rad sa knjižarama.
     *
     * @param knjizaraRepository repozitorijum knjižara
     * @param knjizaraMapper mapper za konverziju knjižara
     */
    @Autowired
    public KnjizaraServis(KnjizaraRepository knjizaraRepository,
            KnjizaraMapper knjizaraMapper) {
        this.knjizaraRepository = knjizaraRepository;
        this.knjizaraMapper = knjizaraMapper;
    }

    /**
     * Vraća listu svih knjižara.
     *
     * @return lista svih knjižara u obliku DTO objekata
     */
    public List<KnjizaraDto> findAll() {
        return knjizaraRepository.findAll()
                .stream()
                .map(knjizaraMapper::toDo)
                .toList();
    }

    /**
     * Pronalazi knjižaru prema identifikatoru.
     *
     * @param id identifikator knjižare
     * @return pronađena knjižara u obliku DTO objekta
     * @throws Exception ukoliko knjižara sa zadatim identifikatorom ne postoji
     */
    public KnjizaraDto findById(Long id) throws Exception {
        return knjizaraMapper.toDo(knjizaraRepository.findById(id));
    }

    /**
     * Kreira novu knjižaru.
     *
     * @param dto DTO objekat sa podacima o knjižari
     * @return kreirana knjižara u obliku DTO objekta
     */
    @Transactional
    public KnjizaraDto create(KnjizaraDto dto) {
        Knjizara knjizara = knjizaraMapper.toEntity(dto);
        knjizaraRepository.save(knjizara);
        return knjizaraMapper.toDo(knjizara);
    }

    /**
     * Briše knjižaru prema identifikatoru.
     *
     * @param id identifikator knjižare koja se briše
     */
    @Transactional
    public void deleteById(Long id) {
        knjizaraRepository.deleteById(id);
    }

    /**
     * Ažurira podatke o knjižari.
     *
     * @param dto DTO objekat sa izmenjenim podacima o knjižari
     * @return ažurirana knjižara u obliku DTO objekta
     */
    @Transactional
    public KnjizaraDto update(KnjizaraDto dto) {
        Knjizara updated = knjizaraMapper.toEntity(dto);
        knjizaraRepository.save(updated);
        return knjizaraMapper.toDo(updated);
    }
}