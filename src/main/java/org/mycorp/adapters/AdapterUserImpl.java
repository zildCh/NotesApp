package org.mycorp.adapters;

import org.mycorp.models.UserCategoryLink;
import org.mycorp.models.User;
import org.mycorp.services.CategoryService;
import org.mycorp.services.UserCategoryLinkService;
import org.mycorp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdapterUserImpl implements AdapterUser {
    final UserCategoryLinkService userCategoryLinkService;
    final CategoryService categoryService;
    final UserService service;

    @Autowired
    public AdapterUserImpl(UserService service, UserCategoryLinkService userCategoryLinkService, CategoryService categoryService){
        this.service=service;
        this.userCategoryLinkService=userCategoryLinkService;
        this.categoryService=categoryService;
    }


    @Override
    public boolean registration(User user) {
        try {
            List<UserCategoryLink> linkList = new ArrayList<>();

            for (int i = 1; i < 6; i++) {
                linkList.add(new UserCategoryLink(user, categoryService.read(i)));
            }

            user.setUserCategoryLinkList(linkList);
            service.create(user);
            return true;
        }catch (RuntimeException e){
            return false;
        }
    }

    @Override
    public boolean updateUser(int id, User userToUpdate) {
        try {
            return service.update(userToUpdate, id);
        }catch (RuntimeException e){
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        return service.delete(id);
    }

    @Override
    public User getUser(int id) {
        return service.read(id);
    }

    @Override
    public User authorisation(User userToAuth) {
        return service.authorisation(userToAuth);
    }
}
