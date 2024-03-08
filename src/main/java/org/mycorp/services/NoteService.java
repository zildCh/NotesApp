package org.mycorp.services;

import org.mycorp.models.Note;
import org.mycorp.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class NoteService extends ServiceImpl<Note>{

    @Autowired
    NoteService(RepositoryInterface<Note> rep) {
        super(rep);
    }

    @Override
    protected Note updateEntity(@NotNull Note newEntity, @NotNull Note entityToUpdate) {
        entityToUpdate.setLink(newEntity.getLink());
        entityToUpdate.setNote(newEntity.getNote());
        entityToUpdate.setDate(newEntity.getDate());
        entityToUpdate.setHeader(newEntity.getHeader());
        return entityToUpdate;
    }
}
