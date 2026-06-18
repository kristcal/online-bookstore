package njt.mavenproject2.mapper.impl;

import njt.mavenproject2.dto.impl.KnjizaraDto;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper za konverziju između entiteta Knjizara i DTO objekta KnjizaraDto.
 *
 * @author Korisnik
 */
@Component
public class KnjizaraMapper implements DtoEntityMapper<KnjizaraDto, Knjizara> {

    /**
     * Konvertuje entitet Knjizara u DTO objekat.
     *
     * @param e entitet knjižare
     * @return DTO objekat knjižare
     */
    @Override
    public KnjizaraDto toDo(Knjizara e) {
        if (e == null) {
            return null;
        }

        return new KnjizaraDto(
                e.getId(),
                e.getNaziv(),
                e.getLokacija(),
                e.getKontakt()
        );
    }

    /**
     * Konvertuje DTO objekat KnjizaraDto u entitet Knjizara.
     *
     * @param t DTO objekat knjižare
     * @return entitet knjižare
     */
    @Override
    public Knjizara toEntity(KnjizaraDto t) {
        if (t == null) {
            return null;
        }

        return new Knjizara(
                t.getId(),
                t.getNaziv(),
                t.getLokacija(),
                t.getKontakt()
        );
    }
}