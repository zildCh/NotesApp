package org.mycorp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class Controller<T> {

    @GetMapping
    public abstract ResponseEntity<List<T>> getEntityById();

    @PostMapping
    public abstract ResponseEntity<?> createEntity(@RequestBody T entity);

    @PutMapping
    public abstract ResponseEntity<?> updateEntity(int id, @RequestBody T entity);

    @DeleteMapping
    public abstract ResponseEntity<?> deleteEntity(int id);

}