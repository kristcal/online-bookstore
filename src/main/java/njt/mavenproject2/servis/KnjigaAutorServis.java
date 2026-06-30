package njt.mavenproject2.servis;

import java.util.List;
import njt.mavenproject2.dto.impl.KnjigaAutorDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.KnjigaAutor;
import njt.mavenproject2.mapper.impl.KnjigaAutorMapper;
import njt.mavenproject2.repository.impl.AutorRepository;
import njt.mavenproject2.repository.impl.KnjigaAutorRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servis zadužen za rad sa vezom između knjiga i autora.
 *
 * Omogućava prikaz autora za određenu knjigu, dodavanje autora knjizi
 * i uklanjanje veze između knjige i autora.
 * Podaci se razmenjuju preko klase {@link KnjigaAutorDto}, dok se mapiranje
 * između DTO i entitetske klase vrši pomoću klase {@link KnjigaAutorMapper}.
 *
 * @author Korisnik
 */
@Service
public class KnjigaAutorServis {

    /**
     * Repozitorijum za pristup podacima o vezama između knjiga i autora.
     */
    private final KnjigaAutorRepository repo;

    /**
     * Repozitorijum za pristup podacima o knjigama.
     */
    private final KnjigaRepository knjigaRepo;

    /**
     * Repozitorijum za pristup podacima o autorima.
     */
    private final AutorRepository autorRepo;

    /**
     * Mapper za konverziju između entiteta KnjigaAutor i DTO objekta.
     */
    private final KnjigaAutorMapper mapper;

    /**
     * Kreira servis za rad sa vezama između knjiga i autora.
     *
     * @param repo repozitorijum veza između knjiga i autora
     * @param knjigaRepo repozitorijum knjiga
     * @param autorRepo repozitorijum autora
     * @param mapper mapper za konverziju veza između knjiga i autora
     */
    public KnjigaAutorServis(KnjigaAutorRepository repo, KnjigaRepository knjigaRepo,
            AutorRepository autorRepo, KnjigaAutorMapper mapper) {
        this.repo = repo;
        this.knjigaRepo = knjigaRepo;
        this.autorRepo = autorRepo;
        this.mapper = mapper;
    }

    /**
     * Vraća listu autora koji su povezani sa određenom knjigom.
     *
     * @param knjigaId identifikator knjige
     * @return lista autora knjige u obliku DTO objekata
     */
    public List<KnjigaAutorDto> listForKnjiga(Long knjigaId) {
        return repo.findByKnjigaId(knjigaId)
                .stream()
                .map(mapper::toDo)
                .toList();
    }

    /**
     * Dodaje autora određenoj knjizi.
     *
     * @param knjigaId identifikator knjige
     * @param dto DTO objekat sa podacima o autoru i njegovoj ulozi
     * @return kreirana veza između knjige i autora u obliku DTO objekta
     * @throws Exception ukoliko knjiga ili autor sa zadatim identifikatorom ne postoji
     */
    @Transactional
    public KnjigaAutorDto addAutorToKnjiga(Long knjigaId, KnjigaAutorDto dto) throws Exception {
        Knjiga k = knjigaRepo.findById(knjigaId);
        Autor a = autorRepo.findById(dto.getAutorId());
        KnjigaAutor ka = new KnjigaAutor(k, a, dto.getUloga());
        repo.save(ka);
        return mapper.toDo(ka);
    }

    /**
     * Uklanja vezu između knjige i autora.
     *
     * @param knjigaAutorId identifikator veze između knjige i autora
     */
    @Transactional
    public void remove(Long knjigaAutorId) {
        repo.deleteById(knjigaAutorId);
    }
}