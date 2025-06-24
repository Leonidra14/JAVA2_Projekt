package project.dao;

import java.util.Collection;
import java.util.Optional;

public interface CrudRepository <E, ID>{
    E save(E e);

    Optional<E> findById(ID id);

    boolean existsById(ID id);

    void deleteById(ID id);

    Collection<E> findAll();

}
