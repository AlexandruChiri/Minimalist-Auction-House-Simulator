import java.util.Scanner;

/**
 * Clasa in care se preiau comenzile de la consola.
 */
public class Command{

    /**
     * Perioada dintre executiile a 2 comenzi.
     */
    private static int period = 0;
    /**
     * Variabila care indica daca priectul este rulat in modul "testare" (fara multithreading)
     * sau nu (cu multithreading)
     */
    private static boolean testing = false;

    /**
     * Metoda care face trecerea dintre modurile "testare" si "multithreading"
     * @param mode Modul in format String (on/off)
     */
    private static void setTestingMode(String mode) {
        if(mode.equalsIgnoreCase("on"))
            testing = true;
        else if(mode.equalsIgnoreCase("off"))
            testing = false;
    }

    /**
     * Metoda care ruleaza un thread in functie daca se face testare sau nu
     * @param thread Thread-ul pe care il porneste in functie daca modul de testare este pornit.
     */
    private static void startThread(Thread thread) {
        if(testing)
            thread.run();
        else
            thread.start();
    }

    /**
     * Metoda in care se citesc comenzile.
     * @param s Sursa de unde se citesc comenzile.
     */
    public static void Consola(Scanner s){
        String comanda;
        String[] parametri;
        // Citirea comenzii.
        comanda = s.nextLine();
        // Memorarea argumentelor.
        //
        while(!comanda.contains("stop") && !comanda.contains("STOP")){
            parametri = MetodeSpeciale.split(comanda, " ");
            // In caz de linie goala nu se face nimic.
            if(parametri.length == 0);
            // Este tratat cazul comenzii new.
            else if(parametri[0].equalsIgnoreCase("new")){
                Command.create(parametri);
            }
            // Este tratat cazul comenzii print
            else if(parametri[0].equalsIgnoreCase("print")){
                Command.print(parametri);
            }
            // Este tratat cazul unei comenzi a unui client
            else if(parametri[0].equalsIgnoreCase("client")){
                Command.clientRequest(parametri);
            }
            // Este tratat cazul unei comenzi de schimbare de setari.
            else if(parametri[0].equalsIgnoreCase("set")){
                Command.settings(parametri);
            }
            else{
                System.out.println("WARNING!!! Unrecognized command '" + parametri[0] + "'. Nothing was done.");
            }
            comanda = s.nextLine();
            MetodeSpeciale.sleep(period);
        }
    }

    /**
     * Se executa comanda 'new'.
     * @param parametri Parametrii comenzii.
     */
    public static void create(String[] parametri){
        // Prea putini parametri? Nu se face nimic.
        if(parametri.length < 3){
            System.out.println("WARNING!!! No entity type was specified. Nothing was created.");
            return;
        }
        Thread addingThread;
        // Se creeaza un thread pentru adaugarea unui angajat.
        if(parametri[1].equalsIgnoreCase("angajat")){
            addingThread = new MkAngajat(parametri[2]);
        }
        // Se creeaza un thread pentru adaugarea unui client.
        else if(parametri[1].equalsIgnoreCase("client")){
            addingThread = new MkClient(parametri);
        }
        // Se creeaza un thread pentru adaugarea unui produs.
        else if(parametri[1].equalsIgnoreCase("produs")){
            addingThread = new MkProdus(parametri);
        }
        // Daca s-a incercat creerea unei entitati de tip necunoscut/in mod necorespunzator nu se face nimic;
        else{
            System.out.println("WARNING!!! Entities of unknown type '" + parametri[1] + "' cannot be created.");
            return;
        }
        // Se executa thread-ul.
        addingThread.setPriority(5);
        startThread(addingThread);
    }

    /**
     * Se executa comanda 'print'.
     * @param parametri Parametrii comenzi.
     */
    private static void print(String[] parametri) {
        // Prea putini parametri? Nu se face nimic.
        if(parametri.length < 2){
            System.out.println("WARNING!!! The command print needs at least 1 argument(least 2 words separated by a space on the line).");
            return;
        }
        // Se afiseaza datele publice ale casei de licitatii.
        if(parametri[1].equalsIgnoreCase("CasaDeLicitatii")){
            System.out.println(CasaDeLicitatii.getInstance().toString());
        }
        // Se afiseaza lista de brokeri
        else if(parametri[1].equalsIgnoreCase("brokeri")){
            System.out.println("" + CasaDeLicitatii.getInstance().getBrokers().toString());
        }
        // Se afiseaza lista de clienti
        else if(parametri[1].equalsIgnoreCase("clienti")){
            System.out.println("" + CasaDeLicitatii.getInstance().getClienti().toString());
        }
        // Se afiseaza lista de administratori
        else if(parametri[1].equalsIgnoreCase("administratori")){
            System.out.println("" + CasaDeLicitatii.getInstance().getAdministrators().toString());
        }
        // Se afiseaza lista de produse
        else if(parametri[1].equalsIgnoreCase("produse")){
            System.out.println("" + CasaDeLicitatii.getInstance().getProduse().toString());
        }
        // Se afiseaza lista de licitatii
        else if(parametri[1].equalsIgnoreCase("licitatii")){
            System.out.println("" + CasaDeLicitatii.getInstance().getLicitatii().toString());
        }
        // Parametru neasteptat => Warning
        else {
            System.out.println("WARNING!!! Unknown argument '" + parametri[1] + "' for command print. No thing was " +
                    "done.");
        }
    }

