package org.mycorp.services;

import org.mycorp.models_buisnes.NoteDto;
import org.mycorp.models_dao.NoteDao;
import org.mycorp.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

public class NoteService extends ServiceImpl<NoteDao, NoteDto>{

    @Autowired
    NoteService(RepositoryInterface<NoteDao> rep) {
        super(rep);
    }

    @Override
    protected NoteDao convertToDAO(NoteDto dtoObj) {
        return new NoteDao(dtoObj.getId(), dtoObj.getCategoryDto(), dtoObj.getNote(), dtoObj.getDate(), dtoObj.getHeader());
    }

    @Override
    protected NoteDto convertToDTO(NoteDao daoObj) {
        return new NoteDto(daoObj.getId(), daoObj.getCategory(), daoObj.getNote(), daoObj.getDate(), daoObj.getHeader());
    }

    @Override
    protected NoteDao updateDAO(NoteDao daoObj, NoteDto dtoObj) {
        daoObj.setCategory(dtoObj.getCategoryDto());
        daoObj.setNote(dtoObj.getNote());
        daoObj.setDate(dtoObj.getDate());
        daoObj.setHeader(dtoObj.getHeader());

        return daoObj;
    }
}
