package DAOs;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T, ID> {
    T create(T entity) throws SQLException;
    T update(T entity) throws SQLException;
    boolean delete(ID id) throws SQLException;
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
}