    /**
     * Executa o comanda data de un client.
     * @param parametri Parametri comenzii.
     */
    private static void clientRequest(String[] parametri) {
        // Prea putini parametri? Nu se face nimic.
        if(parametri.length < 2){
            System.out.println("WARNING!!! Not enough arguments for command client. Nothing was done.");
        }
        // Se memoreaza id-ul clientului.
        Id id;
        Client client;
        id = Id.parseId(parametri[1]);
        if(id == null){
            return;
        }
         // Se cauta clientul cu id-ul dorit.
        client = Client.withId(id, CasaDeLicitatii.getInstance().getClienti());
        if(client == null){
            System.out.println("WARNING!!! The client with the specified id was not found. Nothing was done.");
            return;
        }
        // Prea putini parametri pentru o comanda? Se afiseaza clientul.
        if(parametri.length < 3){
            System.out.println("Found:\n" + client.toString());
            return;
        }
        // Se afiseaza lista de produse cu preturile minime efective la care le-ar putea cumpara clientul.
        else if(parametri[2].equalsIgnoreCase("print_produse")){
            System.out.println(CasaDeLicitatii.getInstance().getProduse().toString(client));
            return;
        }
        // Se creeaza o licitatie.
        else if(parametri[2].equalsIgnoreCase("licitez")){
            Thread addingThread = new MkLicitatie(parametri, client);
            addingThread.setPriority(5);
            startThread(addingThread);
        }
        // Se primeste propunerea unui client.
        else if(parametri[2].equalsIgnoreCase("propun")){
            Thread propose = new MkPropunere(parametri, client);
            propose.setPriority(5);
            startThread(propose);
        }
        // Se efectueaza parasirea unei licitatii de catre un client.
        else if(parametri[2].equalsIgnoreCase("quit")){
            Thread quiting = new QuitThread(parametri, client);
            quiting.setPriority(5);
            startThread(quiting);
        }
        // Se efectueaza sarirea peste un pas al licitatiei de catre un client.
        else if(parametri[2].equalsIgnoreCase("skip")){
            Thread skiping = new SkipThread(parametri, client);
            skiping.setPriority(5);
            startThread(skiping);
        }
        // Comanda nerecunoscuta.
        else{
            System.out.println("WARNNG!!! Unknown argument 3 '" + parametri[2] + "' for command client.");
        }
    }

    /**
     * Efectueaza schimbarea de diverse setari (comanda 'set').
     * @param parametri Parametrii comenzii.
     */
    private static void settings(String[] parametri){
        // Prea putini parametri? Nu se face nimic.
        if(parametri.length < 3){
            System.out.println("ERROR!!! Not enough arguments to change a setting. Nothing was done.");
            return;
        }
        // Se seteaza perioada de asteptare dintre 2 comenzi diferite.
        else if(parametri[1].equalsIgnoreCase("perioada")){
            changePeriod(parametri);
        }
        else if(parametri[1].equalsIgnoreCase("testing")){
            setTestingMode(parametri[2]);
        }
        // Comanda nerecunoscuta.
        else{
            System.out.println("ERROR!!! Unknown argument '" + parametri[1] + "'. Nothing was done.");
        }
    }

    /**
     * Schimba perioada de asteptare dintre 2 comenzi diferite.
     * @param parametri Parametrii comenzii.
     */
    public static void changePeriod(String[] parametri){
        // Se retine numarul de miliseconde la care se seteaza perioada.
        Integer x = MetodeSpeciale.parseInt(parametri[2]);
        if(x == null || x < 0){
            System.out.println("ERROR!!! The period '" + parametri[2] + "' must be a positive integer. Nothing was " +
                    "done.");
            return;
        }
        period = x;
        System.out.println("The period was succesfullly set to " + x + ".");
    }
}
