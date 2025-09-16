import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Custom ArrayList
 * @param <E> Tipul de date al elementelor.
 */
public class MyArrayList<E> extends ArrayList<E> {
    /**
     * Constructor fara parametri.
     */
    public MyArrayList(){
        super();
    }

    /**
     * Lacatul instantei.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Metoda custom toString.
     * @return String-urile elementelor separate prin cate un carecter sfarsit de linie.
     */
    public String toString(){
        lock.lock();
        StringBuilder str = new StringBuilder("[\n");
        try {
            for (E i : this) {
                str.append(i.toString()).append(",\n");
            }
        }catch (ConcurrentModificationException e){
            System.out.println("Concurent Acces treated!!!");
            MetodeSpeciale.sleep(500);
            for (E i : this) {
                str.append(i.toString()).append(",\n");
            }
        }
        str.append("]");
        lock.unlock();
        return str.toString();
    }

    /**
     *  Functie ce gaseste elementul cautat in lista si-l returneaza. Folositoare la gasirea unui element cu anumite
     *  insusiri. Este necesar ca obiectul cautat sa apartina unei clase cu o metoda custom 'public boolean equals(E)'
     *  in care doar anumite insusiri (un identificator unic, de exemplu) sunt verificate.
     * @param o Obiectul cu insusirile obiectului cautat in lista.
     * @return Elementul gasit sau null.
     */
    public E getElem(E o){
        for(E i : this){
            if(o.equals(i)){
                return i;
            }
        }
        return null;
    }

    /**
     * Functie ce returneaza un element aleator din lista.
     * @return Element extras aleator din lista sau null daca lista e goala.
     */
    public E getRandomElem(){
        // Daca lista este goala se returneaza null.
        if(this.size() == 0){
            return null;
        }
        // Se alege un element la intamplare.
        return this.get((new Random()).nextInt(this.size()));
    }

    /**
     * Restrictioneaza accesul altor thread-uri la instanta curenta.
     */
    protected void lock(){
        lock.lock();
    }

    /**
     * Reda accesul altor thread-uri la instanta curenta.
     */
    protected void unlock() {
        lock.unlock();
    }
}
