package org.mycorp.controllers;

import org.mycorp.adapters.AdapterUser;
import org.mycorp.models.User;
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
    public ResponseEntity<?> createEntity(@RequestBody User entity) {
        boolean created = adapterUser.registration(entity);
        return created
                   ? new ResponseEntity<>(HttpStatus.CREATED)
                   : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> updateEntity(@PathVariable int id, @RequestBody User entity) {
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

    @PatchMapping
    public ResponseEntity<User> authorisation(@RequestBody User userToAuth) {
        final User user = adapterUser.authorisation(userToAuth);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> readUser(@PathVariable int id) {
        final User user = adapterUser.getUser(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
