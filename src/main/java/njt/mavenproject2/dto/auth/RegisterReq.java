/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.dto.auth;

/**
 * DTO koji predstavlja zahtev za registraciju novog korisnika.
 *
 * Sadrži osnovne podatke potrebne za kreiranje korisničkog naloga.
 *
 * @param ime ime korisnika
 * @param prezime prezime korisnika
 * @param email email adresa korisnika
 * @param password lozinka korisnika
 *
 * @author Korisnik
 */
public record RegisterReq(String ime, String prezime, String email, String password) {}