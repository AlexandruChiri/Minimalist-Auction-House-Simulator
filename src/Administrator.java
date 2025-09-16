/**
 * Clasa pentru instantele administrator.
 */
public class Administrator extends Angajat {
    /**
     * Creeeaza un administrator cu id.
     */
    public Administrator() {
    }

    /**
     * Metoda toString.
     * @return String-ul administratorului
     */
    @Override
    public String toString() {
        return "Administrator{" + super.toString() +
                '}';
    }

    /**
     * Adauga un produs in lista de produse.
     * @param produs Produsul pe care-l adauga.
     */
    public void addProdus(Produs produs) {
        if(produs == null){
            System.out.println("WARNING!!! There was an attemp to add a null product. No product was added.");
            return;
        }
        CasaDeLicitatii.getInstance().addProdus(produs, this);
    }

    /**
     * Creeaza un produs.
     * @param parametri Descrierea conform careia creeaza produsul.
     * @return Produsul creat.
     */
    public Produs createProdus(String[] parametri){
        // Prea putini parametri? Respins!!!
        if(parametri.length < 8){
            System.out.println("WARNING!!! Not enough arguments for the description of any product. No product was added.");
            return null;
        }
        Produs produs = null;
        Integer an, pretMinim;
        // Se retin numele si tipul de produs.
        String nume = parametri[3], tip = parametri[2];
        an = MetodeSpeciale.parseInt(parametri[6]);
        // Se retine anul.
        if(an == null) {
            System.out.println("WARNING!!! Non-integer value " + parametri[6] + " introduced for the field 'an'.");
            return null;
        }
        // Se retine pretul minim.
        pretMinim = MetodeSpeciale.parseInt(parametri[7]);
        if(pretMinim == null) {
            System.out.println("WARNING!!! Non-integer value " + parametri[7] +
                    " introduced for the minimum price field.");
            return null;
        }
        // Se retine produsul in functie de tipul specificat.
        if(tip.equalsIgnoreCase("tablou")){
            // Se retin culorile.
            Culori culori = Culori.parseCulori(parametri[5]);
            if(culori == null){
                System.out.println("WARNING!!! Culoare " + parametri[5] + " necunoscuta. Esec adaugare tablou nou.");
                return null;
            }
            // Se creeaza tabloul.
            produs = new Tablou()
                    .setNumePictor(parametri[4])
                    .setCulori(culori);
        }
        else if(tip.equalsIgnoreCase("mobila")){
            // Se creeaza mobila.
            produs = new Mobila()
                    .setTip(parametri[4])
                    .setMaterial(parametri[5]);
        }
        else if(tip.equalsIgnoreCase("bijuterie")){
            Boolean piatraPretioasa;
            piatraPretioasa = MetodeSpeciale.parseBoolean(parametri[5].toLowerCase());
            if(piatraPretioasa == null) {
                System.out.println("WARNING!!! Truth value '" + parametri[5] + "' converted to '" +
                        parametri[5].toLowerCase() + "' is not a valid boolean value for field 'piatraPretioasa'." +
                        " No product was added.");
                return null;
            }
            // Se creeaza bijuterie.
            produs = new Bijuterie()
                    .setMaterial(parametri[4])
                    .setPiatraPretioasa(piatraPretioasa);
        }
        // Daca tipul produsului nu este cunoscut.
        else{
            System.out.println("WARNING!!! Unknown product type '" + tip + "'. No product was added");
            return null;
        }
        // Se seteaza anul, numele si pretul minim ale produsului.
        produs
                .setAn(an)
                .setPretMinim(pretMinim)
                .setNume(nume);
        // Se returneaza produsul.
        return produs;
    }
}