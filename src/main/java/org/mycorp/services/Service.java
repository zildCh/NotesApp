package org.mycorp.services;

import org.mycorp.repository.RepositoryInterface;
import org.mycorp.services.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Service<C> {
    void create(C dtoObj);
    List<C> readAll();
    C read(int id);
    boolean update(C dtoObj, int id);
    boolean delete(int id);
}

abstract class ServiceImpl<T,C> implements Service<C>{


    RepositoryInterface<T> repository;
    Converter<C,T> converter;

    @Autowired
    ServiceImpl(RepositoryInterface<T> rep, Converter<C,T> converter){
        this.repository=rep;
        this.converter=converter;
    };

    protected abstract T convertToDAO(C dtoObj);
    protected abstract C convertToDTO(T daoObj);
    protected abstract T updateDAO(T daoObj, C dtoObj);

    @Override
    public void create(C dtoObj){
        T daoObject=converter.convertToDao(dtoObj);
        repository.save(daoObject);
    };

    @Override
    public List<C> readAll(){
        List<T> allDAOs = repository.findAll();
        List<C> allDTOs = new ArrayList<C>();
        for(T dao : allDAOs){
            C dto = converter.convertToDto(dao);
            allDTOs.add(dto);
        }
        return allDTOs;
    };

    @Override
    public C read(int id){
        Optional<T> daoObject = repository.findById(id);
        return converter.convertToDto(daoObject.get());
    };

    @Override
    public boolean update(C dtoObj, int id){
        Optional<T> daoObject = repository.findById(id);
        if (daoObject.isPresent()){
            T updatedDAO = converter.updateDao(daoObject.get(), dtoObj);
            repository.save(updatedDAO);
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