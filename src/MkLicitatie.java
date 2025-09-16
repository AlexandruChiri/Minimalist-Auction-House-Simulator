/**
 * La executia unei instante clientul client solicita/intra in licitatia descrisa de parametri comenzii.
 */
public class MkLicitatie extends Thread {

    /**
     * Constructor cu parametri.
     * @param parametri Parametrii comenzii.
     * @param client Clientul care solicita licitatia.
     */
    public MkLicitatie(String[] parametri, Client client) {
        super();
        this.parametri = parametri;
        this.client = client;
    }

    /**
     * Parametrii comenzii.
     */
    private final String[] parametri;
    /**
     * Clientul care solicita licitatia.
     */
    private final Client client;

    @Override
    public void run() {
        // Prea putini parametri? Nu se face nimic.
        if(parametri.length < 5){
            System.out.println("WARNING!!! Not enough arguments for auction. Nothing was done.");
            return;
        }
        Id id;
        // Se incearca creerea unui UUID dupa al 4-lea argument introdus de la tastatura.
        id = Id.parseId(parametri[3]);
        if(id == null){
            return;
        }
        // Se memoreaza suma maxima specificata de catre client.
        Double suma = MetodeSpeciale.parseDouble(parametri[4]);
        if(suma == null || suma < 0){
            System.out.println("ERROR!!! The sum '" + parametri[4] + "' is not valid. Must be floating point number." +
                    " Nothing was done.");
            return;
        }
        CasaDeLicitatii instanta = CasaDeLicitatii.getInstance();
        // Se cauta licitatia cu id-ul de produs citit de la tastatura.
        Licitatie licitatie = Licitatie.withId(id, instanta.getLicitatii());
        // Daca inca nu s-a pornit o licitatie pentru produsul cu id-ul specificat, se incearca crearea unei noi
        // licitatii pentru acest produs.
        if(licitatie == null){
            // Se cauta produsul cu id-ul dorit.
            Produs produs = Produs.withId(id, CasaDeLicitatii.getInstance().getProduse());
            if(produs == null){
                System.out.println("WARNING!!! The product with the specified id '" + id + "' was not found. Nothing was done.");
                return;
            }
            // Se gaseste un broker pentru client.
            Broker broker = CodDuplicat.getBrokerForClient(suma, instanta, produs, client);
            if (broker == null) {
                return;
            }
            // Se creeaza o noua licitatie pentru produsul cu id-ul specificat de client.
            licitatie = new Licitatie(produs);
            // Se adauga clientul in in licitatie.
            if(licitatie.addParticipant(client) == null){
                return;
            }
            instanta.addLicitatie(licitatie);
            // Se creeaza dependentele client-broker-licitatie.
            instanta.assignBroker(client, licitatie, suma, broker);
            // Succes!
            System.out.println(client.getNume() + " has succesfully started a new auction for this product:");
            System.out.println(produs.toString());
            client.incNrParticipari();
            return;
        }
        // Daca exista deja o licitatie pentru acest produs:
        // Se gaseste produsul.
        Produs produs = Produs.withId(id, instanta.getProduse());
        // Se gaseste un broker pentru client.
        Broker broker = CodDuplicat.getBrokerForClient(suma, instanta, produs, client);
        if (broker == null) {
            return;
        }
        // Se adauga clientul in in licitatie.
        if(licitatie.addParticipant(client) == null){
            return;
        }
        // Se creeaza dependentele dintre client, broker si licitatie.
        instanta.assignBroker(client, licitatie, suma, broker);
        System.out.println(client.getNume() + " has succesfully joined the auction for this product:");
        System.out.println(produs);
        client.incNrParticipari();
    }
}
