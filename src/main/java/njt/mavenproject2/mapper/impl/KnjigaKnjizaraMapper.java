package njt.mavenproject2.mapper.impl;

import njt.mavenproject2.dto.impl.KnjigaKnjizaraDto;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper za konverziju između entiteta KnjigaKnjizara i DTO objekta
 * KnjigaKnjizaraDto.
 *
 * @author Korisnik
 */
@Component
public class KnjigaKnjizaraMapper implements DtoEntityMapper<KnjigaKnjizaraDto, KnjigaKnjizara> {

    /**
     * Konvertuje entitet KnjigaKnjizara u DTO objekat.
     *
     * @param e entitet veze između knjige i knjižare
     * @return DTO objekat veze između knjige i knjižare
     */
    @Override
    public KnjigaKnjizaraDto toDo(KnjigaKnjizara e) {
        if (e == null) {
            return null;
        }

        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setId(e.getId());
        dto.setKnjizaraId(
                e.getKnjizara() != null
                        ? e.getKnjizara().getId()
                        : null
        );
        dto.setKolicina(e.getKolicina());

        return dto;
    }

    /**
     * Konvertuje DTO objekat KnjigaKnjizaraDto u entitet KnjigaKnjizara.
     *
     * Entiteti Knjiga i Knjižara se postavljaju u servisnom sloju.
     *
     * @param t DTO objekat veze između knjige i knjižare
     * @return entitet veze između knjige i knjižare
     */
    @Override
    public KnjigaKnjizara toEntity(KnjigaKnjizaraDto t) {
        if (t == null) {
            return null;
        }

        KnjigaKnjizara e = new KnjigaKnjizara();
        e.setId(t.getId());
        e.setKolicina(t.getKolicina());

        return e;
    }
}