package org.mycorp.adapters;

import org.mycorp.models.CategoryDao;
import org.mycorp.models.NoteDao;
import org.mycorp.models.UserDao;
import org.mycorp.repository.RepositoryCategory;
import org.mycorp.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdapterNote extends AdapterImpl<CategoryDao, NoteDao>{

    RepositoryCategory repositoryCategory;
    NoteService service;

    @Autowired
    public AdapterNote(NoteService service, RepositoryCategory repositoryCategory){
        super(service, repositoryCategory);
    }

    @Override
    NoteDao setParentEntity(NoteDao entity, CategoryDao parentEntity) {
        entity.setCategory(parentEntity);
        return entity;
    }

    public boolean updateEntity(int id, int nested_id, NoteDao entity){
        Optional<CategoryDao> parentEntity = ((RepositoryCategory) repository).findById(nested_id);
        if(parentEntity.isPresent()) {
            NoteDao newEntity = setParentEntity(entity, parentEntity.get());
            return super.updateEntity(id, newEntity);
        }
        else return false;
    }
}
