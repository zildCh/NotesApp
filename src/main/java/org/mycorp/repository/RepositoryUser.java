package org.mycorp.repository;

import org.mycorp.models_dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends RepositoryInterface<UserDao> {

     UserDao findByNickname(String nickname);
}

