package org.mycorp.services;

import org.mycorp.models.CategoryDao;
import org.mycorp.models.NoteDao;
import org.mycorp.models.UserDao;
import org.mycorp.repository.RepositoryCategory;
import org.mycorp.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
