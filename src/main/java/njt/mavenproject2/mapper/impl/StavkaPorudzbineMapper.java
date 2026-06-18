package njt.mavenproject2.mapper.impl;

import njt.mavenproject2.dto.impl.StavkaPorudzbineDto;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;
import njt.mavenproject2.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper za konverziju između entiteta StavkaPorudzbine i DTO objekta
 * StavkaPorudzbineDto.
 *
 * @author Korisnik
 */
@Component
public class StavkaPorudzbineMapper implements DtoEntityMapper<StavkaPorudzbineDto, StavkaPorudzbine> {

    /**
     * Konvertuje entitet StavkaPorudzbine u DTO objekat.
     *
     * @param e entitet stavke porudžbine
     * @return DTO objekat stavke porudžbine
     */
    @Override
    public StavkaPorudzbineDto toDo(StavkaPorudzbine e) {
        if (e == null) {
            return null;
        }

        Long knjigaId = e.getKnjiga() != null ? e.getKnjiga().getId() : null;

        return new StavkaPorudzbineDto(
                e.getId(),
                e.getRb(),
                knjigaId,
                e.getKolicina(),
                e.getCenaK()
        );
    }

    /**
     * Konvertuje DTO objekat StavkaPorudzbineDto u entitet StavkaPorudzbine.
     *
     * Porudžbina i knjiga postavljaju se u servisnom sloju.
     *
     * @param t DTO objekat stavke porudžbine
     * @return entitet stavke porudžbine
     */
    @Override
    public StavkaPorudzbine toEntity(StavkaPorudzbineDto t) {
        if (t == null) {
            return null;
        }

        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(t.getId());
        s.setRb(t.getRb());
        s.setKolicina(t.getKolicina());
        s.setCenaK(t.getCenaK());

        return s;
    }
}