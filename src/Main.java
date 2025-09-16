import java.util.Scanner;


/**
 * Clasa principala a proiectului.
 */
public class Main {
    /**
     * Metoda apelata la inceperea executiei.
     * @param args Argumentele din linia de comanda.
     */
    public static void main(String[] args){
        // Se obtine scanner de pentru stdin.
        Scanner s;
        try{
            s = new Scanner(System.in);
            Command.Consola(s);
        }catch(Exception e){
            System.out.println("Failed to open input stream for System.in/stdin.");
        }
    }
}
