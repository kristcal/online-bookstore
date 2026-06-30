package njt.mavenproject2.servis;

import java.util.List;
import njt.mavenproject2.dto.impl.StavkaPorudzbineDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.Porudzbina;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;
import njt.mavenproject2.mapper.impl.StavkaPorudzbineMapper;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.PorudzbinaRepository;
import njt.mavenproject2.repository.impl.StavkaPorudzbineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servis zadužen za rad sa stavkama porudžbine.
 *
 * Omogućava prikaz stavki određene porudžbine, dodavanje nove stavke,
 * izmenu postojeće stavke i uklanjanje stavke iz porudžbine.
 * Nakon dodavanja, izmene ili brisanja stavke, ponovo se računa ukupan
 * iznos porudžbine.
 *
 * Podaci se razmenjuju preko klase {@link StavkaPorudzbineDto}, dok se
 * mapiranje između DTO i entitetske klase vrši pomoću klase
 * {@link StavkaPorudzbineMapper}.
 *
 * @author Korisnik
 */
@Service
public class StavkaPorudzbineServis {

    /**
     * Repozitorijum za pristup podacima o stavkama porudžbine.
     */
    private final StavkaPorudzbineRepository repo;

    /**
     * Repozitorijum za pristup podacima o porudžbinama.
     */
    private final PorudzbinaRepository porRepo;

    /**
     * Repozitorijum za pristup podacima o knjigama.
     */
    private final KnjigaRepository knjigaRepo;

    /**
     * Mapper za konverziju između entiteta StavkaPorudzbine i DTO objekta.
     */
    private final StavkaPorudzbineMapper mapper;

    /**
     * Kreira servis za rad sa stavkama porudžbine.
     *
     * @param repo repozitorijum stavki porudžbine
     * @param porRepo repozitorijum porudžbina
     * @param knjigaRepo repozitorijum knjiga
     * @param mapper mapper za konverziju stavki porudžbine
     */
    public StavkaPorudzbineServis(StavkaPorudzbineRepository repo,
            PorudzbinaRepository porRepo,
            KnjigaRepository knjigaRepo,
            StavkaPorudzbineMapper mapper) {
        this.repo = repo;
        this.porRepo = porRepo;
        this.knjigaRepo = knjigaRepo;
        this.mapper = mapper;
    }

    /**
     * Vraća listu stavki za određenu porudžbinu.
     *
     * @param porudzbinaId identifikator porudžbine
     * @return lista stavki porudžbine u obliku DTO objekata
     */
    public List<StavkaPorudzbineDto> listForPorudzbina(Long porudzbinaId) {
        return repo.findByPorudzbinaId(porudzbinaId)
                .stream()
                .map(mapper::toDo)
                .toList();
    }

    /**
     * Dodaje novu stavku u porudžbinu.
     *
     * Ako redni broj stavke nije prosleđen, automatski se dodeljuje sledeći
     * redni broj u okviru porudžbine. Nakon dodavanja stavke ponovo se računa
     * ukupan iznos porudžbine.
     *
     * @param porudzbinaId identifikator porudžbine
     * @param dto DTO objekat sa podacima o stavki
     * @return dodata stavka porudžbine u obliku DTO objekta
     * @throws Exception ukoliko porudžbina ili knjiga ne postoji
     */
    @Transactional
    public StavkaPorudzbineDto add(Long porudzbinaId, StavkaPorudzbineDto dto) throws Exception {
        Porudzbina p = porRepo.findById(porudzbinaId);
        Knjiga k = knjigaRepo.findById(dto.getKnjigaId());

        StavkaPorudzbine s = mapper.toEntity(dto);

        Integer rb = s.getRb();
        if (rb == null || rb <= 0) {
            rb = repo.findMaxRbForPorudzbina(porudzbinaId) + 1;
        }

        s.setRb(rb);
        s.setPorudzbina(p);
        s.setKnjiga(k);

        repo.save(s);

        p.setUkupanIznos(
                repo.findByPorudzbinaId(porudzbinaId)
                        .stream()
                        .mapToDouble(x -> (x.getCenaK() != null && x.getKolicina() != null)
                        ? x.getCenaK() * x.getKolicina()
                        : 0d)
                        .sum()
        );

        porRepo.save(p);

        return mapper.toDo(s);
    }

    /**
     * Ažurira postojeću stavku porudžbine.
     *
     * Moguće je promeniti količinu, cenu knjige i knjigu na koju se stavka
     * odnosi. Nakon izmene ponovo se računa ukupan iznos porudžbine.
     *
     * @param stavkaId identifikator stavke koja se ažurira
     * @param dto DTO objekat sa izmenjenim podacima o stavki
     * @return ažurirana stavka porudžbine u obliku DTO objekta
     * @throws Exception ukoliko stavka ili knjiga ne postoji
     */
    @Transactional
    public StavkaPorudzbineDto update(Long stavkaId, StavkaPorudzbineDto dto) throws Exception {
        StavkaPorudzbine s = repo.findById(stavkaId);

        if (dto.getKolicina() != null) {
            s.setKolicina(dto.getKolicina());
        }

        if (dto.getCenaK() != null) {
            s.setCenaK(dto.getCenaK());
        }

        if (dto.getKnjigaId() != null) {
            s.setKnjiga(knjigaRepo.findById(dto.getKnjigaId()));
        }

        repo.save(s);

        Long porId = s.getPorudzbina().getId();
        Porudzbina p = porRepo.findById(porId);

        p.setUkupanIznos(
                repo.findByPorudzbinaId(porId)
                        .stream()
                        .mapToDouble(x -> (x.getCenaK() != null && x.getKolicina() != null)
                        ? x.getCenaK() * x.getKolicina()
                        : 0d)
                        .sum()
        );

        porRepo.save(p);

        return mapper.toDo(s);
    }

    /**
     * Uklanja stavku porudžbine.
     *
     * Nakon brisanja stavke ponovo se računa ukupan iznos porudžbine.
     * Ako stavka ne postoji, metoda ne baca grešku.
     *
     * @param stavkaId identifikator stavke koja se briše
     */
    @Transactional
    public void remove(Long stavkaId) {
        try {
            StavkaPorudzbine s = repo.findById(stavkaId);
            Long porId = s.getPorudzbina().getId();

            repo.deleteById(stavkaId);

            Porudzbina p = porRepo.findById(porId);

            p.setUkupanIznos(
                    repo.findByPorudzbinaId(porId)
                            .stream()
                            .mapToDouble(x -> (x.getCenaK() != null && x.getKolicina() != null)
                            ? x.getCenaK() * x.getKolicina()
                            : 0d)
                            .sum()
            );

            porRepo.save(p);
        } catch (Exception ignore) {
            // Stavka ne postoji - metoda namerno ne baca grešku, brisanje se tretira kao idempotentno.
        }
    }
}