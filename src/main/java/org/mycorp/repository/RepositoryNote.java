package org.mycorp.repository;


import org.mycorp.models.NoteDao;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryNote extends RepositoryInterface<NoteDao>{
}