package org.mycorp.adapters;

import org.mycorp.models.AbstractEntity;
import org.mycorp.repository.RepositoryInterface;
import org.mycorp.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


public abstract class AdapterImpl<C, T extends AbstractEntity> implements Adapter<T> {

    RepositoryInterface<C> repository;
    Service<T> service;


    @Autowired
    public AdapterImpl(Service<T> service, RepositoryInterface<C> repository){
        this.service=service;
        this.repository = repository;
    }


    abstract T setParentEntity(T entity, C parentEntity);

    @Override
    public boolean createEntity(int idParentEntity, T entity) {
        Optional<C> parentEntity = repository.findById(idParentEntity);
        if(parentEntity.isPresent()) {
            T newEntity = setParentEntity(entity, parentEntity.get());
            service.create(newEntity);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean updateEntity(int id, T entity) {
        return service.update(entity, id);
    }

    @Override
    public boolean deleteEntity(int id) {
        return service.delete(id);
    }


}