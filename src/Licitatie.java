import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clasa pentru memorarea licitatiilor in instante.
 */
public class Licitatie {

    /**
     * Numarul curent de participanti.
     */
    private int nrParticipanti = 0;
    /**
     * Id-ul produsului pentru care se face licitatia.
     */
    private final Id idProdus;
    /**
     * Numarul maxim de pasi.
     */
    private int nrPasiMaxim = CasaDeLicitatii.getInstance().getClienti().size() / 2;
    /**
     * Numarul curent de pasi.
     */
    private int nrPasi = 0;
    /**
     * Lista de propuneri/dependente client-suma propusa.
     */
    private final MyArrayList<Propunere> propuneri = new MyArrayList<>();
    /**
     * Lacatul instantei.
     */
    private final Lock lock = new ReentrantLock();
    /**
     * Statutul licitatiei (inceputa - true, neinceputa - false)
     */
    private boolean started = false;
    /**
     * Suma maxima calculata la terminarea pasului anterior sau 0.
     */
    private double sumaMaxima = 0;

    /**
     * Se creeaza o licitatie pentru produsul produs. Faptul ca parametrul nu este id de produs, ci chiar produsul
     * este o masura de protectie prin faptul ca trebuie sa existe produsul cu id-ul respectiv.
     * @param produs Produsul al carui id este preluat de licitatie.
     */
    public Licitatie(Produs produs) {
        this.idProdus = produs.getId();
    }

    /**
     * Constructor privat cu parametri.
     * @param idProdus Id-ul produsului pentru care se face licitatia.
     */
    private Licitatie(Id idProdus){ this.idProdus = idProdus;}

    /**
     * Seteaza la true campul started
     */
    public void start(){
        started = true;
    }

    /**
     * Cauta o licitatie cu un id de produs intr-o lista de licitatii.
     * @param idProdus Id-ul produsului.
     * @param list Lista de licitatii.
     * @return Licitatia gasita sau null.
     */
    public static Licitatie withId(Id idProdus, MyArrayList<Licitatie> list){
        return list.getElem(new Licitatie(idProdus));
    }

    /**
     * Getter pentru lista de propuneri.
     * @return Lista de propuneri.
     */
    public MyArrayList<Propunere> getPropuneri() {
        return propuneri;
    }

    /**
     * Getter pentru id-ul produsului.
     * @return Id-ul produsului.
     */
    public Id getIdProdus() {
        return idProdus;
    }

    /**
     * Blocheaza accesul altor thread-uri la licitatia curenta.
     */
    public void lock(){
        lock.lock();
    }

    /**
     * Deblocheaza accesul altor thread-uri la licitatia curenta.
     */
    public void unlock(){
        lock.unlock();
    }

    /**
     * Getter pentru numarul maxim de pasi.
     * @return Numarul maxim de pasi.
     */
    public int getNrPasiMaxim() {
        return nrPasiMaxim;
    }

