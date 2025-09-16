/**
 * Dependenta client-sumaPropusa in cadrul unei licitatii (propunere).
 */
public class Propunere {
    /**
     * Clientul care a propus suma.
     */
    private final Client client;
    /**
     * Suma propusa de client (sau 0).
     */
    private double suma;
    /**
     * true daca propunerea a fost actualizata la pasul curent al licitatiei, altfel false.
     */
    private boolean updated;

    /**
     * Constructor cu parametri.
     * @param client Clientul.
     * @param suma Suma.
     * @param updated Statutul propunerii la pasul curent al unei licitatii (updatat - true; ne-updatat - false).
     */
    public Propunere(Client client, double suma, boolean updated) {
        this.client = client;
        this.suma = suma;
        this.updated = updated;
    }

    /**
     * Constructor doar cu parametrul client.
     * @param client Clientul.
     */
    private Propunere(Client client) {
        this.client = client;
    }

    /**
     * Metoda equals verifica daca obiectul curent este egal cu obiectul o.
     * @param o Obiectul cu care se compara obiectul curent.
     * @return true in caz afirmativ, altfel false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Propunere)) return false;
        Propunere propunere = (Propunere) o;
        return client.equals(propunere.client);
    }

    /**
     * Cauta o propunere (dependenta client-suma) intr-o lista de propuneri.
     * @param client Clientul a carui propunere se cauta.
     * @param list Lista de propuneri.
     * @return Propunerea/dependenta gasita.
     */
    public static Propunere withClient(Client client, MyArrayList<Propunere> list){
        Propunere propunere = new Propunere(client);
        return list.getElem(propunere);
    }

    /**
     * Metoda toString.
     * @return String-urile concatenate ale campurilor propunerii curente.
     */
    @Override
    public String toString() {
        return "Propunere{" +
                "idClient=" + client.getId() +
                ", suma=" + suma +
                ", updated=" + updated +
                '}';
    }

    /**
     * Getter pentru clientul caruia ii apartine propunerea.
     * @return Clientul caruia ii apartine propunerea.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Getter pentru suma.
     * @return Suma
     */
    public double getSuma() {
        return suma;
    }

    /**
     * Getter pentru statut.
     * @return Valoarea campului updated.
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * Setter pentru suma.
     * @param suma Valoarea la care se seteaza suma.
     */
    public void setSuma(double suma) {
        this.suma = suma;
    }

    /**
     * Setter penntru statut.
     * @param updated Valoarea la care se seteaza statutul.
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
