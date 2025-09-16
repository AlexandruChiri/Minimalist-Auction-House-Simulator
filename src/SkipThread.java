/**
 * Clasa Thread care efectueaza ignorarea unui pas al unei licitatii de catre un client.
 */
public class SkipThread extends Thread{

    /**
     * Constructor cu parametri.
     * @param parametri Descrierea comenzi.
     * @param client Clientul care a facut solicitarea.
     */
    public SkipThread(String[] parametri, Client client) {
        this.client = client;
        this.parametri = parametri;
    }

    /**
     * Clientul care a facut solicitarea.
     */
    private final Client client;
    /**
     * Descrierea comenzi.
     */
    private final String[] parametri;

    /**
     * Realizeaza operatia de skip pentru clientul care a solicitat-o.
     */
    public void run() {
        // Se obtine de pendenta broker-licitatie a clientului in functie de id-ul cerut de acesta.
        LicBrok licBrok = CodDuplicat.bringLicBrok(parametri, client, "skip");
        if (licBrok == null) {
            return;
        }
        // Se retine broker-ul.
        Broker broker = licBrok.getBroker();
        // Se efectueaza sarirea pasului de catre client.
        broker.skip(client, licBrok.getLicitatie());
    }
}
