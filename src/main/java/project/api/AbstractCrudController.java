package project.api;


import project.business.AbstractCrudService;
import project.domain.BasicEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractCrudController<E extends BasicEntity<ID>, D, ID> {
    //volnai bussiness
    protected AbstractCrudService<E, ID> service;
    //prevod pro skryvani atributu a naopak
    protected Function<E, D> toDtoConverter;
    protected Function<D, E> toEntityConverter;

    //konstruktor
    public AbstractCrudController(AbstractCrudService<E, ID> service, Function<E, D> toDtoConverter,
                                  Function<D, E> toEntityConverter){
        this.service = service;
        this.toDtoConverter = toDtoConverter;
        this.toEntityConverter = toEntityConverter;
    }

    //vztvoreni
    @PostMapping
    public D create(@RequestBody D e){
        E entity = toEntityConverter.apply(e);
        if(service.readById(entity.getId()).isPresent())
            throw new RuntimeException("Instance jiz existuje");
        return toDtoConverter.apply(service.create(entity));
    }

    //ziskani entity
    @GetMapping("/{id}")
    public D readById(@PathVariable ID id){
        var tmp = service.readById(id);
        if(tmp.isEmpty())
            throw new RuntimeException("Instance neexistuje");
        return toDtoConverter.apply(tmp.get());
    }



    //ziskani vseho
    @GetMapping
    public Collection<D> readAll(){
        return service.readAll().stream().map(toDtoConverter).collect(Collectors.toList());
    }

    //upload
    @PutMapping("/{id}")
    public void update(@RequestBody D e, @PathVariable ID id){
        var tmp = service.readById(id);
        if(tmp.isEmpty())
            throw new RuntimeException("Instance neexistuje");
        var entity = toEntityConverter.apply(e);
        if(entity.getId() != id)
            throw new RuntimeException("ID se neshoduje");
        service.update(entity);
    }

    //delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id){
        var tmp = service.readById(id);
        if(tmp.isEmpty())
            throw new RuntimeException("Instance neexistuje");
        service.deleteById(id);
    }
}
