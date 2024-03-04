package org.mycorp.controllers;

import org.mycorp.models.NoteDao;
import org.mycorp.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users/{userId}/category/{categoryId}/notes")
public class NoteController {

    final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<?> createEntity(@PathVariable int id_user, @PathVariable int id_category, @RequestBody NoteDao entity) {
        noteService.create(entity);
        return new ResponseEntity<>(entity.getId(), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable int id_user, @PathVariable int id_category, @PathVariable int id, @RequestBody NoteDao entity) {
        final boolean updated = noteService.update(entity, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable int id_user, @PathVariable int id_category, @PathVariable int id) {
        final boolean deleted = noteService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
