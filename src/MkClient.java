/**
 * Thread ce creeaza un client
 */
public class MkClient extends Thread{
    /**
     * Constructor cu parametri.
     * @param parametri Parametrii comenzii de creare a clientului.
     */
    public MkClient(String[] parametri){
        this.parametri = parametri;
    }

    /**
     * Parametrii comenzii de creare a clientului.
     */
    private final String[] parametri;

    /**
     * Metoda ce creeaza un client si-l adauga in lista de clienti.
     */
    @Override
    public void run(){
        Client client;
        // Daca se adauga o persoana fizica:
        if(parametri[2].equalsIgnoreCase("PsF")){
            // Prea putini parametri? Nu se face nimic.
            if(parametri.length < 6){
                System.out.println("WARNING!!! Not enough arguments for physical person description. No client was created.");
                return;
            }
            // Se memoreaza data.
            Date date = new Date();
            date = date.setToDate(parametri[5]);
            if(date == null){
                System.out.println("WARNING!!! Bad date format '" + parametri[5] + "'. No client was added. Make sure the date format is dd.ll.yyyy.");
                return;
            }
            if(!date.valida()){
                System.out.println("WARNING!!! The date " + date.toString() + " is not a valid date. Make sure the specified date exists.");
                return;
            }
            // Se memoreaza clientul.
            client = new PersoanaFizica()
                    .setDataNastere(date);
        }
        // Daca se adauga o persoana juridica.
        else if(parametri[2].equalsIgnoreCase("PsJ")){
            // Prea putini parametri? Nu se face nimic.
            if(parametri.length < 7){
                System.out.println("WARNING!!! Not enough arguments for client description. No client was added.");
                return;
            }
            Companie companie;
            Double capitalSocial;
            // Se memoreaza tipul de companie.
            companie = Companie.parseCompanie(parametri[5]);
            if(companie == null){
                System.out.println("WARNING!!! Unknown company type '" + parametri[5] + "'. No client was addded.");
                return;
            }
            // Se memoreaza capitalul social.
            capitalSocial = MetodeSpeciale.parseDouble(parametri[6]);
            if(capitalSocial == null) {
                System.out.println("WARNING!!! Bad format '" + parametri[6] + "' for double/floating point number." +
                        "No client was added.");
                return;
            }
            // Se memoreaza clientul.
            client = new PersoanaJuridica()
                    .setCompanie(companie)
                    .setCapitalSocial(capitalSocial);
        }
        // Daca clientul nu este persoana fizica si nici persoana juridica, atunci nu se face nimic.
        else {
            System.out.println("WARNING!!! Unknown client type '" + parametri[2] + "' converted to " +
                    parametri[2].toLowerCase() + ". No client was added.");
            return;
        }
        // Se memoreaza numele si adresa clientului.
        client
                .setNume(parametri[3])
                .setAdresa(parametri[4]);
        // Clientul este adaugat in lista de clienti.
        CasaDeLicitatii.getInstance().addClient(client);
    }
}