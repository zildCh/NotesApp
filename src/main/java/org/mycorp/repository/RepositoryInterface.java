package org.mycorp.repository;

import org.mycorp.models.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositoryInterface<T extends AbstractEntity> extends JpaRepository<T, Integer> {
}
