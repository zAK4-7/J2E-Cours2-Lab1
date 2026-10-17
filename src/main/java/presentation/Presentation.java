package presentation;

import dao.DaoImpl;
import metier.MetierImpl;

/** Static instantiation (tight coupling) - baseline matching the UML diagram. */
public class Presentation {

    public static void main(String[] a) {
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao); // static DI
        System.out.println("Result = " + metier.calcul());
    }
}
