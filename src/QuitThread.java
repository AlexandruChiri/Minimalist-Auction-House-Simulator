/**
 * Clasa ce creeaza thread-uri ce realizeaza parasirea unei licitatii de catre un client.
 */
public class QuitThread extends Thread{
    /**
     * Se creeaza un QuitThread.
     * @param parametri Descrierea comenzii ce se retine in campul parametri.
     * @param client Clientul care solicita parasirea unei licitatii.
     */
    public QuitThread(String[] parametri, Client client) {
        this.client = client;
        this.parametri = parametri;
    }

    /**
     * Clientul care solicita parasirea unei licitatii.
     */
    private final Client client;
    /**
     * Descrierea comenzii ce se retine in campul parametri.
     */
    private final String[] parametri;

    /**
     * Executa parasirea unei licitatii de catre client.
     */
    public void run() {
        // Se obtine de pendenta broker-licitatie a clientului in functie de id-ul cerut de acesta.
        LicBrok licBrok = CodDuplicat.bringLicBrok(parametri, client, "quit");
        if (licBrok == null) {
            return;
        }
        // Se retine broker-ul.
        Broker broker = licBrok.getBroker();
        // Se efectueaza parasirea licitatiei de catre broker.
        broker.quit(client, licBrok.getLicitatie());
    }
}
