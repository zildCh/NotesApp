package org.mycorp.adapters;

import org.mycorp.models.User;
import org.mycorp.models.UserCategoryLink;
import org.mycorp.services.CategoryService;
import org.mycorp.services.UserCategoryLinkService;
import org.mycorp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface AdapterUser {
    boolean registration(User userToReg);
    boolean updateUser(int id, User userToUpdate);
    boolean deleteUser(int id);
    User authorisation(User userToAuth);
    User getUser(int id);

    @Component
    class AdapterUserImpl implements AdapterUser {
        final CategoryService categoryService;
        final UserService service;

        @Autowired
        public AdapterUserImpl(UserService service, CategoryService categoryService){
            this.service=service;
            this.categoryService=categoryService;
        }


        @Override
        public boolean registration(@NotNull User user) {
            try {
                List<UserCategoryLink> linkList = new ArrayList<>();

                for (int i = 1; i < 7; i++) {
                    linkList.add(new UserCategoryLink(user, categoryService.read(i)));
                }

                user.setUserCategoryLinkList(linkList);
                service.create(user);
                return true;
            }catch (Exception e){
                return false;
            }
        }

        @Override
        public boolean updateUser(int id, @NotNull User userToUpdate) {
            try {
                return service.update(userToUpdate, id);
            }catch (Exception e){
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
        public User authorisation(@NotNull User userToAuth) {
            return service.authorisation(userToAuth);
        }
    }
}
