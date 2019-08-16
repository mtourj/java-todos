package com.lambdaschool.javatodos.controller;

import com.lambdaschool.javatodos.model.Todo;
import com.lambdaschool.javatodos.model.User;
import com.lambdaschool.javatodos.service.TodoService;
import com.lambdaschool.javatodos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    /*
    POST /users - adds a user. Can only be done by an admin.
     */
    @PostMapping(value = "", produces = {"application/json"})
    public ResponseEntity<?> createUser(@Valid @RequestBody User newUser, Authentication authentication) {
        System.out.println(authentication.toString());
        if(authentication.getName() == "admin") {

            newUser = userService.save(newUser);

            HttpHeaders response = new HttpHeaders();
            URI newUri = ServletUriComponentsBuilder.fromCurrentRequest().path("{todoid}").buildAndExpand(newUser.getUserid()).toUri();
            response.setLocation(newUri);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("You must be logged in as admin to perform this action", HttpStatus.FORBIDDEN);
        }
    }

    /*
    GET /users/mine - return the user and
    to-do based off of the authenticated user.
    You can only look up your own. It is okay if
    this also lists the users roles and authorities.
     */
    @GetMapping(value = "/mine",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserName(Authentication authentication)
    {
        System.out.println(authentication.getName());
        return new ResponseEntity<>(userService.findUserByName(authentication.getName()), HttpStatus.OK);
    }

    @PostMapping(value = "/todo/{userid}")
    public ResponseEntity<?> addNewTodo(@Valid @RequestBody Todo newTodo,
                                        @PathVariable long userid) throws URISyntaxException
    {
        newTodo.setUser(userService.findUserById(userid));

        newTodo = todoService.save(newTodo);

        HttpHeaders response = new HttpHeaders();
        URI newUri = ServletUriComponentsBuilder.fromCurrentRequest().path("{todoid}").buildAndExpand(newTodo.getTodoid()).toUri();
        response.setLocation(newUri);

        return new ResponseEntity<>( response, HttpStatus.CREATED );
    }

    /*
    DELETE /users/userid/{userid} - Deletes a user based off of their
    userid and deletes all their associated todos. Can only be done by an admin.
     */
    @DeleteMapping("/userid/{userid}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
                    long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
