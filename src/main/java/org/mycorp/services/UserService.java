package org.mycorp.services;

import org.mycorp.models.UserDao;
import org.mycorp.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserDao>{

    final AuthService authService;

    @Autowired
    public UserService(RepositoryUser repositoryUser, AuthService authService){
        super(repositoryUser);
        this.authService=authService;
    }

    @Override
    protected UserDao updateDao(UserDao newDao, UserDao daoToUpdate) {
        daoToUpdate.setNickname(newDao.getNickname());
        daoToUpdate.setPassword(authService.passwordEncoding(newDao.getPassword()));
        return daoToUpdate;
    }

    @Override
    public void create(UserDao daoObj){
        authService.passwordEncoding(daoObj.getPassword());
        super.create(daoObj);
    };


    public UserDao read(UserDao user){
        UserDao findUser = ((RepositoryUser) repository).findByNickname(user.getNickname());
        UserDao authorizedUser=authService.authorisation(user,findUser);
        if(authorizedUser == null)
            return null;
        else{
            authorizedUser.setNickname(null);
            authorizedUser.setPassword(null);
            return authorizedUser;
        }
    };


   /* public void registration(UserDao user) {
        user.setPassword(passwordEncoding(user.getPassword()));
        create(user);
    }

    public UserDao authorisation(UserDao user){
        UserDao findUser = ((RepositoryUser) repository).findByNickname(user.getNickname());
        if(passwordEncoder.matches(user.getPassword(), findUser.getPassword())){
            findUser.setNickname(null);
            findUser.setPassword(null);
            return findUser;
        }
        else
            return null;
    }

    private String passwordEncoding(String password){
        return passwordEncoder.encode(password);
    }*/
}
