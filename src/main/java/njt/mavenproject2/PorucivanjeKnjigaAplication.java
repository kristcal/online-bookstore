/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Glavna klasa Spring Boot aplikacije za online poručivanje knjiga.
 *
 * Predstavlja ulaznu tačku aplikacije i pokreće Spring Boot
 * kontejner zajedno sa svim definisanim komponentama,
 * kontrolerima, servisima, repozitorijumima i konfiguracijama.
 *
 * @author Korisnik
 */
@SpringBootApplication
public class PorucivanjeKnjigaAplication {
	
	/**
     * Pokreće aplikaciju.
     *
     * @param args argumenti komandne linije
     */
    public static void main(String[] args) {
        SpringApplication.run(PorucivanjeKnjigaAplication.class, args);
    }
}
