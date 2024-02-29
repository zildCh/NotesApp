package org.mycorp.controllers;

import org.mycorp.models_dao.CategoryDao;
import org.mycorp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CategoryController extends Controller<CategoryDao> {

    final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Override
    public ResponseEntity<?> createEntity(CategoryDao entity) {
        categoryService.create(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateEntity(int id, CategoryDao entity) {
        final boolean updated = categoryService.update(entity, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Override
    public ResponseEntity<?> deleteEntity(int id) {
        final boolean deleted = categoryService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
