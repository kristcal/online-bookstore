package njt.mavenproject2.mapper.impl;

import njt.mavenproject2.dto.impl.AutorDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper za konverziju između entiteta Autor i DTO objekta AutorDto.
 *
 * @author Korisnik
 */
@Component
public class AutorMapper implements DtoEntityMapper<AutorDto, Autor> {

    /**
     * Konvertuje entitet Autor u DTO objekat.
     *
     * @param e entitet autora
     * @return DTO objekat autora
     */
    @Override
    public AutorDto toDo(Autor e) {
        if (e == null) {
            return null;
        }

        return new AutorDto(
                e.getId(),
                e.getIme(),
                e.getPrezime()
        );
    }

    /**
     * Konvertuje DTO objekat AutorDto u entitet Autor.
     *
     * @param t DTO objekat autora
     * @return entitet autora
     */
    @Override
    public Autor toEntity(AutorDto t) {
        if (t == null) {
            return null;
        }

        Autor e = new Autor();
        e.setId(t.getId());
        e.setIme(t.getIme());
        e.setPrezime(t.getPrezime());

        return e;
    }
}