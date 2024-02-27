package org.mycorp.services.converter;

import org.mycorp.models_buisnes.NoteDto;
import org.mycorp.models_dao.NoteDao;
import org.springframework.beans.factory.annotation.Autowired;

public class NoteConverter extends Converter<NoteDto, NoteDao>{

    CategoryConverter categoryConverter;

    @Autowired
    NoteConverter(CategoryConverter categoryConverter){
        this.categoryConverter=categoryConverter;
    }

    @Override
    public NoteDto convertToDto(NoteDao daoObj) {
        return new NoteDto(daoObj.getId(), categoryConverter.convertToDto(daoObj.getCategory()), daoObj.getNote(), daoObj.getDate(), daoObj.getHeader());
    }

    @Override
    public NoteDao convertToDao(NoteDto dtoObj) {
        return new NoteDao(dtoObj.getId(), categoryConverter.convertToDao(dtoObj.getCategoryDto()), dtoObj.getNote(), dtoObj.getDate(), dtoObj.getHeader());

    }

    @Override
    public NoteDao updateDao(NoteDao daoObj, NoteDto dtoObj) {
        daoObj.setCategory(categoryConverter.convertToDao(dtoObj.getCategoryDto()));
        daoObj.setNote(dtoObj.getNote());
        daoObj.setDate(dtoObj.getDate());
        daoObj.setHeader(dtoObj.getHeader());

        return daoObj;
    }
}
