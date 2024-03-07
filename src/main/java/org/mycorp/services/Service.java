package org.mycorp.services;

import org.mycorp.models.AbstractEntity;
import org.mycorp.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface Service<T extends AbstractEntity> {

    void create(T entity);
    List<T> readAll();
    T read(int id);
    boolean update(T entity, int id);
    boolean delete(int id);
}

abstract class ServiceImpl<T extends AbstractEntity> implements Service<T>{

    final RepositoryInterface<T> repository;

    @Autowired
    ServiceImpl(RepositoryInterface<T> rep){
        this.repository=rep;
    };

    abstract protected T updateDao(T newEntity, T entityToUpdate);

    @Override
    public void create(T entity){
        repository.save(entity);
    };

    @Override
    public List<T> readAll(){
        List<T> allEntity = repository.findAll();
        return repository.findAll();
    };

    @Override
    public T read(int id){
        Optional<T> entity = repository.findById(id);
        return entity.orElse(null);
    };

    @Override
    public boolean update(T newEntity, int id){
        Optional<T> entityToUpdate = repository.findById(id);
        if (entityToUpdate.isPresent()){
            T updatedEntity = updateDao(newEntity, entityToUpdate.get());
            repository.save(updatedEntity);
            return true;
        }
        return false;
    };

    @Override
    public boolean delete(int id){
        if(repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    };
}