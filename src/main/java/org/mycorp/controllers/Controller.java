package org.mycorp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class Controller<T> {

    @PostMapping
    public abstract ResponseEntity<?> createEntity(@RequestBody T entity);

    @PutMapping("/{id}")
    public abstract ResponseEntity<?> updateEntity(@PathVariable int id, @RequestBody T entity);

    @DeleteMapping("/{id}")
    public abstract ResponseEntity<?> deleteEntity(@PathVariable int id);

}