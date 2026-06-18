package njt.mavenproject2.mapper.impl;

import njt.mavenproject2.dto.impl.ZanrDto;
import njt.mavenproject2.entity.impl.Zanr;
import njt.mavenproject2.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper za konverziju između entiteta Zanr i DTO objekta ZanrDto.
 *
 * @author Korisnik
 */
@Component
public class ZanrMapper implements DtoEntityMapper<ZanrDto, Zanr> {

    /**
     * Konvertuje entitet Zanr u DTO objekat.
     *
     * @param e entitet žanra
     * @return DTO objekat žanra
     */
    @Override
    public ZanrDto toDo(Zanr e) {
        if (e == null) {
            return null;
        }

        return new ZanrDto(
                e.getId(),
                e.getNaziv()
        );
    }

    /**
     * Konvertuje DTO objekat ZanrDto u entitet Zanr.
     *
     * @param t DTO objekat žanra
     * @return entitet žanra
     */
    @Override
    public Zanr toEntity(ZanrDto t) {
        if (t == null) {
            return null;
        }

        Zanr e = new Zanr();
        e.setId(t.getId());
        e.setNaziv(t.getNaziv());

        return e;
    }
}