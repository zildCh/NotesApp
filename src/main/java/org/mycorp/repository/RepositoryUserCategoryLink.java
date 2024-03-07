package org.mycorp.repository;

import org.mycorp.models.UserCategoryLink;

public interface RepositoryUserCategoryLink extends RepositoryInterface<UserCategoryLink>{
    UserCategoryLink findByUser_idAndCategory_id(int id_user, int id_category);
}
