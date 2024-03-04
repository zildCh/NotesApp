package org.mycorp.adapters;

import org.mycorp.models.CategoryDao;
import org.mycorp.models.NoteDao;
import org.mycorp.repository.RepositoryCategory;
import org.mycorp.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdapterNote extends AdapterImpl<CategoryDao, NoteDao>{

    RepositoryCategory repository;
    NoteService service;

    @Autowired
    public AdapterNote(NoteService service, RepositoryCategory repository){
        super(service, repository);
    }

    @Override
    NoteDao setParentEntity(NoteDao entity, CategoryDao parentEntity) {
        entity.setCategory(parentEntity);
        return entity;
    }
}
