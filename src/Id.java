/**
 * Clasa pentru generare si ccomparare de identificatori.
 */
public class Id {
    /**
     * Valoarea id-ului.
     */
    private final int val;

    /**
     * Numarul de id-uri generate pana acum.
     */
    private static int nr = 0;

    /**
     * Se creeaza un id si se incrementeaza variabila statica nr.
     */
    public Id(){
        this.val = nr;
        //System.out.println(nr);
        ++nr;
    }

    /**
     * Creeaza un nou id, dar fara a folosi/modifica variabila statica nr.
     * @param val Valoarea care este atribuita campului val.
     */
    private Id(int val){
        this.val = val;
    }

    /**
     * Similar cu Integer.parseInt primeste un string ca parametru si in functie de acel string initializeaza
     * campurile nestatice (mai multe in caz de extindere a proiectului).
     * @param string Stringul din care se extrage valoarea.
     * @return Un obiect Id sau null in caz de esec.
     */
    public static Id parseId(String string){
        try{
            return new Id(Integer.parseInt(string));
        } catch (NumberFormatException e){
            System.out.println("ERROR!!! Bad id string '" + string + "'. Nothing was done.");
            return null;
        }
    }

    /**
     * Metoda toString.
     * @return Valoarea campului val in format sir de caractere.
     */
    @Override
    public String toString() {
        return Integer.toString(val);
    }

    /**
     * Metoda equals verifica egalitatea dintre 2 id-uri.
     * @param o Obiectul cu care se verifica egalitatea lui this.
     * @return true daca obiectele au aceeasi valoare val, false altfel.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Id)) return false;

        Id id = (Id) o;

        return val == id.val;
    }
}
