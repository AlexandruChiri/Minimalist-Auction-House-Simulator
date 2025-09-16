/**
 * Clasa pentru primirea propunerii unui client pentru o licitatie.
 */
public class MkPropunere extends Thread {

    /**
     * String-ul pentru descrierea comenzii.
     */
    private final String[] parametri;
    /**
     * Clientul care propune o suma in cadrul unei licitatii.
     */
    private final Client client;

    /**
     * Constructor cu parametri.
     * @param parametri String-ul pentru descrierea comenzii.
     * @param client Clientul care propune o suma in cadrul unei licitatii.
     */
    public MkPropunere(String[] parametri, Client client) {
        this.parametri = parametri;
        this.client = client;
    }

    /**
     * Metoda care executa propunerea unei sume de catre un client.
     */
    public void run(){
        // Parametri insuficienti? Nu se face nimic.
        if(parametri.length < 4){
            System.out.println("WARNING!!! Not enough arguments for a proposal. Nothing was done.");
            return;
        }
        Double suma;
        // Se retine dependenta broker-licitatie a clientului.
        LicBrok licBrok = LicBrok.licitatieSiBrokerDin(parametri, client);
        if(licBrok == null){
            return;
        }
        // Se retine broker-ul.
        Broker broker = licBrok.getBroker();
        // Se retine suma propusa.
        suma = MetodeSpeciale.parseDouble(parametri[4]);
        if(suma == null || suma < 0){
            System.out.println("ERROR!!! Unexpected argument '" + parametri[4] + ". Must be a positive sum." +
                    " Nothing was done.");
            return;
        }
        // Se face transmiterea sumei de catre broker.
        broker.transmite(licBrok.getLicitatie(), client, suma);
    }
}
