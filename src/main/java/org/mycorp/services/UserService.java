package org.mycorp.services;

import org.mycorp.models.User;
import org.mycorp.models.UserCategoryLink;
import org.mycorp.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<User>{

    final AuthService authService;

    @Autowired
    public UserService(RepositoryUser repositoryUser, AuthService authService){
        super(repositoryUser);
        this.authService=authService;
    }

    @Override
    protected User updateDao(User newEntity, User entityToUpdate) {
        entityToUpdate.setNickname(newEntity.getNickname());
        entityToUpdate.setPassword(authService.passwordEncoding(newEntity.getPassword()));
        return entityToUpdate;
    }

    @Override
    public void create(User entity){
        entity.setPassword(authService.passwordEncoding(entity.getPassword()));
        super.create(entity);
    };

    @Override
    public User read(int id){
        User findUser = super.read(id);
        findUser.setNickname(null);
        findUser.setPassword(null);
        return findUser;
    }

    public User authorisation(User user){
        User findUser = ((RepositoryUser) repository).findByNickname(user.getNickname());
        User authorizedUser=authService.authentication(user,findUser);
        if(authorizedUser == null)
            return null;
        else{
            authorizedUser.setNickname(null);
            authorizedUser.setPassword(null);
            for (UserCategoryLink link : authorizedUser.getUserCategoryLinkList()){
                link.setId(0);
            }
            return authorizedUser;
        }
    };
}
