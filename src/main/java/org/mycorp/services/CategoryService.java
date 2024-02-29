package org.mycorp.services;

import org.mycorp.models_dao.CategoryDao;
import org.mycorp.repository.RepositoryCategory;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryService extends ServiceImpl<CategoryDao> {
    @Autowired
    public CategoryService(RepositoryCategory repositoryCategory){
        super(repositoryCategory);
    }

    @Override
    protected CategoryDao updateDao(CategoryDao newDao, CategoryDao daoToUpdate) {
        daoToUpdate.setCategory(newDao.getCategory());
        return daoToUpdate;
    }
}
