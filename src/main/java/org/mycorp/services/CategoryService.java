package org.mycorp.services;

import org.mycorp.models.Category;
import org.mycorp.repository.RepositoryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class CategoryService extends ServiceImpl<Category> {

    @Autowired
    public CategoryService(RepositoryCategory repositoryCategory){
        super(repositoryCategory);
    }

    @Override
    protected Category updateEntity(@NotNull Category newEntity, @NotNull Category entityToUpdate) {
        entityToUpdate.setCategory(newEntity.getCategory());
        return entityToUpdate;
    }
}
