package njt.mavenproject2.servis;

import jakarta.transaction.Transactional;
import java.time.ZoneId;
import java.util.List;
import njt.mavenproject2.dto.impl.PorudzbinaDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.entity.impl.Porudzbina;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;
import njt.mavenproject2.mapper.impl.PorudzbinaMapper;
import njt.mavenproject2.repository.impl.KnjigaKnjizaraRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.KorisnikRepository;
import njt.mavenproject2.repository.impl.PorudzbinaRepository;
import org.springframework.stereotype.Service;

/**
 * Servis zadužen za rad sa porudžbinama.
 *
 * Omogućava pronalaženje, kreiranje, izmenu, brisanje i promenu statusa
 * porudžbina. Prilikom kreiranja i izmene porudžbine računa se ukupan iznos na
 * osnovu stavki porudžbine.
 *
 * Podaci se razmenjuju preko klase {@link PorudzbinaDto}, dok se mapiranje
 * između DTO i entitetske klase vrši pomoću klase {@link PorudzbinaMapper}.
 *
 * @author Korisnik
 */
@Service
public class PorudzbinaServis {

    /**
     * Repozitorijum za pristup podacima o porudžbinama.
     */
    private final PorudzbinaRepository repo;

    /**
     * Repozitorijum za pristup podacima o korisnicima.
     */
    private final KorisnikRepository korisnikRepo;

    /**
     * Repozitorijum za pristup podacima o knjigama.
     */
    private final KnjigaRepository knjigaRepo;

    /**
     * Mapper za konverziju između entiteta Porudzbina i DTO objekta.
     */
    private final PorudzbinaMapper mapper;

    /**
     * Repozitorijum za pristup podacima o dostupnosti knjiga u knjižarama.
     */
    private final KnjigaKnjizaraRepository kkRepo;

    /**
     * Status koji označava da je porudžbina obrađena i zalihe skinute.
     */
    private static final String STATUS_OBRADJENA = "OBRADJENA";

    /**
     * Kreira servis za rad sa porudžbinama.
     *
     * @param repo repozitorijum porudžbina
     * @param korisnikRepo repozitorijum korisnika
     * @param knjigaRepo repozitorijum knjiga
     * @param mapper mapper za konverziju porudžbina
     * @param kkRepo repozitorijum dostupnosti knjiga u knjižarama
     */
    public PorudzbinaServis(
            PorudzbinaRepository repo,
            KorisnikRepository korisnikRepo,
            KnjigaRepository knjigaRepo,
            PorudzbinaMapper mapper,
            KnjigaKnjizaraRepository kkRepo
    ) {
        this.repo = repo;
        this.korisnikRepo = korisnikRepo;
        this.knjigaRepo = knjigaRepo;
        this.mapper = mapper;
        this.kkRepo = kkRepo;
    }

