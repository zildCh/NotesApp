package org.mycorp.adapters;

import org.mycorp.models.UserDao;
import org.mycorp.repository.RepositoryCategory;
import org.mycorp.services.NoteService;
import org.mycorp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class AdapterUserImpl implements AdapterUser {

    final UserService service;

    @Autowired
    public AdapterUserImpl(UserService service){
        this.service=service;
    }


    @Override
    public void registration(UserDao userToReg) {
        service.registration(userToReg);
    }

    @Override
    public boolean updateUser(int id, UserDao userToUpdate) {
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public UserDao authorisation(UserDao userToAuth) {
        return null;
    }
}
