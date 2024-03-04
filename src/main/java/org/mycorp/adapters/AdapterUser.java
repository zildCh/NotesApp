package org.mycorp.adapters;

import org.mycorp.models.UserDao;

public interface AdapterUser {
    void registration(UserDao userToReg);
    boolean updateUser(int id, UserDao userToUpdate);
    boolean deleteUser(int id);
    UserDao authorisation(UserDao userToAuth);
}
