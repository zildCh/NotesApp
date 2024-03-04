package org.mycorp.controllers;

import org.mycorp.adapters.Adapter;
import org.mycorp.models.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{id_user}/category")
public class CategoryController {

    //final CategoryService categoryService;
    final Adapter adapterCategory;

    @Autowired
    public CategoryController(Adapter adapterCategory) {
        this.adapterCategory = adapterCategory;
    }


    @PostMapping
    public ResponseEntity<?> createEntity(@PathVariable int id_user, @RequestBody CategoryDao entity) {
        adapterCategory.createCategory(id_user, entity);
        return new ResponseEntity<>(entity.getId(), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable int id_user, @RequestBody CategoryDao entity, @PathVariable int id) {
        final boolean updated = adapterCategory.updateCategory(id, entity);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable int id_user, @PathVariable int id) {
        final boolean deleted = adapterCategory.deleteCategory(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
