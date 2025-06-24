package project.business;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.dao.CrudRepository;
import project.domain.BasicEntity;

import java.util.Collection;
import java.util.Optional;

public  abstract class AbstractCrudService <E extends BasicEntity<K>, K>{

    protected final CrudRepository<E,K> repository;

    protected AbstractCrudService(CrudRepository<E,K> repository){
        this.repository = repository;
    }


    public E create(E entity) throws RuntimeException {
        //overuji duplikat
        if(entity.getId() != null && repository.existsById(entity.getId())){
            throw new RuntimeException("duplikat");
        }
        return repository.save(entity);
    }

    public E update(E entity) throws RuntimeException{
        if(repository.existsById(entity.getId())){
            return repository.save(entity);
        } else {
            throw new RuntimeException("klic neexistuje");
        }
    }

    public void deleteById(K id){
        repository.deleteById(id);
    }

    public Optional<E> readById(K id){
        if(id == null)
            return Optional.empty();
        return repository.findById(id);
    }


    public Collection<E> readAll(){
        return repository.findAll();
    }
}