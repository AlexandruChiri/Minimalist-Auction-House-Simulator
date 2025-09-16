/**
 * Clasa pentru produse.
 */
public abstract class Produs {
    /**
     * Creeaza un proodus fara a initializa campul id, motiv pentru care este private.
     */
    private Produs() {

    }

    /**
     * Creeaza un produs si ii initializeaza campul id.
     * @param nulObject Obiect neimportant. Face diferenta fata de constructorul fara parametri.
     */
    public Produs(Object nulObject){
        this.id = new Id();
    }

    /**
     * Metoda toString.
     * @return String-ul valorilor concatenate ale produsului.
     */
    @Override
    public String toString() {
        return  "id=" + id +
                ", nume='" + nume + '\'' +
                ", pretVanzare=" + pretVanzare +
                ", pretMinim=" + pretMinim +
                ", an=" + an
                ;
    }

    /**
     * Metoda custom toString cu scopul vizualizarii unui produs de catre un client, pretul fiind cel pe care
     * clientul l-ar plati, nu cel fara comision.
     * @param caller Clientul care vizualizeaza produsul (de regula in lista de produse).
     * @return String-ul valorilor concatenate ale produsului.
     */
    public String toString(Client caller) {
        return  "id=" + id +
                ", nume='" + nume + '\'' +
                ", pretVanzare=" + pretVanzare +
                ", pretMinim=" + MetodeSpeciale.cuComision(caller, pretMinim) +
                ", an=" + an
                ;
    }

    /**
     * Identificatorul unic al produsului.
     */
    private Id id;
    /**
     * Numele produsului.
     */
    private String nume;
    /**
     * Pretul cu care produsul a fost vandut (dupa terminarea unei licitatii pentru acesta).
     */
    private double pretVanzare;
    /**
     * Pretul minim ca care produsul poate fi vandut.
     */
    private double pretMinim;
    /**
     * Cerinta -- no description.
     */
    private int an;

    /**
     * Constructor cu parametri.
     * @param pretVanzare Valoarea cu care se initializeaza pretVanzare.
     * @param pretMinim Valoarea cu care se initializeaza pretMinim.
     * @param an Valoarea cu care se initializeaza an.
     */
    public Produs(double pretVanzare, double pretMinim, int an) {
        this.id = new Id();
        this.pretVanzare = pretVanzare;
        this.pretMinim = pretMinim;
        this.an = an;
    }

    /**
     * Returneaza un produs cu id-ul specificat din lista specificata.
     * @param id Id-ul produsului cautat.
     * @param list Lista in care este cautat produsul.
     * @return Produsul gasit sau null in caz ca nu exista produs cu id-ul specificat in lista.
     */
    public static Produs withId(Id id, MyArrayList<Produs> list){
        Produs produs = new Produs() {};
        produs.id = id;
        return list.getElem(produs);
    }

    /**
     * Builder type setter.
     * @param nume Stringul la care se seteaza nume.
     * @return Produsul curent.
     */
    public Produs setNume(String nume) {
        this.nume = nume;
        return this;
    }

    /**
     * Getter pentru id.
     * @return Id-ul produsului.
     */
    public Id getId() {
        return id;
    }

    /**
     * Builder type setter pentru pretVanzare.
     * @param pretVanzare Pretul la care se seteaza pretul de vanzare.
     * @return Produsul curent.
     */
    public Produs setPretVanzare(double pretVanzare) {
        this.pretVanzare = pretVanzare;
        return this;
    }

    /**
     * Getter pentru pretMinim.
     * @return Valoarea pretului minim.
     */
    public double getPretMinim() {
        return pretMinim;
    }

    /**
     * Builder style setter pentru pret minim.
     * @param pretMinim Pretul la care este setat pretMinim.
     * @return Produsul curent.
     */
    public Produs setPretMinim(double pretMinim) {
        this.pretMinim = pretMinim;
        return this;
    }

    /**
     * Getter pentru an.
     * @return Valoarea campului an.
     */
    public int getAn() {
        return an;
    }

    /**
     * Builder style setter pentru an.
     * @param an Anul la care este setat campul an.
     * @return Produsul curent.
     */
    public Produs setAn(int an) {
        this.an = an;
        return this;
    }

    /**
     * Metoda equals compara produsul curent cu obiectul primit ca parametru.
     * @param o Produsul cu care este comparat produsul curent.
     * @return true daca produsele sunt considerate egale, altfel false.
     */
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null){
            return false;
        }
        if(!(o instanceof Produs)){
            return false;
        }
        Produs produs = (Produs)o;
        return this.id.equals(produs.id);
    }
}
