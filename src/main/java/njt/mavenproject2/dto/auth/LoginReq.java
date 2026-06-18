/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.dto.auth;

/**
 * DTO koji predstavlja zahtev za prijavu korisnika.
 *
 * Sadrži email adresu i lozinku koje korisnik unosi
 * prilikom prijavljivanja u sistem.
 *
 * @param email email adresa korisnika
 * @param password lozinka korisnika
 *
 * @author Korisnik
 */
public record LoginReq(String email, String password) {}
