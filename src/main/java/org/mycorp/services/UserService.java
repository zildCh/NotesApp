package org.mycorp.services;

import org.mycorp.models.User;
import org.mycorp.models.UserCategoryLink;
import org.mycorp.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserService extends ServiceImpl<User> {

    final AuthService authService;

    @Autowired
    public UserService(RepositoryUser repositoryUser, AuthService authService) {
        super(repositoryUser);
        this.authService = authService;
    }

    @Override
    protected User updateEntity(@NotNull User newEntity, @NotNull User entityToUpdate) {
        entityToUpdate.setNickname(newEntity.getNickname());
        entityToUpdate.setPassword(newEntity.getPassword());
        return entityToUpdate;
    }

    @Override
    public void create(@NotNull User entity) throws Exception {
        if(entity.getNickname()==null || entity.getPassword()==null)
            throw new Exception("Invalid input data");

        if (findUserByNickname(entity.getNickname()) != null)
            throw new Exception("Duplicate nickname");

        entity.setPassword(authService.passwordEncoding(entity.getPassword()));
        super.create(entity);
    }

    @Override
    public User read(int id) {
        User findUser = super.read(id);
        if (findUser == null)
            return null;
        else
            return userToJSON(findUser);
    }

    @Override
    public boolean update(@NotNull User newEntity, int id) throws Exception {
        if(newEntity.getNickname()==null || newEntity.getPassword()==null)
            throw new Exception("Invalid input data");

        if (findUserByNickname(newEntity.getNickname()) != null)
            throw new Exception("Modified to Duplicate nickname");

        newEntity.setPassword(authService.passwordEncoding(newEntity.getPassword()));
        return super.update(newEntity, id);
    }


    public User authorisation(@NotNull User user) {
        if(user.getNickname()==null || user.getPassword()==null)
            return null;

        User findUser = findUserByNickname(user.getNickname());
        if (findUser == null)
            return null;

        User authorizedUser = authService.authentication(user, findUser);
        if (authorizedUser == null)
            return null;
        else
            return userToJSON(authorizedUser);
    }


    private User findUserByNickname(String nickname) {
        return ((RepositoryUser) repository).findByNickname(nickname);
    }


    private User userToJSON(@NotNull User user) {
        user.setNickname(null);
        user.setPassword(null);

        for (UserCategoryLink link : user.getUserCategoryLinkList())
            link.setId(0);

        return user;
    }
}
