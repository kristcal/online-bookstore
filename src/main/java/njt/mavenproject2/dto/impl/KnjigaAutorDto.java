/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import njt.mavenproject2.dto.Dto;

/**
 * DTO koji predstavlja vezu između knjige i autora.
 *
 * Koristi se za prenos podataka o autorima određene knjige,
 * uključujući ulogu autora u nastanku knjige.
 *
 * @author Korisnik
 */
public class KnjigaAutorDto implements Dto {

    /**
     * Jedinstveni identifikator veze knjiga-autor.
     */
    private Long id;

    /**
     * Identifikator autora.
     */
    @NotNull(message = "autorId je obavezan")
    private Long autorId;

    /**
     * Uloga autora u nastanku knjige.
     */
    @NotBlank(message = "Uloga je obavezna")
    @Size(max = 50, message = "Uloga ne može biti duža od 50 karaktera")
    private String uloga;

    /**
     * Kreira prazan DTO objekat.
     */
    public KnjigaAutorDto() {
    }

    /**
     * Kreira DTO objekat sa zadatim podacima.
     *
     * @param id identifikator veze knjiga-autor
     * @param autorId identifikator autora
     * @param uloga uloga autora
     */
    public KnjigaAutorDto(Long id, Long autorId, String uloga) {
        this.id = id;
        this.autorId = autorId;
        this.uloga = uloga;
    }

    /**
     * Vraća identifikator veze.
     *
     * @return identifikator veze
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator veze.
     *
     * @param id identifikator veze
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća identifikator autora.
     *
     * @return identifikator autora
     */
    public Long getAutorId() {
        return autorId;
    }

    /**
     * Postavlja identifikator autora.
     *
     * @param autorId identifikator autora
     */
    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    /**
     * Vraća ulogu autora.
     *
     * @return uloga autora
     */
    public String getUloga() {
        return uloga;
    }

    /**
     * Postavlja ulogu autora.
     *
     * @param uloga uloga autora
     */
    public void setUloga(String uloga) {
        this.uloga = uloga;
    }
}