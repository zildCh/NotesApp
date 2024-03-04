package org.mycorp.controllers;

import org.mycorp.adapters.Adapter;
import org.mycorp.adapters.AdapterCategory;
import org.mycorp.models.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{id_user}/category")
public class CategoryController extends Controller<CategoryDao> {

    AdapterCategory adapterCategory;

    @Autowired
    public CategoryController(AdapterCategory adapterCategory) {
        super(adapterCategory);
    }


    @Override
    @PostMapping
    public ResponseEntity<?> createEntity(@PathVariable("id_user") int id_user, @RequestBody CategoryDao entity) {
        return super.createEntity(id_user, entity);
    }
}
