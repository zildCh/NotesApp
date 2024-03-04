package org.mycorp.adapters;

import org.mycorp.models.CategoryDao;
import org.mycorp.models.UserDao;
import org.mycorp.repository.RepositoryUser;
import org.mycorp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdapterCategory extends AdapterImpl<UserDao, CategoryDao>{

    RepositoryUser repository;
    CategoryService service;

    @Autowired
    public AdapterCategory(CategoryService service, RepositoryUser repository){
        super(service, repository);
    }

    @Override
    CategoryDao setParentEntity(CategoryDao entity, UserDao parentEntity) {
        entity.setUserDao(parentEntity);
        return entity;
    }
}
