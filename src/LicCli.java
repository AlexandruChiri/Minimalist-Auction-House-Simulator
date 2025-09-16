/**
 * Dependenta client-licitatie (memorata in cadrul unei instante broker)
 */
public class LicCli {

    /**
     * Licitatia
     */
    private final Licitatie licitatie;
    /**
     * Clientul
     */
    private final Client client;
    /**
     * Suma maxima declarata de catre client la intrarea acestuia in licitatie.
     */
    private double sumaMaxima;

    /**
     * Constructor cu parametri.
     * @param licitatie Licitatia
     * @param client Clientul
     * @param sumaMaxima Suma maxima declarata de catre client la intrarea acestuia in licitatie.
     */
    public LicCli(Licitatie licitatie, Client client, double sumaMaxima){
        this.licitatie = licitatie;
        this.client = client;
        this.sumaMaxima = sumaMaxima;
    }

    /**
     * Constructor privat.
     * @param client Client
     * @param licitatie Licitatia
     */
    private LicCli(Client client, Licitatie licitatie){
        this.client = client;
        this.licitatie = licitatie;
    }

    /**
     * Getter pentru suma maxima.
     * @return Suma maxima.
     */
    public double getSumaMaxima(){
        return sumaMaxima;
    }

    /**
     * Cauta o dependenta client-licitatie cu un anume client si licitatie intr-o lista de astfel de dependente.
     * @param client Clientul dependentei cautate.
     * @param licitatie Licitatia dependentei cautate.
     * @param list Lista in care se face cautarea.
     * @return Dependenta gasita.
     */
    public static LicCli withClientLicitatie(Client client, Licitatie licitatie, MyArrayList<LicCli> list){
        return list.getElem(new LicCli(client, licitatie));
    }

    /**
     * Metoda toString.
     * @return String-urile concatenate ale client-ului si al licitatiei.
     */
    @Override
    public String toString() {
        return "LicCli{" +
                "idLicitatie" + licitatie.getIdProdus() +
                ", client=" + client +
                '}';
    }

    /**
     * Metoda equals verifica daca dependenta curenta este egala cu alta.
     * @param o Dependenta cu care se compara dependenta curenta.
     * @return true in caz afirmativ, altfel false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicCli)) return false;
        LicCli licCli = (LicCli) o;
        if (!licitatie.equals(licCli.licitatie)) return false;
        return client.equals(licCli.client);
    }
}
