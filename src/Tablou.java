/**
 * Tipurile de culori.
 */
enum Culori {
    /**
     * Tipul de culoare ulei.
     */
    ulei,
    /**
     * Tipul de culoare tempera.
     */
    tempera,
    /**
     * Tipul de culoare acrilic.
     */
    acrilic;

    /**
     * Converteste din String in Culori.
     * @param str String-ul convertit.
     * @return Valoarea corespunzatoarea sau null daca String-ul nu este bun.
     */
    public static Culori parseCulori(String str){
        try {
            return valueOf(str.toLowerCase());
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}

/**
 * Clasa pentru memorarea tablourilor.
 */
public class Tablou extends Produs {

    /**
     * Constructor fara parametri.
     */
    public Tablou() {
        super(null);
    }

    /**
     * Numele pictorului.
     */
    private String numePictor;
    /**
     * Tipul culorilor.
     */
    private Culori culori;

    /**
     * Metoda toString.
     * @return String-urile concatenate ale campurilor tabloului.
     */
    @Override
    public String toString() {
        return "Tablou{" +
                super.toString() +
                ", numePictor='" + numePictor + '\'' +
                ", culori=" + culori +
                '}';
    }

    /**
     * Builder style setter.
     * @param numePictor Numele pictorului.
     * @return Tabloul curent.
     */
    public Tablou setNumePictor(String numePictor) {
        this.numePictor = numePictor;
        return this;
    }

    /**
     * Builder style setter.
     * @param culori Valoarea la care se seteaza culorile.
     * @return Tabloul curent.
     */
    public Tablou setCulori(Culori culori) {
        this.culori = culori;
        return this;
    }
}
