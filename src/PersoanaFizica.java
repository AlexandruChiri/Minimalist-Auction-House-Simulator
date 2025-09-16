/**
 * Clasa pentru persoana fizica.
 */
public class PersoanaFizica extends Client {
    /**
     * Constructor fara parametri.
     */
    public PersoanaFizica() {
        super(null);
    }

    /**
     * Data de nastere.
     */
    private Date dataNastere;

    /**
     * Metoda toString.
     * @return String-urile concatenate ale valorilor campurilor.
     */
    @Override
    public String
    toString() {
        return "PersoanaFizica{" +
                super.toString() +
                ", dataNastere=" + dataNastere +
                '}';
    }

    /**
     * Builder style setter.
     * @param dataNastere Data la care se seteaza data de nastere.
     * @return Clientul curent.
     */
    public PersoanaFizica setDataNastere(Date dataNastere) {
        this.dataNastere = dataNastere;
        return this;
    }
}
