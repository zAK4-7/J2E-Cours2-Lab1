package dao;

/** DAO contract: provides a numeric value from a data source. */
public interface IDao {
    /** @return the value fetched from the data source */
    double getData();
}
