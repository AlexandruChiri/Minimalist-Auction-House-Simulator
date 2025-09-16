/**
 * Tipurile de compani.
 */
enum Companie{
    /**
     * Tipul de companie SRL.
     */
    SRL,
    /**
     * Tipul de companie SA.
     */
    SA;

    /**
     * Metoda ce face convesia din tipul de date String in tipul de date Companie.
     * @param str Sirul ce trebuie convertit.
     * @return Valoarea din tipul de date Companie corespunzatoare String-ului sir sau null daca sir nu corespunde
     * niciunei valori din acest tip de date.
     */
    public static Companie parseCompanie(String str){
        try{
            return valueOf(str.toUpperCase());
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}

/**
 * Clasa pentru memorarea persoanelor juridice.
 */
public class PersoanaJuridica extends Client{
    /**
     * Constructor fara parametri.
     */
    public PersoanaJuridica() {
        super(null);
    }

    /**
     * Capitalul social.
     */
    private double capitalSocial;
    /**
     * Tipul de companie.
     */
    private Companie companie;

    /**
     * Metoda toString.
     * @return String-urile concatenate ale persoanei juridice.
     */
    @Override
    public String toString() {
        return "PersoanaJuridica{" +
                super.toString() +
                ", capitalSocial=" + capitalSocial +
                ", companie=" + companie +
                '}';
    }

    /**
     * Builder style setter.
     * @param capitalSocial Valoarea la care se seteaza capitalul social.
     * @return Clientul curent.
     */
    public PersoanaJuridica setCapitalSocial(double capitalSocial) {
        this.capitalSocial = capitalSocial;
        return this;
    }

    /**
     * Builder style setter pentru companie.
     * @param companie Valoarea la care se seteaza campul tipului de companie.
     * @return Clientul curent.
     */
    public PersoanaJuridica setCompanie(Companie companie) {
        this.companie = companie;
        return this;
    }
}
