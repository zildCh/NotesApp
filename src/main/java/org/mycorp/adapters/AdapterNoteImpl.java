package org.mycorp.adapters;

import org.mycorp.models.Note;
import org.mycorp.models.UserCategoryLink;
import org.mycorp.services.NoteService;
import org.mycorp.services.UserCategoryLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdapterNoteImpl implements AdapterNote{

    final UserCategoryLinkService userCategoryLinkService;
    final NoteService noteService;

    @Autowired
    AdapterNoteImpl(UserCategoryLinkService userCategoryLinkService, NoteService noteService){
        this.userCategoryLinkService = userCategoryLinkService;
        this.noteService=noteService;
    }

    @Override
    public boolean createNote(int id_user, int id_category, Note note) {
        UserCategoryLink link = userCategoryLinkService.findLink(id_user, id_category);

        if(link==null)
            return false;

        note.setLink(link);
        noteService.create(note);
        return true;
    }

    @Override
    public boolean updateNote(int id_user, int id_category, int id, Note note) {
        UserCategoryLink link = userCategoryLinkService.findLink(id_user, id_category);

        if(link==null)
            return false;

        note.setLink(link);
        return noteService.update(note, id);
    }

    @Override
    public boolean deleteNote(int id) {
        return noteService.delete(id);
    }
}
