/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.dto.auth;

/**
 * DTO koji predstavlja odgovor nakon uspešne prijave korisnika.
 *
 * Sadrži JWT token i osnovne podatke o prijavljenom korisniku.
 *
 * @param token JWT token za autentifikaciju
 * @param id identifikator korisnika
 * @param email email adresa korisnika
 * @param ime ime korisnika
 * @param prezime prezime korisnika
 * @param uloga uloga korisnika u sistemu
 *
 * @author Korisnik
 */
public record LoginRes(String token, Long id, String email, String ime, String prezime, String uloga) {}

