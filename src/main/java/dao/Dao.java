package dao;

import java.util.List;
import java.util.Optional;

/**
 * It's data access object template
 * @param <T>
 * @param <ID>
 */
public interface Dao<T, ID> {

    Optional<T> find(ID id);

    List<T> findAll();

    void save(T t);

    void update(T t);

    void delete(T t);

}
