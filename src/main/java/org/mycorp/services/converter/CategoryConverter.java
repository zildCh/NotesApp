package org.mycorp.services.converter;

import org.mycorp.models_buisnes.CategoryDto;
import org.mycorp.models_dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryConverter extends Converter<CategoryDto, CategoryDao>{

    UserConverter userConverter;

    @Autowired
    CategoryConverter(UserConverter userConverter){
        this.userConverter=userConverter;
    }

    @Override
    public CategoryDto convertToDto(CategoryDao daoObj) {
        return new CategoryDto(daoObj.getId(), userConverter.convertToDto(daoObj.getUser()), daoObj.getCategory());
    }

    @Override
    public CategoryDao convertToDao(CategoryDto dtoObj) {
        return new CategoryDao(dtoObj.getId(), userConverter.convertToDao(dtoObj.getUserDto()), dtoObj.getCategory());
    }

    @Override
    public CategoryDao updateDao(CategoryDao daoObj, CategoryDto dtoObj) {
        daoObj.setCategory(dtoObj.getCategory());
        return daoObj;
    }
}
