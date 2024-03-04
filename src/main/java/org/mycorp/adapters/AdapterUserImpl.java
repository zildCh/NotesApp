package org.mycorp.adapters;

import org.mycorp.models.UserDao;
import org.mycorp.repository.RepositoryCategory;
import org.mycorp.services.NoteService;
import org.mycorp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdapterUserImpl implements AdapterUser {

    final UserService service;

    @Autowired
    public AdapterUserImpl(UserService service){
        this.service=service;
    }


    @Override
    public void registration(UserDao user) {
        service.create(user);
    }

    @Override
    public boolean updateUser(int id, UserDao userToUpdate) {
        return service.update(userToUpdate, id);
    }

    @Override
    public boolean deleteUser(int id) {
        return service.delete(id);
    }

    @Override
    public UserDao getUser(UserDao userToAuth) {
        return service.read(userToAuth);
    }
}
