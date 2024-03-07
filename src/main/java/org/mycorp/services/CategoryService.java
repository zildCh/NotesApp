package org.mycorp.services;

import org.mycorp.models.Category;
import org.mycorp.repository.RepositoryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends ServiceImpl<Category> {

    @Autowired
    public CategoryService(RepositoryCategory repositoryCategory){
        super(repositoryCategory);
    }

    @Override
    protected Category updateDao(Category newEntity, Category entityToUpdate) {
        entityToUpdate.setCategory(newEntity.getCategory());
        return entityToUpdate;
    }
}
