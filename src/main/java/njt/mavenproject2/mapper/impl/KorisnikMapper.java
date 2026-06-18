package njt.mavenproject2.mapper.impl;

import njt.mavenproject2.dto.impl.KorisnikDto;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper za konverziju između entiteta Korisnik i DTO objekta KorisnikDto.
 *
 * @author Korisnik
 */
@Component
public class KorisnikMapper implements DtoEntityMapper<KorisnikDto, Korisnik> {

    /**
     * Konvertuje entitet Korisnik u DTO objekat.
     *
     * @param e entitet korisnika
     * @return DTO objekat korisnika
     */
    @Override
    public KorisnikDto toDo(Korisnik e) {
        if (e == null) {
            return null;
        }

        return new KorisnikDto(
                e.getId(),
                e.getIme(),
                e.getPrezime(),
                e.getEmail(),
                e.getLozinka()
        );
    }

    /**
     * Konvertuje DTO objekat KorisnikDto u entitet Korisnik.
     *
     * @param t DTO objekat korisnika
     * @return entitet korisnika
     */
    @Override
    public Korisnik toEntity(KorisnikDto t) {
        if (t == null) {
            return null;
        }

        Korisnik e = new Korisnik();
        e.setId(t.getId());
        e.setIme(t.getIme());
        e.setPrezime(t.getPrezime());
        e.setEmail(t.getEmail());
        e.setLozinka(t.getLozinka());

        return e;
    }
}