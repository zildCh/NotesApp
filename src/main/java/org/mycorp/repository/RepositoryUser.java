package org.mycorp.repository;

import org.mycorp.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends RepositoryInterface<User> {
     User findByNickname(String nickname);
}

