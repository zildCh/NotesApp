package org.mycorp.controllers;

import org.mycorp.adapters.AdapterNote;
import org.mycorp.models.NoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category/{id_category}/notes")
public class NoteController extends Controller<NoteDao> {

    AdapterNote adapterNote;


    @Autowired
    public NoteController(AdapterNote adapterNote) {
        super(adapterNote);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> createEntity(@PathVariable("id_category") int id_category, @RequestBody NoteDao entity) {
        return super.createEntity(id_category, entity);
    }
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable int id, @RequestBody NoteDao entity, @PathVariable("id_category") int nested_id){
        final boolean updated = ((AdapterNote) adapter).updateEntity(id, nested_id, entity);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    };

}
