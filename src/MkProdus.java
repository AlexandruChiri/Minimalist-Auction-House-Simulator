/**
 * Clasa ce creeaza thread-uri ce creeaza produse apeland la administratori.
 */
public class MkProdus extends Thread{
    /**
     * Constructor cu parametri.
     * @param parametri Array-ul de string-uri cu care se initializeaza campul parametri.
     */
    public MkProdus(String[] parametri){
        this.parametri = parametri;
    }

    /**
     * Descrierea produsului.
     */
    private final String[] parametri;

    /**
     * Creaza un produs conform descrierii date de parametri si-l adauga in lista de produse.
     */
    @Override
    public void run() {
        // Se alege un administrator la intamplare care sa adauge noul produs.
        Administrator administrator = CasaDeLicitatii.getInstance().getAdministrators().getRandomElem();
        // Daca nu exista administratori, atunci nu se face nimic.
        if(administrator == null){
            System.out.println("WARNING!!! No administrators to add the new product. No product was added.");
            return;
        }
        // Administratorul ales creeaza produsul.
        Produs produs = administrator.createProdus(parametri);
        if(produs == null){
            return;
        }
        // Administratorul adauga produsul in lista de produse scoase la vanzare.
        administrator.addProdus(produs);
    }
}