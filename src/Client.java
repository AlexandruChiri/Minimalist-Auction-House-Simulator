import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clasa client.
 */
public abstract class Client {

    /**
     * Constructor fara parametri. Nu cere id.
     */
    private Client() {
    }

    /**
     * Constructor cu parametri. Cere id.
     * @param nulObject Acest parametru doar face diferenta fata de constructorul fara parametri.
     */
    public Client(Object nulObject) {
        this.id = new Id();
    }

    /**
     * Identificatorul unic.
     */
    private Id id;
    /**
     * Noutatile.
     */
    private int noutati = 0;
    /**
     * Nume
     */
    private String nume;
    /**
     * Adresa
     */
    private String adresa;
    /**
     * Numarul de licitatii la care a participat.
     */
    private int nrParticipari = 0;
    /**
     * Numarul de licitatii castigate.
     */
    private int nrLicitatiiCastigate = 0;
    /**
     * Lista de licitatii in care se afla clientul in prezent.
     */
    private final MyArrayList<LicBrok> licitatii = new MyArrayList<>();
    /**
     * Lacatul obiectului.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Getter pentru lista de licitatii a clientului.
     * @return Lista de licitatii.
     */
    public MyArrayList<LicBrok> getLicitatii() {
        return licitatii;
    }

    /**
     * Metoda toString.
     * @return String-urile concatenate ale variabilelor clientului curent.
     */
    @Override
    public String toString() {
        lock.lock();
        String str = "id=" + id +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", nrParticipari=" + nrParticipari +
                ", nrLicitatiiCastigate=" + nrLicitatiiCastigate +
                ", licitatie=" + licitatii
                ;
        lock.unlock();
        return str;
    }

    /**
     * Gaseste un client cu un anume id intr-o lista de clienti.
     * @param id Id-ul clientului cautat.
     * @param list Lista de clienti.
     * @return Clientul cautat sau null in caz ca nu exista vreun client cu acest id in lista.
     */
    public static Client withId(Id id, MyArrayList<Client> list) {
        Client client = new Client() {
        };
        client.id = id;
        return list.getElem(client);
    }

    /**
     * Getter pentru nume.
     * @return Numele clientului.
     */
    public String getNume() {
        return nume;
    }

    /**
     * Builder style setter.
     * @param nume Numele la care se seteaza numele clientului.
     * @return Clientul curent.
     */
    public Client setNume(String nume) {
        this.nume = nume;
        return this;
    }

    /**
     * Builder style setter.
     * @param adresa Adresa la care se seteaza adresa clientului.
     * @return Clientul curent.
     */
    public Client setAdresa(String adresa) {
        this.adresa = adresa;
        return this;
    }

    /**
     * Getter pentru numarul de licitatii castigate.
     * @return Numarul de licitatii castigate.
     */
    public int getNrLicitatiiCastigate() {
        return nrLicitatiiCastigate;
    }

    /**
     * Metoda custom equals. Verifica daca clientul curent este considerat a fi egal cu o.
     * @param o Obiectul cu care se compara clientul curent.
     * @return true daca clientul este egal cu obiectul o, altfel false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Client client = (Client) o;
        return id.equals(client.id);
    }

    /**
     * Notifica clientul curent.
     */
    public void notifica(){
        this.noutati += 1;
    }

    /**
     * Incrementeaza cu 1 numarul de licitatii castigate.
     */
    public void incLicitatiiCastigate() {
        ++nrLicitatiiCastigate;
    }

    /**
     * Incrementeaza cu 1 numarul de participari la licitatii ale clientului curent.
     */
    public void incNrParticipari() {
        ++nrParticipari;
    }

    /**
     * Getter pentru id.
     * @return Id-ul clientului curent.
     */
    public Id getId() {
        return id;
    }
}
