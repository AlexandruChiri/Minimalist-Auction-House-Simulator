/**
 * Clasa pentru tipul angajat.
 */
public abstract class Angajat {
    /**
     * Metoda toString.
     * @return String-ul care descrie valorile campurilor angajatolui.
     */
    @Override
    public String toString() {
        return "id=" + id;
    }

    /**
     * Identificatorul unic al angajatului.
     */
    private final Id id;

    /**
     * Constructor fara parametri.
     */
    Angajat(){
        id = new Id();
    }
}