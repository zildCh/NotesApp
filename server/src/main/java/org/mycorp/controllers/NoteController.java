package org.mycorp.controllers;

import org.mycorp.adapters.AdapterNote;
import org.mycorp.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users/{id_user}/category/{id_category}/notes")
public class NoteController{

    final AdapterNote adapterNote;

    @Autowired
    NoteController(AdapterNote adapterNote){
        this.adapterNote=adapterNote;
    }


    @PostMapping
    public ResponseEntity<?> createEntity(@PathVariable("id_user") int id_user, @PathVariable("id_category") int id_category, @RequestBody Note entity) {
        boolean created = adapterNote.createNote(id_user, id_category, entity);

        return created
                ? new ResponseEntity<>(entity.getId(), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable("id_user") int id_user, @PathVariable("id_category") int id_category, @PathVariable int id, @RequestBody Note entity) {
        boolean updated = adapterNote.updateNote(id_user, id_category, id, entity);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable("id_user") int id_user, @PathVariable("id_category") int id_category, @PathVariable int id) {
       boolean deleted = adapterNote.deleteNote(id_user, id_category, id);

       return deleted
               ? new ResponseEntity<>(HttpStatus.OK)
               : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
