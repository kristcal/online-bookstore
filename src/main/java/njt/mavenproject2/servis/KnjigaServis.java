package njt.mavenproject2.servis;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import njt.mavenproject2.dto.impl.KnjigaDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.KnjigaAutor;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.mapper.impl.KnjigaMapper;
import njt.mavenproject2.repository.impl.AutorRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.KnjizaraRepository;
import njt.mavenproject2.repository.impl.ZanrRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servis zadužen za rad sa knjigama.
 *
 * Omogućava pronalaženje, kreiranje, izmenu, brisanje i pretragu knjiga.
 * Pored osnovnih podataka o knjizi, servis upravlja i povezivanjem knjige
 * sa žanrom, autorima i dostupnošću u knjižarama.
 *
 * Podaci se razmenjuju preko klase {@link KnjigaDto}, dok se mapiranje
 * između DTO i entitetske klase vrši pomoću klase {@link KnjigaMapper}.
 *
 * @author Korisnik
 */
@Service
public class KnjigaServis {

    /**
     * Repozitorijum za pristup podacima o knjigama.
     */
    private final KnjigaRepository repo;

    /**
     * Repozitorijum za pristup podacima o žanrovima.
     */
    private final ZanrRepository zanrRepo;

    /**
     * Repozitorijum za pristup podacima o autorima.
     */
    private final AutorRepository autorRepo;

    /**
     * Repozitorijum za pristup podacima o knjižarama.
     */
    private final KnjizaraRepository knjizaraRepo;

    /**
     * Mapper za konverziju između entiteta Knjiga i DTO objekta.
     */
    private final KnjigaMapper mapper;

    /**
     * Kreira servis za rad sa knjigama.
     *
     * @param repo repozitorijum knjiga
     * @param zanrRepo repozitorijum žanrova
     * @param mapper mapper za konverziju knjiga
     * @param autorRepo repozitorijum autora
     * @param knjizaraRepo repozitorijum knjižara
     */
    public KnjigaServis(
            KnjigaRepository repo,
            ZanrRepository zanrRepo,
            KnjigaMapper mapper,
            AutorRepository autorRepo,
            KnjizaraRepository knjizaraRepo
    ) {
        this.repo = repo;
        this.zanrRepo = zanrRepo;
        this.mapper = mapper;
        this.autorRepo = autorRepo;
        this.knjizaraRepo = knjizaraRepo;
    }

