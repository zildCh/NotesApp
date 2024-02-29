package org.mycorp.services;

import org.mycorp.models_dao.NoteDao;
import org.mycorp.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

public class NoteService extends ServiceImpl<NoteDao>{

    @Autowired
    NoteService(RepositoryInterface<NoteDao> rep) {
        super(rep);
    }

    @Override
    protected NoteDao updateDao(NoteDao newDao, NoteDao daoToUpdate) {
        daoToUpdate.setCategory(newDao.getCategory());
        daoToUpdate.setNote(newDao.getNote());
        daoToUpdate.setDate(newDao.getDate());
        daoToUpdate.setHeader(newDao.getHeader());
        return daoToUpdate;
    }
}
