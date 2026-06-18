package njt.mavenproject2.mapper.impl;

import njt.mavenproject2.dto.impl.PorudzbinaDto;
import njt.mavenproject2.entity.impl.Porudzbina;
import njt.mavenproject2.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper za konverziju između entiteta Porudzbina i DTO objekta PorudzbinaDto.
 *
 * @author Korisnik
 */
@Component
public class PorudzbinaMapper implements DtoEntityMapper<PorudzbinaDto, Porudzbina> {

    /**
     * Konvertuje entitet Porudzbina u DTO objekat.
     *
     * @param e entitet porudžbine
     * @return DTO objekat porudžbine
     */
    @Override
    public PorudzbinaDto toDo(Porudzbina e) {
        if (e == null) {
            return null;
        }

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(e.getId());
        dto.setDatum(e.getDatum());
        dto.setUkupanIznos(e.getUkupanIznos());
        dto.setStatus(e.getStatus());

        if (e.getKorisnik() != null) {
            dto.setKorisnikId(e.getKorisnik().getId());
        }

        return dto;
    }

    /**
     * Konvertuje DTO objekat PorudzbinaDto u entitet Porudzbina.
     *
     * Korisnik i stavke porudžbine postavljaju se u servisnom sloju.
     *
     * @param dto DTO objekat porudžbine
     * @return entitet porudžbine
     */
    @Override
    public Porudzbina toEntity(PorudzbinaDto dto) {
        if (dto == null) {
            return null;
        }

        Porudzbina e = new Porudzbina();
        e.setId(dto.getId());
        e.setDatum(dto.getDatum());
        e.setUkupanIznos(dto.getUkupanIznos());
        e.setStatus(dto.getStatus() != null ? dto.getStatus() : "KREIRANA");

        return e;
    }
}