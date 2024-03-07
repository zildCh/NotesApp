package org.mycorp.adapters;

import org.mycorp.models.User;

public interface AdapterUser {
    boolean registration(User userToReg);
    boolean updateUser(int id, User userToUpdate);
    boolean deleteUser(int id);
    User authorisation(User userToAuth);
    User getUser(int id);
}
