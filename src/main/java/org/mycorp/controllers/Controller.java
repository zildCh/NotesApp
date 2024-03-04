package org.mycorp.controllers;

import org.mycorp.adapters.Adapter;
import org.mycorp.models.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

public abstract class Controller<T extends AbstractEntity> {

    final Adapter<T> adapter;

    @Autowired
    public Controller(Adapter<T> adapter){
        this.adapter=adapter;
    }


    public ResponseEntity<?> createEntity(int idParent, T entity){
        boolean created = adapter.createEntity(idParent, entity);
        return created ? new ResponseEntity<>(entity.getId(), HttpStatus.OK)
                       : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    };

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable int id, @RequestBody T entity){
        final boolean updated = adapter.updateEntity(id, entity);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    };

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable int id){
        final boolean deleted = adapter.deleteEntity(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    };
}