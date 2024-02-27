package org.mycorp.repository;


import org.mycorp.models_dao.NoteDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryNote extends RepositoryInterface<NoteDao>{
}