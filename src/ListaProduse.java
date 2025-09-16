/**
 * Clasa pentru a crea o lista de produse.
 */
public class ListaProduse extends MyArrayList<Produs> {
    /**
     * Metoda speciala toString pentru apelarea metodei toString(Client) din clasa Produs.
     * @param caller Clientul care solicita vizualizarea listei de produse.
     * @return String-urile concatenate ale produselor din lista.
     */
    public String toString(Client caller){
        super.lock();
        StringBuilder str = new StringBuilder("[\n");
        for(Produs i : this){
            str.append(i.toString(caller)).append(",\n");
        }
        str.append("]");
        super.unlock();
        return str.toString();
    }
}
