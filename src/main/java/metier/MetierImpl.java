package metier;

import dao.IDao;

/** Concrete IMetier impl. Depends on IDao (injected via setter). */
public class MetierImpl implements IMetier {

    // Injectable dependency
    private IDao dao;

    /** Setter used for DI (called manually or via reflection). */
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        if (dao == null) {
            throw new IllegalStateException("DAO not injected.");
        }
        double base = dao.getData();
        double res = base * 2; // business rule: double the value
        System.out.println("[METIER] Calcul done. Result: " + res);
        return res;
    }
}
