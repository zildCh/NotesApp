package org.mycorp.services.converter;

import org.mycorp.models_buisnes.UserDto;
import org.mycorp.models_dao.UserDao;

public class UserConverter extends Converter<UserDto, UserDao>{

    @Override
    public UserDto convertToDto(UserDao daoObj) {
        return new UserDto();
    }

    @Override
    public UserDao convertToDao(UserDto dtoObj) {
        //return new UserDao(dtoObj.getId(), dtoObj.getNickname(), passwordToHash("saa"));
        return null;
    }

    @Override
    public UserDao updateDao(UserDao daoObj, UserDto dtoObj) {
        daoObj.setNickname(dtoObj.getNickname());
        //daoObj.setPassword(passwordToHash(dtoObj.getPassword()));
        return daoObj;
    }
}
