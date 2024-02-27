package org.mycorp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryInterface<T> extends JpaRepository<T, Integer> {
}
