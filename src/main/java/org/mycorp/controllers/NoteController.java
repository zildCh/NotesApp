package org.mycorp.controllers;

import org.mycorp.adapters.AdapterCategory;
import org.mycorp.adapters.AdapterImpl;
import org.mycorp.adapters.AdapterNote;
import org.mycorp.models.CategoryDao;
import org.mycorp.models.NoteDao;
import org.mycorp.services.NoteService;
import org.openjdk.tools.javac.util.JCDiagnostic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users/{id_user}/category/{id_category}/notes")
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

}
