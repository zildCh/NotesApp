package org.mycorp.services;

import org.mycorp.models.UserCategoryLink;
import org.mycorp.repository.RepositoryInterface;
import org.mycorp.repository.RepositoryUserCategoryLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCategoryLinkService extends ServiceImpl<UserCategoryLink>{

    @Autowired
    UserCategoryLinkService(RepositoryInterface<UserCategoryLink> rep) {
        super(rep);
    }

    @Override
    protected UserCategoryLink updateDao(UserCategoryLink newDao, UserCategoryLink daoToUpdate) {
        daoToUpdate.setCategory(newDao.getCategory());
        daoToUpdate.setUser(newDao.getUser());
        return daoToUpdate;
    }

    public UserCategoryLink findLink(int user_id, int category_id){
        return  ((RepositoryUserCategoryLink)repository).findByUser_idAndCategory_id(user_id, category_id);
    }
}
