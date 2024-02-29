package org.mycorp.controllers;

import org.mycorp.models_dao.UserDao;
import org.mycorp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends Controller<UserDao>{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ResponseEntity<?> createEntity(UserDao entity) {
        userService.registration(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateEntity(int id, UserDao entity) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEntity(int id) {
        return null;
    }



    @GetMapping
    public ResponseEntity<UserDao> Authorisation(@PathVariable UserDao userToAuth) {
        final UserDao user = userService.authorisation(userToAuth);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
