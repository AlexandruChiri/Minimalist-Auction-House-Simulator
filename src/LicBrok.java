/**
 * Clasa de dependenta intre o liictatie si un broker.
 */
public class LicBrok {

    /**
     * Licitatia.
     */
    private final Licitatie licitatie;
    /**
     * Broker-ul.
     */
    private final Broker broker;

    /**
     * Constructor cu parametri.
     * @param licitatie Licitatia.
     * @param broker Broker-ul.
     */
    public LicBrok(Licitatie licitatie, Broker broker) {
        this.licitatie = licitatie;
        this.broker = broker;
    }

    /**
     * Cauta o dependenta broker-licitatie intr-o lista de astfel de dependente.
     * @param licitatie Licitatia dependentei cautate.
     * @param list Lista de dependente.
     * @return Dependenta gasita sau null in caz de esec.
     */
    public static LicBrok withLicitatie(Licitatie licitatie, MyArrayList<LicBrok> list){
        return list.getElem(new LicBrok(licitatie, null));
    }

    /**
     * Metoda toString.
     * @return String-urile licitatiei si al broker-ului concatenate intr-unul.
     */
    @Override
    public String toString() {
        return "LicBrok{" +
                ", licitatie=" + licitatie +
                '}';
    }

    /**
     * Metoda equals.
     * @param o Obiectul cu care se verifica egalitatea dependentei curente.
     * @return true in caz de egalitate, altfel false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicBrok)) return false;
        LicBrok licBrok = (LicBrok) o;
        return licitatie.equals(licBrok.licitatie);
    }

    /**
     * Getter pentru licitatie.
     * @return Licitatia dependentei.
     */
    public Licitatie getLicitatie() {
        return licitatie;
    }

    /**
     * Getter pentru broker.
     * @return Valoarea campului broker.
     */
    public Broker getBroker() {
        return broker;
    }

    /**
     * Creeaza o dependenta intre o licitatie si un broker pentru clientul client.
     * @param parametri Descrierea.
     * @param client Client-ul pentru care se creeaza dependenta.
     * @return Dependenta creata.
     */
    public static LicBrok licitatieSiBrokerDin(String[] parametri, Client client){
        Id idProdus;
        // Se retine id-ul produsului.
        idProdus = Id.parseId(parametri[3]);
        if(idProdus == null){
            return null;
        }
        // Se retine licitatia.
        Licitatie licitatie = Licitatie.withId(idProdus, CasaDeLicitatii.getInstance().getLicitatii());
        if(licitatie == null){
            System.out.println("ERROR!!! There is no auction with the product id '" + idProdus + "'. Nothing was " +
                    "done.");
            return null;
        }
        // Se cauta dependenta.
        LicBrok licBrok = LicBrok.withLicitatie(licitatie, client.getLicitatii());
        if(licBrok == null){
            System.out.println("ERROR!!! This client is not taking part to this auction. Clients need to join before" +
                    " proposing a price.");
            return null;
        }
        // Se returneaza dependenta.
        return licBrok;
    }
}
