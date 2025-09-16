/**
 * Clasa pentru produsele bijuteri.
 */
public class Bijuterie extends Produs {

    /**
     * Creeaza un obiect bijuterie cu id creat in mod normal.
     */
    public Bijuterie() {
        super(null);
    }

    /**
     * Materialul.
     */
    private String material;
    /**
     * Piatra pretioasa true/false.
     */
    private boolean piatraPretioasa;

    /**
     * Metoda toString.
     * @return String-ul bijuteriei.
     */
    @Override
    public String toString() {
        return "Bijuterie{" +
                super.toString() +
                ", material='" + material + '\'' +
                ", piatraPretioasa=" + piatraPretioasa +
                '}';
    }

    /**
     * Builder style setter.
     * @param material String-ul la care se seteaza campul material.
     * @return Bijuteria curenta.
     */
    public Bijuterie setMaterial(String material) {
        this.material = material;
        return this;
    }

    /**
     * Builder style setter.
     * @param piatraPretioasa Valoarea de adevar la care se seteaza piatraPretioasa.
     * @return Bijuteria curenta.
     */
    public Bijuterie setPiatraPretioasa(boolean piatraPretioasa) {
        this.piatraPretioasa = piatraPretioasa;
        return this;
    }
}
