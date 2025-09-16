import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clasa in care se retin metode statice fara clasa.
 */
public abstract class MetodeSpeciale {

    /**
     * La fel ca String.split, doar ca elimina stringurile vide din vectorul de stringuri.
     * @param str String-ul care este impartit in mai multe string-uri.
     * @param regex String-ul separator.
     * @return Array-ul de string-uri.
     */
    public static String[] split(String str, String regex){
        String[] ret = str.split(regex);
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(ret));
        arr.remove("");
        ret = arr.toArray(new String[0]);
        return ret;
    }

    /**
     * La fel ca Integer.parseInt, doar ca nu arunca NumberFormatException, ci returneaza null in caz de esec.
     * @param str String-ul din care se extrage intregul.
     * @return Intregul extras.
     */
    public static Integer parseInt(String str){
        try{
            return Integer.parseInt(str);
        } catch(NumberFormatException e){
            return null;
        }
    }

    /**
     * La fel ca Boolean.parseBoolean, doar ca nu arunca NumberFormatException, ci returneaza null in caz de esec.
     * @param str String-ul din care se extrage valoarea booleana.
     * @return Boolean-ul extras.
     */
    public static Boolean parseBoolean(String str){
        try{
            return Boolean.parseBoolean(str);
        } catch(NumberFormatException e){
            return null;
        }
    }

    /**
     * La fel ca Double.parseDouble, doar ca nu arunca NumberFormatException, ci returneaza null in caz de esec.
     * @param str String-ul din care se extrage numarul cu/fara zecimale.
     * @return Numarul extras din string.
     */
    public static Double parseDouble(String str) {
        try{
            return Double.parseDouble(str);
        } catch(NumberFormatException e){
            return null;
        }
    }

    /**
     * La fel ca Thread.sleep, doar ca nu arunca InterruptedException.
     * @param millis La fel ca la Thread.sleep(long millis)
     */
    public static void sleep(long millis){
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculeaza comisionul procentual in functie de client.
     * Daca clientul nu este nici persoana fizica, nici persoana juridica, atunci se poate ca cineva sa fi incercat
     * sa obtina un altfel de comision (posibil 0), caz pentru care comisionul procentual este 100 (taxa de hacking).
     * @param client Clientul primit ca parametru.
     * @return Comisionul procentual.
     */
    private static double comisionProcentual(Client client){
        if(client instanceof PersoanaFizica){
            if(client.getNrLicitatiiCastigate() < 5){
                return 20;
            }
            else{
                return 15;
            }
        }
        else if(client instanceof PersoanaJuridica){
            if(client.getNrLicitatiiCastigate() < 25){
                return 25;
            }
            else{
                return 10;
            }
        }
        System.out.println("ERROR!!! Tried to get comision to an unknown type of client:\n" + client);
        // Taxa de hacking per produs XD
        return 100;
    }

    /**
     * Adauga comision la o suma in functie de clientul respectiv.
     * @param client Clientul.
     * @param suma Suma.
     * @return Suma dupa adaugarea comisionului.
     */
    public static double cuComision(Client client, double suma) {
        return suma * (1 + comisionProcentual(client) / 100);
    }

    /**
     * Elimina comision dintr-o suma in functie de clientul respectiv.
     * @param client Clientul.
     * @param suma Suma.
     * @return Suma dupa extragerea comisionului.
     */
    public static double faraComision(Client client, double suma){
        return suma / (1 + comisionProcentual(client) / 100);
    }

    /**
     * Verifica daca un client isi poate permite sa plateasca un pret (cu tot cu comision) conform unei sume maxime
     * declarate.
     * @param client Clientul.
     * @param suma Suma declarata.
     * @param pret Pretul fara comision.
     * @return true in caz afirmativ, altfel false.
     */
    public static boolean afford(Client client, double suma, double pret){
        return suma >= cuComision(client, pret);
    }
}
