package org.mycorp.services;

import org.mycorp.models.Note;
import org.mycorp.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService extends ServiceImpl<Note>{

    @Autowired
    NoteService(RepositoryInterface<Note> rep) {
        super(rep);
    }

    @Override
    protected Note updateDao(Note newEntity, Note entityToUpdate) {
        entityToUpdate.setLink(newEntity.getLink());
        entityToUpdate.setNote(newEntity.getNote());
        entityToUpdate.setDate(newEntity.getDate());
        entityToUpdate.setHeader(newEntity.getHeader());
        return entityToUpdate;
    }
}
