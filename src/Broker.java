import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clasa pentru instantele broker.
 */
public class Broker extends Angajat {
    /**
     * Creeaza un broker cu un identificator unic.
     */
    public Broker() {
    }

    /**
     * Lista de dependente client-licitatie ale broker-ului curent.
     */
    private final MyArrayList<LicCli> clienti = new MyArrayList<>();
    /**
     * Variabila de noutati (element de Observer)
     */
    private static int noutati = 0;
    /**
     * Lock-ul clasei.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Metoda toString.
     * @return String-urile concatenate ale variabilelor broker-ului.
     */
    @Override
    public String toString() {
        lock.lock();
        String str = "Broker{" +
                super.toString() +
                ", clienti:" + clienti +
                "\nnoutati=" + noutati +
                "\n}";
        lock.unlock();
        return str;
    }

    /**
     * Notifica toti brokerii (element de Observer).
     */
    public static void notifica(){
        noutati += 1;
    }

    /**
     * Adauga o dependenta client-licitatie.
     * @param client Clientul care este adaugat.
     * @param licitatie Licitatia.
     * @param suma Suma declarata de client.
     */
    public void adaugaClient(Client client, Licitatie licitatie, double suma){
        clienti.add(new LicCli(licitatie, client, suma));
        Broker.notifica();
    }

    /**
     * Elimina o dependenta client-licitatie.
     * @param licitatie_client Dependenta care este eliminata.
     */
    public void remove(LicCli licitatie_client){
        lock.lock();
        clienti.remove(licitatie_client);
        lock.unlock();
    }

    /**
     * Getter pentru lista de dependente.
     * @return Lista de dependente.
     */
    public MyArrayList<LicCli> getClienti() {
        return clienti;
    }


    /**
     * Transmite casei de licitatii cererea clientului de a propune o suma intr-o licitatie.
     * @param licitatie Licitatia in cadrul careia clientul propune suma.
     * @param client Clientul care propune suma.
     * @param suma Suma propusa.
     */
    public void transmite(Licitatie licitatie, Client client, Double suma) {
        CasaDeLicitatii.transmite(licitatie, client, suma, this);
    }

    /**
     * Broker-ul transmite cererea de ignorare a unui pas al unei licitatii de catre un client.
     * @param client Clientul care cere trecerea la urmatorul pas al licitatiei fara sa propuna vreo suma.
     * @param licitatie Licitatia in cadrul careia clientul face cererea.
     */
    public void skip(Client client, Licitatie licitatie) {
        CasaDeLicitatii.getInstance().skip(client, licitatie);
    }

    /**
     * Broker-ul transmite cererea parasire a unei licitatii de catre un client.
     * @param client Clientul care doreste sa paraseasca licitatia.
     * @param licitatie Licitatia in cadrul careia clientul face cererea.
     */
    public void quit(Client client, Licitatie licitatie) {
        CasaDeLicitatii.getInstance().quit(client, licitatie, this);
    }

    /**
     * Aplica comision.
     * @param client Clientul pentru care se aplica comisionul.
     * @param pret Pretul fara comision.
     * @return Pretul cu comision.
     */
    public double aplicComision(Client client, double pret) {
        return MetodeSpeciale.cuComision(client, pret);
    }
}