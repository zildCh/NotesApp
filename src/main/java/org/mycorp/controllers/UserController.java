package org.mycorp.controllers;

import org.mycorp.adapters.AdapterUser;
import org.mycorp.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController{

    final AdapterUser adapterUser;

    @Autowired
    public UserController(AdapterUser adapterUser) {
        this.adapterUser = adapterUser;
    }

    @PostMapping
    public ResponseEntity<?> createEntity(@RequestBody UserDao entity) {
        adapterUser.registration(entity);
        return new ResponseEntity<>(entity.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateEntity(@PathVariable int id, @RequestBody UserDao entity) {
        final boolean updated = adapterUser.updateUser(id, entity);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    public ResponseEntity<?> deleteEntity(@PathVariable int id) {
        final boolean deleted = adapterUser.deleteUser(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    public ResponseEntity<UserDao> Authorisation(@RequestBody UserDao userToAuth) {
        final UserDao user = adapterUser.getUser(userToAuth);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
