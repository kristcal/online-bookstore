/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.dto.impl;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import njt.mavenproject2.dto.Dto;
/**
 * DTO koji predstavlja korisnika sistema.
 *
 * Koristi se za prenos podataka o korisnicima između klijentskog i
 * serverskog dela aplikacije.
 *
 * Sadrži osnovne podatke o korisniku potrebne za registraciju,
 * prijavu i upravljanje korisničkim nalogom.
 *
 * @author Korisnik
 */
public class KorisnikDto implements Dto {

    /**
     * Jedinstveni identifikator korisnika.
     */
    private Long id;

    /**
     * Ime korisnika.
     */
    @NotBlank(message = "Ime je obavezno")
    @Size(max = 60, message = "Ime ne može biti duže od 60 karaktera")
    private String ime;

    /**
     * Prezime korisnika.
     */
    @NotBlank(message = "Prezime je obavezno")
    @Size(max = 60, message = "Prezime ne može biti duže od 60 karaktera")
    private String prezime;

    /**
     * Email adresa korisnika.
     */
    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije ispravan")
    @Size(max = 120, message = "Email ne može biti duži od 120 karaktera")
    private String email;

    /**
     * Lozinka korisnika.
     */
    @NotBlank(message = "Lozinka je obavezna")
    @Size(min = 6, max = 100, message = "Lozinka mora imati 6-100 karaktera")
    private String lozinka;

    /**
     * Kreira prazan DTO objekat korisnika.
     */
    public KorisnikDto() {
    }

    /**
     * Kreira DTO objekat korisnika sa zadatim podacima.
     *
     * @param id identifikator korisnika
     * @param ime ime korisnika
     * @param prezime prezime korisnika
     * @param email email adresa korisnika
     * @param lozinka lozinka korisnika
     */
    public KorisnikDto(Long id, String ime, String prezime, String email, String lozinka) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
    }

    /**
     * Vraća identifikator korisnika.
     *
     * @return identifikator korisnika
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator korisnika.
     *
     * @param id identifikator korisnika
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća ime korisnika.
     *
     * @return ime korisnika
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime korisnika.
     *
     * @param ime ime korisnika
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * Vraća prezime korisnika.
     *
     * @return prezime korisnika
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime korisnika.
     *
     * @param prezime prezime korisnika
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * Vraća email adresu korisnika.
     *
     * @return email adresa korisnika
     */
    public String getEmail() {
        return email;
    }

    /**
     * Postavlja email adresu korisnika.
     *
     * @param email email adresa korisnika
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Vraća lozinku korisnika.
     *
     * @return lozinka korisnika
     */
    public String getLozinka() {
        return lozinka;
    }

    /**
     * Postavlja lozinku korisnika.
     *
     * @param lozinka lozinka korisnika
     */
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}