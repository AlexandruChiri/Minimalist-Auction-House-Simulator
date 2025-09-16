/**
 * Clasa ce contine metode pentru codul duplicat.
 */
public abstract class CodDuplicat {
    /**
     * Metoda ce gaseste dependenta broker-licitatie a clientului in functie de comanda.
     * @param parametri Parametrii comenzii.
     * @param client Clientul.
     * @param comanda Comanda (quit sau skip).
     * @return Dependenta broker-licitatie sau null in cazul in care clientul nu se afla in licitatia respectiva.
     */
    public static LicBrok bringLicBrok(String[] parametri, Client client, String comanda) {
        // Prea putini parametri? Nu se face nimic.
        if(parametri.length < 4){
            System.out.println("WARNING!!! Not enough arguments for command '" + comanda + "'. Nothing was done.");
            return null;
        }
        // Se se cauta dependenta dintre acest client si broker-ul care ii mediaza licitatia.
        return LicBrok.licitatieSiBrokerDin(parametri, client);
    }

    /**
     * Metoda ce obtine un broker pentru un client ce solicita inceperea/intrarea intr-o licitatie.
     * @param suma Suma maxima pe care clientul doreste sa o plateasca.
     * @param instanta Instanta casei de licitatii.
     * @param produs Produsul pentru care clientul liciteaza.
     * @param client Clientul
     * @return Broker-ul gasit.
     */
    public static Broker getBrokerForClient(Double suma, CasaDeLicitatii instanta, Produs produs, Client client) {
        // Clientul nu poate intra in licitatie daca nu doreste sa ofere macar pretul minim al produsului cu tot
        // cu comision.
        if(!MetodeSpeciale.afford(client, suma, produs.getPretMinim())){
            System.out.println("WARNING!!! You can't buy this product with less than " +
                    MetodeSpeciale.cuComision(client, produs.getPretMinim()) +
                    ", thus you can't join this auction with a maximum sum lower than that. Nothing was done.");
            return null;
        }
        // Se verifica daca se poate incepe o licitatie noua.
        if(!instanta.checkNewAuctionPossibility()){
            return null;
        }
        // Se gaseste un broker pentru client.
        return instanta.getBrokers().getRandomElem();
    }
}
