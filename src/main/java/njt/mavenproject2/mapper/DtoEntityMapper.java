package njt.mavenproject2.mapper;

/**
 * Generički interfejs za konverziju između DTO objekata i entiteta.
 *
 * Omogućava dvosmerno mapiranje između DTO klase i odgovarajućeg
 * domenskog entiteta.
 *
 * @param <T> tip DTO objekta
 * @param <E> tip entiteta
 *
 * @author Korisnik
 */
public interface DtoEntityMapper<T, E> {

    /**
     * Konvertuje entitet u DTO objekat.
     *
     * @param e entitet koji se konvertuje
     * @return DTO objekat
     */
    T toDo(E e);

    /**
     * Konvertuje DTO objekat u entitet.
     *
     * @param t DTO objekat koji se konvertuje
     * @return entitet
     */
    E toEntity(T t);
}