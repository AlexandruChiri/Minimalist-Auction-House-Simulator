import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clasa pentru casa de licitatii.
 */
public class CasaDeLicitatii {
    /**
     * Constructor fara parametri. Este privat deoarece nu trebuie generata mai mult de o instanta.
     */
    private CasaDeLicitatii() {

    }

    /**
     * Variabila instanta, initializata pe loc (element de Singleton).
     */
    private static final CasaDeLicitatii instance = new CasaDeLicitatii();

    /**
     * Lista de produse.
     */
    private final ListaProduse produse = new ListaProduse();
    /**
     * Lista de clienti.
     */
    private final MyArrayList<Client> clienti = new MyArrayList<>();
    /**
     * Lista de licitatii.
     */
    private final MyArrayList<Licitatie> licitatii = new MyArrayList<>();
    /**
     * Lista de brokeri.
     */
    private final MyArrayList<Broker> brokers = new MyArrayList<>();
    /**
     * Lista de administratori.
     */
    private final MyArrayList<Administrator> administrators = new MyArrayList<>();
    /**
     * Lacatul pentru blocarea instantei.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Getter pentru lista de brokeri.
     * @return Lista de brokeri.
     */
    public MyArrayList<Broker> getBrokers() {
        return brokers;
    }

    /**
     * Metoda toString.
     * @return String-urile concatenate ale listelor.
     */
    @Override
    public String toString() {
        lock.lock();
        String str = "CasaDeLicitatii{" +
                "\nproduse=" + produse +
                "\nclienti=" + clienti +
                "\nlicitatii=" + licitatii +
                "\nbrokers=" + brokers +
                "\nadministrators=" + administrators +
                "\n}";
        lock.unlock();
        return str;
    }

    /**
     * Getter pentru instanta.
     * @return Instanta clasei.
     */
    public static CasaDeLicitatii getInstance() {
        return instance;
    }

    /**
     * Getter pentru lista de brokeri.
     * @return Lista de brokeri.
     */
    public ListaProduse getProduse() {
        return produse;
    }

    /**
     * Getter pentru lista de clienti.
     * @return Lista de clienti.
     */
    public MyArrayList<Client> getClienti() {
        return clienti;
    }

    /**
     * Getter pentru lista de licitatii.
     * @return Lista de licitatii.
     */
    public MyArrayList<Licitatie> getLicitatii() {
        return licitatii;
    }

    /**
     * Adauga un broker in lista de brokeri si il afiseaza la stdout.
     * @param broker Broker-ul adaugaat.
     */
    public void addBroker(Broker broker){
        lock.lock();
        brokers.add(broker);
        lock.unlock();
        System.out.println("Added " + broker.toString());
    }

    /**
     * Adauga un administrator in lista de administratori si il afiseaza la stdout.
     * @param administrator Administratorul adaugat.
     */
    public void addAdministrator(Administrator administrator){
        lock.lock();
        administrators.add(administrator);
        lock.unlock();
        System.out.println("Added " + administrator.toString());
    }

    /**
     * Adauga un client in lista de clienti si il afiseaza la stdout.
     * @param client Clientul adaugat.
     */
    public void addClient(Client client){
        lock.lock();
        clienti.add(client);
        lock.unlock();
        System.out.println("Added " + client.toString());
    }

    /**
     * Adauga un licitatie in lista de licitatii si il afiseaza la stdout.
     * @param licitatie Licitatia adaugata.
     */
    public void addLicitatie(Licitatie licitatie){
        lock.lock();
        licitatii.add(licitatie);
        lock.unlock();
        System.out.println("Added " + licitatie.toString());
    }

    /**
     * Adauga un produs in lista de produse si il afiseaza la stdout.
     * @param produs Clientul adaugat.
     * @param caller Administratorul care efectueaza adaugarea.
     */
    public void addProdus(Produs produs, Administrator caller){
        lock.lock();
        produse.add(produs);
        lock.unlock();
        System.out.println("Produsul:");
        System.out.println(produs.toString());
        System.out.print("a fost adaugat de catre administratorul ");
        if(caller == null){
            System.out.println("null/confidential/necunoscut.");
        }
        else{
            System.out.println(caller.toString());
        }
    }

    /**
     * Getter pentru lista de administratori.
     * @return Lista de administratori.
     */
    public MyArrayList<Administrator> getAdministrators() {
        return administrators;
    }

    /**
     * Creeaza dependentele intre un client, o licitatie si un broker extras la intamplare din lista de brokeri.
     * @param client Clientul care a solicitata licitatia.
     * @param licitatie Licitatia solicitata.
     * @param suma Suma maxima pe care clientul o poate oferi.
     * @param broker Broker-ul care este asignat clientului.
     */
    public void assignBroker(Client client, Licitatie licitatie, double suma, Broker broker){
        // Se creeaza dependenta client-licitatie la dependentele memorate de broker.
        broker.adaugaClient(client, licitatie, suma);
        // Se creeaza dependenta broker-licitatie la lista de dependente a clientului.
        client.getLicitatii().add(new LicBrok(licitatie, broker));
    }

    /**
     * Verifica daca este posibila crearea unei noi licitatii.
     * @return true in caz afirmativ, altfel false.
     */
    public boolean checkNewAuctionPossibility() {
        // Se verifica daca exista macar un broker.
        if(brokers.isEmpty()){
            System.out.println("ERROR!!! No broker to assign. Nothing was done.");
            return false;
        }
        return true;
    }

