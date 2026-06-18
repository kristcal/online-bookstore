/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package njt.mavenproject2.repository;

import java.util.List;

/**
 * Generički interfejs repozitorijuma koji definiše osnovne CRUD operacije.
 *
 * Ovaj interfejs predstavlja osnovu za rad sa entitetima u sistemu i
 * omogućava pronalaženje, čuvanje i brisanje podataka nezavisno od
 * konkretnog tipa entiteta.
 *
 * @param <E> tip entiteta
 * @param <ID> tip identifikatora entiteta
 *
 * @author Korisnik
 */
public interface MyAppRepository<E, ID> {
    
	/**
     * Vraća listu svih entiteta.
     *
     * @return lista svih entiteta
     */
    List<E> findAll();
    
    /**
     * Pronalazi entitet prema identifikatoru.
     *
     * @param id identifikator entiteta
     * @return pronađeni entitet
     * @throws Exception ukoliko entitet sa zadatim identifikatorom ne postoji
     */
    E findById(ID id) throws Exception;
    
    /**
     * Čuva novi entitet ili ažurira postojeći.
     *
     * @param entity entitet koji se čuva
     */
    void save(E entity);
    
    /**
     * Briše entitet prema identifikatoru.
     *
     * @param id identifikator entiteta
     */
    void deleteById(ID id);
}
