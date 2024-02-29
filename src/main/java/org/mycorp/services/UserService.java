package org.mycorp.services;

import org.mycorp.models_dao.UserDao;
import org.mycorp.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService extends ServiceImpl<UserDao>{

    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RepositoryUser repositoryUser, BCryptPasswordEncoder passwordEncoder){
        super(repositoryUser);
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    protected UserDao updateDao(UserDao newDao, UserDao daoToUpdate) {
        daoToUpdate.setNickname(newDao.getNickname());
        daoToUpdate.setPassword(newDao.getPassword());
        return daoToUpdate;
    }

    public void registration(UserDao user){
        user.setPassword(passwordEncoding(user.getPassword()));
        create(user);
    }

    public UserDao authorisation(UserDao user){
        UserDao findUser = ((RepositoryUser) repository).findByNickname(user.getNickname());
        if(passwordEncoder.matches(user.getPassword(), findUser.getPassword())){
            findUser.setPassword("********");
            return findUser;
        }
        else
            return null;
    }

    private String passwordEncoding(String password){
        return passwordEncoder.encode(password);
    }
}
