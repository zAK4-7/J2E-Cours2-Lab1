package dao;

/** Concrete IDao impl. Simulates a DB / remote call with a fixed value. */
public class DaoImpl implements IDao {

    @Override
    public double getData() {
        System.out.println("[DAO] Fetching data from the source...");
        return 100.0;
    }
}
