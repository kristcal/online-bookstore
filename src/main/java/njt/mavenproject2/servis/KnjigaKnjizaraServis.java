package njt.mavenproject2.servis;

import java.util.List;
import njt.mavenproject2.dto.impl.KnjigaKnjizaraDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.mapper.impl.KnjigaKnjizaraMapper;
import njt.mavenproject2.repository.impl.KnjigaKnjizaraRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.KnjizaraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servis zadužen za rad sa dostupnošću knjiga u knjižarama.
 *
 * Omogućava prikaz dostupnosti određene knjige, dodavanje ili postavljanje
 * dostupnosti knjige u knjižari, izmenu količine i uklanjanje veze između
 * knjige i knjižare.
 * Podaci se razmenjuju preko klase {@link KnjigaKnjizaraDto}, dok se mapiranje
 * između DTO i entitetske klase vrši pomoću klase {@link KnjigaKnjizaraMapper}.
 *
 * @author Korisnik
 */
@Service
public class KnjigaKnjizaraServis {

    /**
     * Repozitorijum za pristup podacima o vezama između knjiga i knjižara.
     */
    private final KnjigaKnjizaraRepository repo;

    /**
     * Repozitorijum za pristup podacima o knjigama.
     */
    private final KnjigaRepository knjigaRepo;

    /**
     * Repozitorijum za pristup podacima o knjižarama.
     */
    private final KnjizaraRepository knjizaraRepo;

    /**
     * Mapper za konverziju između entiteta KnjigaKnjizara i DTO objekta.
     */
    private final KnjigaKnjizaraMapper mapper;

    /**
     * Kreira servis za rad sa dostupnošću knjiga u knjižarama.
     *
     * @param repo repozitorijum veza između knjiga i knjižara
     * @param knjigaRepo repozitorijum knjiga
     * @param knjizaraRepo repozitorijum knjižara
     * @param mapper mapper za konverziju veza između knjiga i knjižara
     */
    public KnjigaKnjizaraServis(KnjigaKnjizaraRepository repo, KnjigaRepository knjigaRepo,
            KnjizaraRepository knjizaraRepo, KnjigaKnjizaraMapper mapper) {
        this.repo = repo;
        this.knjigaRepo = knjigaRepo;
        this.knjizaraRepo = knjizaraRepo;
        this.mapper = mapper;
    }

    /**
     * Vraća listu knjižara u kojima je određena knjiga dostupna.
     *
     * @param knjigaId identifikator knjige
     * @return lista dostupnosti knjige u obliku DTO objekata
     */
    public List<KnjigaKnjizaraDto> listForKnjiga(Long knjigaId) {
        return repo.findByKnjigaId(knjigaId)
                .stream()
                .map(mapper::toDo)
                .toList();
    }

    /**
     * Dodaje ili postavlja dostupnost knjige u određenoj knjižari.
     *
     * @param knjigaId identifikator knjige
     * @param dto DTO objekat sa podacima o knjižari i količini knjige
     * @return kreirana veza između knjige i knjižare u obliku DTO objekta
     * @throws Exception ukoliko knjiga ili knjižara sa zadatim identifikatorom ne postoji
     */
    @Transactional
    public KnjigaKnjizaraDto addOrSet(Long knjigaId, KnjigaKnjizaraDto dto) throws Exception {
        Knjiga k = knjigaRepo.findById(knjigaId);
        Knjizara s = knjizaraRepo.findById(dto.getKnjizaraId());
        KnjigaKnjizara kk = new KnjigaKnjizara(k, s, dto.getKolicina());
        repo.save(kk);
        return mapper.toDo(kk);
    }

    /**
     * Ažurira količinu knjige u određenoj knjižari.
     *
     * @param kkId identifikator veze između knjige i knjižare
     * @param novaKolicina nova količina knjige
     * @return ažurirana dostupnost knjige u obliku DTO objekta
     * @throws Exception ukoliko veza između knjige i knjižare sa zadatim identifikatorom ne postoji
     */
    @Transactional
    public KnjigaKnjizaraDto updateKolicina(Long kkId, Integer novaKolicina) throws Exception {
        KnjigaKnjizara kk = repo.findById(kkId);
        kk.setKolicina(novaKolicina);
        repo.save(kk);
        return mapper.toDo(kk);
    }

    /**
     * Uklanja vezu između knjige i knjižare.
     *
     * @param kkId identifikator veze između knjige i knjižare
     */
    @Transactional
    public void remove(Long kkId) {
        repo.deleteById(kkId);
    }
}