    /**
     * Vraća listu svih porudžbina.
     *
     * @return lista svih porudžbina u obliku DTO objekata
     */
    public List<PorudzbinaDto> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDo)
                .toList();
    }

    /**
     * Pronalazi porudžbinu prema identifikatoru.
     *
     * @param id identifikator porudžbine
     * @return pronađena porudžbina u obliku DTO objekta
     * @throws Exception ukoliko porudžbina sa zadatim identifikatorom ne
     * postoji
     */
    public PorudzbinaDto findById(Long id) throws Exception {
        return mapper.toDo(repo.findById(id));
    }

    /**
     * Kreira novu porudžbinu.
     *
     * Datum se postavlja na serverskoj strani, a ukupan iznos se računa na
     * osnovu stavki porudžbine.
     *
     * @param dto DTO objekat sa podacima o porudžbini
     * @return kreirana porudžbina u obliku DTO objekta
     * @throws Exception ukoliko korisnik ili neka od knjiga ne postoje
     */
    @Transactional
    public PorudzbinaDto create(PorudzbinaDto dto) throws Exception {
        if (dto.getKorisnikId() == null || dto.getStavke() == null || dto.getStavke().isEmpty()) {
            throw new IllegalArgumentException("korisnik_id i stavke su obavezni");
        }

        Korisnik k = korisnikRepo.findById(dto.getKorisnikId());
        if (k == null) {
            throw new Exception("Korisnik ne postoji");
        }

        Porudzbina p = new Porudzbina();
        p.setKorisnik(k);
        p.setDatum(java.time.LocalDateTime.now(ZoneId.of("Europe/Belgrade")));
        p.setUkupanIznos(0d);

        double suma = 0d;

        for (var s : dto.getStavke()) {
            Knjiga knj = knjigaRepo.findById(s.getKnjigaId());
            if (knj == null) {
                throw new Exception("Knjiga ne postoji: id=" + s.getKnjigaId());
            }

            StavkaPorudzbine ps = new StavkaPorudzbine();
            ps.setPorudzbina(p);
            ps.setKnjiga(knj);
            ps.setKolicina(s.getKolicina() != null ? s.getKolicina() : 1);
            ps.setCenaK(s.getCena() != null ? s.getCena() : knj.getCena());

            p.getStavke().add(ps);

            suma += ps.getCenaK() * ps.getKolicina();
        }

        p.setUkupanIznos(suma);

        repo.save(p);
        return mapper.toDo(p);
    }

    /**
     * Ažurira postojeću porudžbinu.
     *
     * Datum porudžbine se ne preuzima iz DTO objekta, već ostaje kontrolisan na
     * serverskoj strani. Moguće je promeniti korisnika i kompletno zameniti
     * stavke porudžbine.
     *
     * @param id identifikator porudžbine koja se ažurira
     * @param dto DTO objekat sa izmenjenim podacima o porudžbini
     * @return ažurirana porudžbina u obliku DTO objekta
     * @throws Exception ukoliko porudžbina, korisnik ili knjiga ne postoje
     */
    @Transactional
    public PorudzbinaDto update(Long id, PorudzbinaDto dto) throws Exception {
        Porudzbina p = repo.findById(id);
        if (p == null) {
            throw new Exception("Porudžbina ne postoji");
        }

        if (dto.getKorisnikId() != null) {
            Korisnik k = korisnikRepo.findById(dto.getKorisnikId());
            if (k == null) {
                throw new Exception("Korisnik ne postoji");
            }
            p.setKorisnik(k);
        }

        if (dto.getStavke() != null) {
            p.getStavke().clear();

            double suma = 0d;

            for (var s : dto.getStavke()) {
                Knjiga knj = knjigaRepo.findById(s.getKnjigaId());
                if (knj == null) {
                    throw new Exception("Knjiga ne postoji: id=" + s.getKnjigaId());
                }

                StavkaPorudzbine ps = new StavkaPorudzbine();
                ps.setPorudzbina(p);
                ps.setKnjiga(knj);
                ps.setKolicina(s.getKolicina() != null ? s.getKolicina() : 1);
                ps.setCenaK(s.getCena() != null ? s.getCena() : knj.getCena());

                p.getStavke().add(ps);

                suma += ps.getCenaK() * ps.getKolicina();
            }

            p.setUkupanIznos(suma);
        }

        repo.save(p);
        return mapper.toDo(p);
    }

    /**
     * Briše porudžbinu prema identifikatoru.
     *
     * @param id identifikator porudžbine koja se briše
     */
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    /**
     * Pronalazi sve porudžbine određenog korisnika.
     *
     * @param korisnikId identifikator korisnika
     * @return lista porudžbina korisnika u obliku DTO objekata
     */
    public List<PorudzbinaDto> findByKorisnik(Long korisnikId) {
        return repo.findByKorisnikId(korisnikId)
                .stream()
                .map(mapper::toDo)
                .toList();
    }

    /**
     * Menja status porudžbine.
     *
     * Ako se status menja u OBRADJENA, sistem skida zalihe za knjige iz
     * porudžbine. Ako je porudžbina već obrađena, metoda ne ponavlja skidanje
     * zaliha.
     *
     * @param id identifikator porudžbine
     * @param noviStatus novi status porudžbine
     * @return porudžbina sa izmenjenim statusom u obliku DTO objekta
     * @throws Exception ukoliko nema dovoljno zaliha ili porudžbina ne postoji
     */
    @Transactional
    public PorudzbinaDto promeniStatus(Long id, String noviStatus) throws Exception {
        Porudzbina p = repo.findById(id);
        String stari = p.getStatus();

        String target = (noviStatus == null ? "" : noviStatus.trim()).toUpperCase();

        if (stari != null && stari.equalsIgnoreCase(STATUS_OBRADJENA) && target.equals(STATUS_OBRADJENA)) {
            return mapper.toDo(p);
        }

        if (target.equals(STATUS_OBRADJENA)) {
            skiniZaliheZa(p);
        }

        p.setStatus(target);
        repo.save(p);

        return mapper.toDo(p);
    }

    /**
     * Skida zalihe za sve stavke iz porudžbine.
     *
     * Za svaku stavku proverava dostupnost knjige u knjižarama i smanjuje
     * količinu na stanju. Ako nema dovoljno primeraka, baca se izuzetak.
     *
     * @param p porudžbina za koju se skidaju zalihe
     * @throws Exception ukoliko nema dovoljno knjiga na stanju
     */
    @Transactional
    protected void skiniZaliheZa(Porudzbina p) throws Exception {
        if (p.getStavke() == null || p.getStavke().isEmpty()) {
            return;
        }

        for (StavkaPorudzbine s : p.getStavke()) {
            skiniZalihuZaStavku(s);
        }
    }

    /**
     * Skida zalihu za jednu stavku porudžbine.
     *
     * @param s stavka porudžbine za koju se skida zaliha
     * @throws Exception ukoliko nema dovoljno knjiga na stanju
     */
    private void skiniZalihuZaStavku(StavkaPorudzbine s) throws Exception {
        if (s.getKnjiga() == null) {
            return;
        }

        int potrebno = s.getKolicina() == null ? 0 : s.getKolicina();
        if (potrebno <= 0) {
            return;
        }

        var redovi = kkRepo.findByKnjigaIdForUpdate(s.getKnjiga().getId());
        int skinuto = skiniSaRedova(redovi, potrebno);

        if (skinuto < potrebno) {
            throw new Exception("Nema dovoljno na stanju za: " + s.getKnjiga().getNaziv());
        }
    }

    /**
     * Skida potrebnu količinu sa raspoloživih redova dostupnosti.
     *
     * @param redovi redovi dostupnosti knjige po knjižarama
     * @param potrebno potrebna količina za skidanje
     * @return ukupno skinuta količina
     */
    private int skiniSaRedova(List<KnjigaKnjizara> redovi, int potrebno) {
        int skinuto = 0;

        for (var kk : redovi) {
            if (skinuto >= potrebno) {
                break;
            }

            int naStanju = kk.getKolicina() == null ? 0 : kk.getKolicina();
            int uzmi = Math.min(potrebno - skinuto, naStanju);

            if (uzmi > 0) {
                kk.setKolicina(naStanju - uzmi);
                kkRepo.save(kk);
                skinuto += uzmi;
            }
        }

        return skinuto;
    }
}