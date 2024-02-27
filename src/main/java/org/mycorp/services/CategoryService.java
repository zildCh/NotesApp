package org.mycorp.services;

import org.mycorp.models_buisnes.CategoryDto;
import org.mycorp.models_buisnes.UserDto;
import org.mycorp.models_dao.CategoryDao;
import org.mycorp.models_dao.UserDao;
import org.mycorp.repository.RepositoryCategory;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryService extends ServiceImpl<CategoryDao, CategoryDto> {
    @Autowired
    public CategoryService(RepositoryCategory repositoryCategory){
        super(repositoryCategory);
    }

    @Override
    protected CategoryDao convertToDAO(CategoryDto dtoObj) {
        return new CategoryDao(dtoObj.getId(), dtoObj.getUserDto(), dtoObj.getCategory());
    }

    @Override
    protected CategoryDto convertToDTO(CategoryDao daoObj) {
        return new CategoryDto(daoObj.getId(), daoObj.getUser(), daoObj.getCategory());
    }

    @Override
    protected CategoryDao updateDAO(CategoryDao daoObj, CategoryDto dtoObj) {
        daoObj.setCategory(dtoObj.getCategory());
        return daoObj;
    }
}
