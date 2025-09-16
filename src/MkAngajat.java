/**
 * Clasa ce creeaza thread-uri ce creeaza angajati.
 */
class MkAngajat extends Thread{
    /**
     * Constructor cu parametri.
     * @param parametru Al treilea parametru al comenzii.
     */
    public MkAngajat(String parametru) {
        tipAngajat = parametru;
    }

    /**
     * Tipul angajatului pe care il creeaza.
     */
    private final String tipAngajat;

    /**
     * Creeaza un nou angajat conform descrierii din stringul tipAngajat si-l adauga in lista de brokeri/administratori
     * in functie de tip.
     */
    @Override
    public void run() {
        Angajat angajat;
        if(tipAngajat.equalsIgnoreCase("broker")){
            angajat = new Broker();
            CasaDeLicitatii.getInstance().addBroker((Broker) angajat);
        }
        else if(tipAngajat.equalsIgnoreCase("administrator")){
            angajat = new Administrator();
            CasaDeLicitatii.getInstance().addAdministrator((Administrator)angajat);
        }
        else{
            System.out.println("WARNING!!! Unknown type '" + tipAngajat + "' of employee. No employee was added.");
            return;
        }
    }
}