    /**
     * Metoda equals compara licitatia curenta cu alt obiect.
     * @param o Obiectul cu care se compara licitatia curenta.
     * @return true daca obiectul o este egal cu licitatia curenta, altfel false.
     */
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o == null){
            return false;
        }
        if(o.getClass() != o.getClass()){
            return false;
        }
        Licitatie licitatie = (Licitatie) o;
        return this.idProdus.equals(licitatie.idProdus);
    }

    /**
     * Adauga un client in licitatie.
     * @param client Clientul care este adaugat.
     * @return Clientul adaugat sau null.
     */
    public Client addParticipant(Client client){
        // Se verifica daca clientul este deja implicat in licitatia curenta.
        Propunere propunere = Propunere.withClient(client, propuneri);
        if(propunere != null){
            System.out.println("ERROR!!! The client is already in this auction. Nothing was done.");
            return null;
        }
        // Se creeaza o noua propunere neactualizata.
        propunere = new Propunere(client, 0.0, false);
        // Se adauga propunerea.
        lock.lock();
        propuneri.add(propunere);
        // Creste numarul de participanti.
        nrParticipanti += 1;
        // Daca numarul de participanti a atins 2, atunci incepe licitatia.
        if(!started && nrParticipanti >= 2){
            start();
        }
        lock.unlock();
        return client;
    }

    /**
     * Metoda toString.
     * @return Returneaza String-urile concatenate ale campurilor.
     */
    @Override
    public String toString() {
        String str = "Licitatie{" +
                "nrParticipanti=" + nrParticipanti +
                ", idProdus=" + idProdus +
                ", nrPasi=" + nrPasi +
                ", nrPasiMaxim=" + nrPasiMaxim +
                ", sumaMaxima=" + sumaMaxima +
                "\npropuneri=\n" + propuneri +
                "\n}";
        return str;
    }

    /**
     * Verifica daca pasul curent s-a incheiat.
     * @return true in caz afirmativ, altfel false.
     */
    public boolean stepDone(){
        lock();
        for(Propunere i : propuneri){
            if(!i.isUpdated()){
                unlock();
                return false;
            }
        }
        unlock();
        return true;
    }

    /**
     * Actualizeaza propunerea unui client conform sumei pe care acesta doreste sa o propuna.
     * @param suma Suma propusa de client.
     * @param client Clientul.
     */
    public void updatePropuneri(Double suma, Client client){
        // Se verfica daca a inceput licitatia.
        if(!started){
            System.out.println("WARNING!!! The auction has not started yet. At least 2 clients must take part in it " +
                    "in order for it to start.");
            return;
        }
        // Se verifica daca suma este in format corect (teoretic ar trebui sa fie)
        if(suma == null){
            System.out.println("ERROR!!! In Licitatie.update!!! Suma must not be null!!! Nothing was done.");
            return;
        }
        // Se memoreaza propunerea clientului.
        lock.lock();
        Propunere propunere = Propunere.withClient(client, propuneri);
        lock.unlock();
        if(propunere == null){
            System.out.println("ERROR!!! In Licitatie.update!!! Client " + client.getNume() + " is not in this" +
                    "auction: " + this.idProdus);
            return;
        }
        // Propunerea a fost deja actualizata la pasul curent? => Warning
        if(propunere.isUpdated()){
            System.out.println("WARNING!!! You had already made a proposal at this current step.\nWait for the other" +
                    " clients to propose a price. Nothing was done.");
            return;
        }
        // Actualizeaza propunerea.
        propunere.setSuma(suma);
        propunere.setUpdated(true);
        System.out.println("Your proposal was succesfully set to " + suma + ".");
        // Se incearca trecerea la urmatorul pas.
        if(stepDone()) {
            CasaDeLicitatii.nextStep(this);
        }
        Broker.notifica();
    }

    /**
     * Efectueaza operatia de skip pentru un client.
     * @param client Clientul.
     */
    public void skip(Client client) {
        // Se cauta propunerea clientului.
        lock.lock();
        Propunere propunere = Propunere.withClient(client, propuneri);
        lock.unlock();
        // A fost deja updatata la pasul curent? => Warning
        if(propunere.isUpdated()){
            System.out.println("WARNING!!! You had already made a proposal at this current step.\nWait for the other" +
                    " clients to propose a price. Nothing was done.");
            return;
        }
        // Se actualizeaza propunerea.
        propunere.setUpdated(true);
        System.out.println(client.getNume() + " has succesfuly skipped their proposal.");
        // Se incearca trecerea la urmatorul pas.
        if(this.stepDone()) {
            CasaDeLicitatii.nextStep(this);
        }
        Broker.notifica();
    }

    /**
     * Realizeaza ruperea dependentelor dintre client si licitatie.
     * @param client Clientul care a solicitat parasirea licitatiei.
     */
    public void quit(Client client) {
        // Se cauta propunerea unui client.
        lock.lock();
        Propunere propunere = Propunere.withClient(client, propuneri);
        if(propunere == null){
            System.out.println("ERROR!!! In Licitatie.quit(Client) Client " + client.getNume() + " is not taking part" +
                    " to this auction.");
        }
        // Se inlatura propunerea clientului.
        propuneri.remove(propunere);
        // Se decrementeaza numarul de participanti.
        nrParticipanti -= 1;
        System.out.println("Client " + client.getNume() + " has succesfully quited the auction.");
        // Se verifica daca mai sunt clienti in licitatie.
        if(nrParticipanti == 0){
            CasaDeLicitatii.getInstance().getLicitatii().remove(this);
        }
        // Daca mai este unul, se incearca terminarea licitatiei (inainte sa propuna o suma mai mica XD).
        else if(nrParticipanti == 1){
            this.propuneri.get(0).setUpdated(true);
        }
        else if(this.stepDone()) {
            CasaDeLicitatii.nextStep(this);
        }
        lock.unlock();
    }

    /**
     * Metoda ce incearca terminarea licitatiei.
     * @return true daca macar un client a oferit o suma mai mare decat pretul produsului, altfel false
     */
    public boolean finish(){
        // Se cauta clientul cu propunerea pretului cel mai mare/numarul de licitatii castigate cel mai mare.
        double maxPrice = propuneri.get(0).getSuma();
        Client winer = propuneri.get(0).getClient();
        // Se cauta clientul cu propunerea celei mai mari sume.
        for(Propunere propunere : propuneri){
            double suma = propunere.getSuma();
            if(suma > maxPrice){
                maxPrice = suma;
                winer = propunere.getClient();
            }
            else if(suma == maxPrice){
                Client client = propunere.getClient();
                if(client.getNrLicitatiiCastigate() > winer.getNrLicitatiiCastigate()){
                    winer = client;
                }
            }
        }
        // Se verifica daca pretul minim al produsului a fost atins.
        Produs produs = Produs.withId(this.idProdus, CasaDeLicitatii.getInstance().getProduse());
        if(maxPrice >= produs.getPretMinim()){
            // In caz afirmativ:
            // Se gaseste broker-ul clientului castigator si acesta aplica comisionul.
            Broker broker = (LicBrok.withLicitatie(this, winer.getLicitatii())).getBroker();
            // Broker-ul aplica comisionul.
            maxPrice = broker.aplicComision(winer, maxPrice);
            // Clientii sunt instiintati de rezultatul licitatiei.
            CasaDeLicitatii.getInstance().instiinteaza(winer, produs, maxPrice);
            // Se elimina toate propunerile din lista de propuneri.
            while(!propuneri.isEmpty()){
                Propunere propunere = propuneri.get(0);
                Client client = propunere.getClient();
                // Se rup dependentele dintre client, licitatie si broker.
                broker = (LicBrok.withLicitatie(this, client.getLicitatii())).getBroker();
                CasaDeLicitatii.getInstance().rupeDependente(client, this, broker);
            }
            return true;
        }
        // Daca pretul maxim nu a fost atins se returneaza false.
        System.out.println("Pretul minim al produsului:\n" + produs + "\nnu a fost atins. Se mai adauga un pas la licitatie.");
        return false;
    }

    /**
     * Elimina o propunere din lista de propuneri.
     * @param propunere Propunerea care este eliminata.
     */
    public void remove(Propunere propunere){
        lock.lock();
        this.propuneri.remove(propunere);
        lock.unlock();
    }

    /**
     * Incrementeaza numarul de pasi.
     */
    public void incNrPasi() {
        ++nrPasi;
    }

    /**
     * Getter pentru pasul curent.
     * @return Pasul curent al licitatiei.
     */
    public int getNrPasi() {
        return nrPasi;
    }

    /**
     * Incrementeaza numarul maxim de pasi.
     */
    public void incNrPasiMaxim() {
        ++nrPasiMaxim;
    }

    /**
     * Getter pentru suma maxima.
     * @return Suma maxima.
     */
    public double getSumaMaxima() {
        return sumaMaxima;
    }

    /**
     * Setter pentru suma maxima.
     * @param sumaMaxima Valoarea la care se steaza suma maxima.
     */
    public void setSumaMaxima(double sumaMaxima) {
        this.sumaMaxima = sumaMaxima;
    }
}
