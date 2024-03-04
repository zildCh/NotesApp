package org.mycorp.controllers;

import org.mycorp.models.UserDao;
import org.mycorp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController extends Controller<UserDao>{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ResponseEntity<?> createEntity(@RequestBody UserDao entity) {
        userService.registration(entity);
        return new ResponseEntity<>(entity.getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateEntity(@PathVariable int id, @RequestBody UserDao entity) {
        final boolean updated = userService.update(entity, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Override
    public ResponseEntity<?> deleteEntity(@PathVariable int id) {
        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    public ResponseEntity<UserDao> Authorisation(@RequestBody UserDao userToAuth) {
        final UserDao user = userService.authorisation(userToAuth);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
