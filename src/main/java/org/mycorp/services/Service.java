package org.mycorp.services;

import org.mycorp.models.UserDao;
import org.mycorp.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Service<T> {

    void create(T daoObj);
    List<T> readAll();
    T read(int id);
    boolean update(T daoObj, int id);
    boolean delete(int id);
}



abstract class ServiceImpl<T> implements Service<T>{

    RepositoryInterface<T> repository;

    @Autowired
    ServiceImpl(RepositoryInterface<T> rep){
        this.repository=rep;
    };

    abstract protected T updateDao(T newDao, T daoToUpdate);

    @Override
    public void create(T daoObj){
        repository.save(daoObj);
    };

    @Override
    public List<T> readAll(){
        List<T> allDAOs = repository.findAll();
        return repository.findAll();
    };

    @Override
    public T read(int id){
        Optional<T> daoObject = repository.findById(id);
        return daoObject.orElse(null);
    };

    @Override
    public boolean update(T newDao, int id){
        Optional<T> daoToUpdate = repository.findById(id);
        if (daoToUpdate.isPresent()){
            T updatedDAO = updateDao(newDao, daoToUpdate.get());
            repository.save(updatedDAO);
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