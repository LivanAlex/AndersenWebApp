package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {

    Optional<T> find(ID id);

    List<T> findAll();

    boolean save(T t);

    boolean update(T t);

    boolean delete(T t);

}
