package org.mycorp.services;

import org.mycorp.models_buisnes.NoteDto;
import org.mycorp.models_dao.NoteDao;
import org.mycorp.repository.RepositoryInterface;
import org.mycorp.services.converter.NoteConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class NoteService extends ServiceImpl<NoteDao, NoteDto>{

    @Autowired
    NoteService(RepositoryInterface<NoteDao> rep, NoteConverter converter) {
        super(rep, converter);
    }

}