    /**
     * Memoreaza in entitatea licitatie suma pe care clientul doreste sa o propuna si actualizeaza propunerea acestuia.
     * @param licitatie Licitatia curenta a clientului.
     * @param client Clientul care face propunerea.
     * @param suma Suma propusa.
     * @param broker Broker-ul mediator al clientului.
     */
    public static void transmite(Licitatie licitatie, Client client, Double suma, Broker broker) {
        Broker.notifica();
        LicCli licCli = new LicCli(licitatie, client, suma);
        licCli = broker.getClienti().getElem(licCli);
        if(licCli == null){
            System.out.println("ERROR!!! In CasaDeLicitatii.transmite.\nNo such client-auction dependance in broker:" + broker);
            System.out.println("Auction: " + licitatie);
            System.out.println("Client: " + client);
            MetodeSpeciale.sleep(5000);
            return;
        }
        double maxim = MetodeSpeciale.faraComision(client, licCli.getSumaMaxima());
        // Daca suma maxima (declararea sumei maxima de catre clientul se considera cu comision) este mai mica decat
        // suma pe care clientul doreste sa o propuna, atunci se va afisa un mesaj de avertizare cu privire la acest
        // fapt.
        if(suma > maxim){
            System.out.println("WARNING!!! The client can't propose a sum bigger than the maximum sum they have " +
                    "decided " + maxim + " when they joined/started the auction. Nothing was done.");
            System.out.println("Don't forget the comision is included.");
            return;
        }
        // Altfel se actualizeaza propunerea clientului.
        licitatie.updatePropuneri(suma, client);
    }

    /**
     * Broker-ul transmite cererea parasire a unei licitatii de catre un client si rupe dependentele dintre
     * client, licitatie si broker.
     * @param client Clientul care doreste sa paraseasca licitatia.
     * @param licitatie Licitatia in cadrul careia clientul face cererea.
     * @param broker Broker-ul care trimite aceasta soliictare.
     */
    public void quit(Client client, Licitatie licitatie, Broker broker) {
        licitatie.quit(client);
        LicCli licCli = LicCli.withClientLicitatie(client, licitatie, broker.getClienti());
        broker.remove(licCli);
        LicBrok licBrok = LicBrok.withLicitatie(licitatie, client.getLicitatii());
        client.getLicitatii().remove(licBrok);
        licitatie.remove(Propunere.withClient(client, licitatie.getPropuneri()));
    }

    /**
     * Se efectueaza ignorarea unui pas de catre un client.
     * @param client Clientul care cere trecerea la urmatorul pas al licitatiei fara sa propuna vreo suma.
     * @param licitatie Licitatia in cadrul careia clientul face cererea.
     */
    public void skip(Client client, Licitatie licitatie) {
        Broker.notifica();
        licitatie.skip(client);
    }

    /**
     * Notifica clientul despre rezultatul licitatiei si realizeaza parasirea licitatiei de catre client cu tot cu
     * ruperea dependentelor dintre client, licitatie si broker.
     * @param client Clientul care este instiintat.
     * @param licitatie Licitatia care s-a incheiat.
     * @param broker Broker-ul asignat clientului.
     */
    public void rupeDependente(Client client, Licitatie licitatie, Broker broker) {
        // Clientul este informat de rezultatul licitatiei.
        client.notifica();
        // Se realizeaza ruperea dependentelor.
        CasaDeLicitatii.getInstance().quit(client, licitatie, broker);
    }

    /**
     * Instiinteaza castigatorul unei licitatii despre rezultat.
     * @param winer Castigatorul licitatiei.
     * @param produs Produsul pentru care s-a licitat.
     * @param pret Pretul cu care produsul va fi cumparat de catre clientul castigator.
     */
    public void instiinteaza(Client winer, Produs produs, double pret) {
        winer.notifica();
        winer.incLicitatiiCastigate();
        // Se seteaza valoarea campului vanzare la pretul primit ca parametru.
        produs.setPretVanzare(pret);
        System.out.println("The winer who will buy\n" + produs + "\nFor the price of " + pret + " is " +
                winer.getNume());
        // Se inlatura produsul din lista.
        getInstance().lock.lock();
        CasaDeLicitatii.getInstance().getProduse().remove(produs);
        getInstance().lock.unlock();
    }

    /**
     * Metoda ce realizeaza trecerea la urmatorul pas al unei licitatii.
     * @param licitatie Licitatia pentru care se face trecerea la urmatorul pas.
     */
    public static void nextStep(Licitatie licitatie){
        licitatie.lock();
        licitatie.incNrPasi();
        // Se verifica daca s-a atins numarul maxim de pasi.
        if(licitatie.getNrPasi() == licitatie.getNrPasiMaxim()){
            // Se incearca terminarea licitatiiei.
            if(licitatie.finish()){
                // In caz afirmativ se elimina licitatia din lista de licitatii.
                CasaDeLicitatii.getInstance().getLicitatii().remove(licitatie);
                return;
            }
            // In caz de esec se adauga un pas maxim.
            else licitatie.incNrPasiMaxim();
        }
        // Se face trecerea la urmatorul pas.
        for(Propunere propunere : licitatie.getPropuneri()){
            double suma = propunere.getSuma();
            if(suma > licitatie.getSumaMaxima()){
                licitatie.setSumaMaxima(suma);
            }
            propunere.setUpdated(false);
        }
        licitatie.unlock();
    }
}
