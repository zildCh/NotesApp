package org.mycorp.repository;

import org.mycorp.models.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends RepositoryInterface<UserDao> {
     UserDao findByNickname(String nickname);
}

