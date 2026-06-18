package njt.mavenproject2.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import njt.mavenproject2.dto.Dto;

/**
 * DTO koji predstavlja žanr knjige.
 *
 * Koristi se za prenos podataka o žanrovima između klijentskog
 * i serverskog dela aplikacije.
 *
 * @author Korisnik
 */
public class ZanrDto implements Dto {

    /**
     * Jedinstveni identifikator žanra.
     */
    private Long id;

    /**
     * Naziv žanra.
     */
    @NotBlank(message = "Naziv žanra je obavezan")
    @Size(max = 80, message = "Naziv žanra ne može biti duži od 80 karaktera")
    private String naziv;

    /**
     * Kreira prazan DTO objekat žanra.
     */
    public ZanrDto() {
    }

    /**
     * Kreira DTO objekat žanra sa zadatim podacima.
     *
     * @param id identifikator žanra
     * @param naziv naziv žanra
     */
    public ZanrDto(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    /**
     * Vraća identifikator žanra.
     *
     * @return identifikator žanra
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator žanra.
     *
     * @param id identifikator žanra
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća naziv žanra.
     *
     * @return naziv žanra
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv žanra.
     *
     * @param naziv naziv žanra
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}