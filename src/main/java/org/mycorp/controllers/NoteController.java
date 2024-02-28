package org.mycorp.controllers;

import org.mycorp.models_buisnes.NoteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController extends Controller<NoteDto> {
    @Override
    public ResponseEntity<List<NoteDto>> getEntityById() {
        return null;
    }

    @Override
    public ResponseEntity<?> createEntity(NoteDto entity) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateEntity(int id, NoteDto entity) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEntity(int id) {
        return null;
    }

}
