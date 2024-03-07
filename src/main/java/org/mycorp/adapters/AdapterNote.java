package org.mycorp.adapters;

import org.mycorp.models.Note;

public interface AdapterNote{
    boolean createNote(int id_user, int id_category, Note note);
    boolean updateNote(int id_user, int id_category, int id, Note note);
    boolean deleteNote(int id_user, int id_category, int id);
}