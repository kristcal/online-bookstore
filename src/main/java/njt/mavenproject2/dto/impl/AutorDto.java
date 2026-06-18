/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import njt.mavenproject2.dto.Dto;

/**
 * DTO koji predstavlja autora u sistemu.
 *
 * Koristi se za prenos podataka o autorima između klijentskog i
 * serverskog dela aplikacije.
 *
 * Sadrži identifikator autora, ime i prezime.
 *
 * @author Korisnik
 */
public class AutorDto implements Dto {

	/**
     * Jedinstveni identifikator autora.
     */
    private Long id;

    /**
     * Ime autora.
     */
    @NotBlank(message = "Ime je obavezno")
    @Size(max = 60, message = "Ime ne može biti duže od 60 karaktera")
    private String ime;

    /**
     * Prezime autora.
     */
    @NotBlank(message = "Prezime je obavezno")
    @Size(max = 60, message = "Prezime ne može biti duže od 60 karaktera")
    private String prezime;

    /**
     * Kreira prazan DTO objekat autora.
     */
    public AutorDto() {
    }

    /**
     * Kreira DTO objekat autora sa zadatim podacima.
     *
     * @param id identifikator autora
     * @param ime ime autora
     * @param prezime prezime autora
     */
    public AutorDto(Long id, String ime, String prezime) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
    }

    /**
     * Vraća identifikator autora.
     *
     * @return identifikator autora
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator autora.
     *
     * @param id identifikator autora
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća ime autora.
     *
     * @return ime autora
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime autora.
     *
     * @param ime ime autora
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * Vraća prezime autora.
     *
     * @return prezime autora
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime autora.
     *
     * @param prezime prezime autora
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
