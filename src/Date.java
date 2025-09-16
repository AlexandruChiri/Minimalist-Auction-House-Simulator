/**
 * Clasa ce retine o data calendaristica in format zz.ll.aaaa.
 */
public class Date {
    /**
     * Constructor fara parametri.
     */
    public Date(){
    }

    /**
     * Ziua
     */
    private int zi;
    /**
     * Luna
     */
    private int luna;
    /**
     * Anul
     */
    private int an;

    /**
     * Verifica daca un an este bisec.
     * @param an Anul
     * @return true in caz afirmativ, altfel false.
     */
    private static boolean anBisect(int an){
        if(an % 400 == 0){
            return true;
        }
        if(an % 100 == 0){
            return false;
        }
        return an % 4 == 0;
    }

    /**
     * Verifica daca o data este valida sau fictiva.
     * @return true daca data este valida, false daca este fictiva.
     */
    public boolean valida(){
        if(zi < 1 || luna < 1 || an < 1 || luna > 12){
            return false;
        }
        if(luna == 1 || luna == 3 || luna == 5 || luna == 7 || luna == 8 || luna == 10 || luna == 12){
            return zi <= 31;
        }
        else if(luna == 2){
            if(zi > 29){
                return false;
            }
            return zi != 29 || Date.anBisect(an);
        }
        else {
            return zi <= 30;
        }
    }

    /**
     * Builder style setter.
     * Seteaza data curenta la data din string-ul 'data' sau returneaza null.
     * @param data String-ul care contine data in format zz.ll.aaaa
     * @return Data curenta sau null in caz de esec.
     */
    public Date setToDate(String data){
        String[] ziLunaAn = data.split("\\.");
        Integer zi, luna, an;
        if(ziLunaAn.length < 3){
            return null;
        }
        zi = MetodeSpeciale.parseInt(ziLunaAn[0]);
        if(zi == null){
            return null;
        }
        luna = MetodeSpeciale.parseInt(ziLunaAn[1]);
        if(luna == null){
            return null;
        }
        an = MetodeSpeciale.parseInt(ziLunaAn[2]);
        if(an == null){
            return null;
        }
        this.zi = zi;
        this.luna = luna;
        this.an = an;
        return this;
    }

    /**
     * Metoda toString.
     * @return Data in format string.
     */
    @Override
    public String toString() {
        return zi + "." + luna + "." + + an;
    }
}
