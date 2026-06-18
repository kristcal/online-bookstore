package njt.mavenproject2.mapper.impl;

import njt.mavenproject2.dto.impl.KnjigaAutorDto;
import njt.mavenproject2.entity.impl.KnjigaAutor;
import njt.mavenproject2.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper za konverziju između entiteta KnjigaAutor i DTO objekta
 * KnjigaAutorDto.
 *
 * @author Korisnik
 */
@Component
public class KnjigaAutorMapper implements DtoEntityMapper<KnjigaAutorDto, KnjigaAutor> {

    /**
     * Konvertuje entitet KnjigaAutor u DTO objekat.
     *
     * @param e entitet veze između knjige i autora
     * @return DTO objekat veze između knjige i autora
     */
    @Override
    public KnjigaAutorDto toDo(KnjigaAutor e) {
        if (e == null) {
            return null;
        }

        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setId(e.getId());
        dto.setAutorId(
                e.getAutor() != null
                        ? e.getAutor().getId()
                        : null
        );
        dto.setUloga(e.getUloga());

        return dto;
    }

    /**
     * Konvertuje DTO objekat KnjigaAutorDto u entitet KnjigaAutor.
     *
     * Entiteti Knjiga i Autor se postavljaju u servisnom sloju.
     *
     * @param t DTO objekat veze između knjige i autora
     * @return entitet veze između knjige i autora
     */
    @Override
    public KnjigaAutor toEntity(KnjigaAutorDto t) {
        if (t == null) {
            return null;
        }

        KnjigaAutor e = new KnjigaAutor();
        e.setUloga(t.getUloga());

        return e;
    }
}