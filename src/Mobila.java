/**
 * Clasa ce creeaza instante pentru memorarea obiectelor de mobila.
 */
public class Mobila extends Produs {
    /**
     * Constructor fara parametri.
     */
    public Mobila() {
        super(null);
    }

    /**
     * Tipul de mobila.
     */
    private String tip;
    /**
     * Materialul.
     */
    private String material;

    /**
     * Metoda toString.
     * @return String-urile concatenate ale campurilor obiectului curent.
     */
    @Override
    public String toString() {
        return "Mobila{" + super.toString() +
                ", tip='" + tip + '\'' +
                ", material='" + material + '\'' +
                '}';
    }

    /**
     * Builder style setter.
     * @param tip Tipul la care se seteaza campul tip.
     * @return Obiectul curent.
     */
    public Mobila setTip(String tip) {
        this.tip = tip;
        return this;
    }

    /**
     * Builder style setter.
     * @param material Materialul.
     * @return Obiectul curent.
     */
    public Mobila setMaterial(String material) {
        this.material = material;
        return this;
    }
}
