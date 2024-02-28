package org.mycorp.services;

import org.mycorp.models_buisnes.CategoryDto;
import org.mycorp.models_buisnes.UserDto;
import org.mycorp.models_dao.CategoryDao;
import org.mycorp.models_dao.UserDao;
import org.mycorp.repository.RepositoryCategory;
import org.mycorp.services.converter.CategoryConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryService extends ServiceImpl<CategoryDao, CategoryDto> {
    @Autowired
    public CategoryService(RepositoryCategory repositoryCategory, CategoryConverter converter){
        super(repositoryCategory, converter);
    }
}