    /**
     * Vraća listu svih knjiga.
     *
     * @return lista svih knjiga u obliku DTO objekata
     */
    public List<KnjigaDto> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDo)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi knjigu prema identifikatoru.
     *
     * Metoda koristi upit koji učitava i povezane podatke o knjizi.
     *
     * @param id identifikator knjige
     * @return pronađena knjiga u obliku DTO objekta
     * @throws Exception ukoliko knjiga sa zadatim identifikatorom ne postoji
     */
    @Transactional(readOnly = true)
    public KnjigaDto findById(Long id) throws Exception {
        Knjiga k = repo.findOneFull(id);
        return mapper.toDo(k);
    }

    /**
     * Kreira novu knjigu.
     *
     * Pored osnovnih podataka o knjizi, metoda postavlja žanr, autore
     * i dostupnost knjige u knjižarama ukoliko su ti podaci prosleđeni.
     *
     * @param dto DTO objekat sa podacima o knjizi
     * @return kreirana knjiga u obliku DTO objekta
     * @throws Exception ukoliko povezani žanr, autor ili knjižara ne postoje
     */
    @Transactional
    public KnjigaDto create(KnjigaDto dto) throws Exception {
        Knjiga k = mapper.toEntity(dto);

        if (dto.getZanrId() != null) {
            k.setZanr(zanrRepo.findById(dto.getZanrId()));
        }

        if (dto.getAutori() != null) {
            for (KnjigaDto.AutorView a : dto.getAutori()) {
                if (a.id == null) {
                    continue;
                }

                Autor autor = autorRepo.findById(a.id);

                KnjigaAutor ka = new KnjigaAutor();
                ka.setKnjiga(k);
                ka.setAutor(autor);
                ka.setUloga(a.uloga);

                k.getAutori().add(ka);
            }
        }

        if (dto.getDostupnost() != null) {
            for (KnjigaDto.DostupnostView d : dto.getDostupnost()) {
                if (d.knjizaraId == null) {
                    continue;
                }

                Knjizara kn = knjizaraRepo.findById(d.knjizaraId);

                KnjigaKnjizara kk = new KnjigaKnjizara();
                kk.setKnjiga(k);
                kk.setKnjizara(kn);
                kk.setKolicina(d.kolicina == null ? 0 : d.kolicina);

                k.getDostupnost().add(kk);
            }
        }

        repo.save(k);
        return mapper.toDo(repo.findOneFull(k.getId()));
    }

    /**
     * Ažurira podatke o knjizi.
     *
     * Metoda menja osnovne podatke o knjizi, žanr, listu autora i dostupnost
     * knjige u knjižarama. Za dostupnost se koristi diff pristup kako ne bi
     * dolazilo do dupliranja postojećih veza.
     *
     * @param id identifikator knjige koja se ažurira
     * @param dto DTO objekat sa izmenjenim podacima o knjizi
     * @return ažurirana knjiga u obliku DTO objekta
     * @throws Exception ukoliko knjiga, žanr, autor ili knjižara ne postoje
     */
    @Transactional
    public KnjigaDto update(Long id, KnjigaDto dto) throws Exception {
        Knjiga existing = repo.findById(id);

        existing.setNaziv(dto.getNaziv());
        existing.setOpis(dto.getOpis());
        existing.setCena(dto.getCena());
        existing.setIsbn(dto.getIsbn());
        existing.setGodinaIzdanja(dto.getGodinaIzdanja());
        existing.setImageUrl(dto.getImageUrl());
        existing.setZanr(dto.getZanrId() != null ? zanrRepo.findById(dto.getZanrId()) : null);

        existing.getAutori().clear();
        if (dto.getAutori() != null) {
            for (KnjigaDto.AutorView a : dto.getAutori()) {
                if (a.id == null) {
                    continue;
                }

                Autor autor = autorRepo.findById(a.id);

                KnjigaAutor ka = new KnjigaAutor();
                ka.setKnjiga(existing);
                ka.setAutor(autor);
                ka.setUloga(a.uloga);

                existing.getAutori().add(ka);
            }
        }

        Map<Long, KnjigaKnjizara> poKnjizari = existing.getDostupnost()
                .stream()
                .filter(kk -> kk.getKnjizara() != null)
                .collect(Collectors.toMap(kk -> kk.getKnjizara().getId(), kk -> kk));

        if (dto.getDostupnost() != null) {
            for (KnjigaDto.DostupnostView d : dto.getDostupnost()) {
                if (d.knjizaraId == null) {
                    continue;
                }

                KnjigaKnjizara kk = poKnjizari.remove(d.knjizaraId);

                if (kk == null) {
                    Knjizara kn = knjizaraRepo.findById(d.knjizaraId);

                    kk = new KnjigaKnjizara();
                    kk.setKnjiga(existing);
                    kk.setKnjizara(kn);

                    existing.getDostupnost().add(kk);
                }

                kk.setKolicina(d.kolicina == null ? 0 : Math.max(0, d.kolicina));
            }
        }

        poKnjizari.values().forEach(existing.getDostupnost()::remove);

        repo.save(existing);
        return mapper.toDo(repo.findOneFull(existing.getId()));
    }

    /**
     * Briše knjigu prema identifikatoru.
     *
     * @param id identifikator knjige koja se briše
     */
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    /**
     * Pronalazi knjige prema žanru.
     *
     * @param zanrId identifikator žanra
     * @return lista knjiga iz zadatog žanra u obliku DTO objekata
     */
    public List<KnjigaDto> findByGenre(Long zanrId) {
        return repo.findByGenre(zanrId)
                .stream()
                .map(mapper::toDo)
                .collect(Collectors.toList());
    }

    /**
     * Pronalazi knjige čija je cena manja od zadate vrednosti.
     *
     * @param max maksimalna cena knjige
     * @return lista knjiga jeftinijih od zadate vrednosti u obliku DTO objekata
     */
    public List<KnjigaDto> findCheaperThan(Double max) {
        return repo.findCheaperThan(max)
                .stream()
                .map(mapper::toDo)
                .collect(Collectors.toList());
    }

    /**
     * Pretražuje knjige prema tekstu, žanru i maksimalnoj ceni.
     *
     * @param q tekst za pretragu
     * @param zanrId identifikator žanra
     * @param max maksimalna cena knjige
     * @return lista pronađenih knjiga u obliku DTO objekata
     */
    public List<KnjigaDto> search(String q, Long zanrId, Double max) {
        return repo.search(q, zanrId, max)
                .stream()
                .map(mapper::toDo)
                .collect(Collectors.toList());
    }
}