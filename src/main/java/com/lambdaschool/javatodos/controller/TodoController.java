package com.lambdaschool.javatodos.controller;

import com.lambdaschool.javatodos.model.Todo;
import com.lambdaschool.javatodos.service.TodoService;
import com.lambdaschool.javatodos.view.CountTodos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController
{
    @Autowired
    TodoService todoService;

    @GetMapping(value = "/todos",
            produces = {"application/json"})
    public ResponseEntity<?> listAllTodos()
    {
        List<Todo> allTodos = todoService.findAll();
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }


    @GetMapping(value = "/todo/{todoId}",
            produces = {"application/json"})
    public ResponseEntity<?> getTodo(
            @PathVariable
                    Long todoId)
    {
        Todo t = todoService.findTodoById(todoId);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }


    @GetMapping(value = "/username/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> findTodoByUsername(
            @PathVariable
                    String userName)
    {
        List<Todo> theTodos = todoService.findByUserName(userName);
        return new ResponseEntity<>(theTodos, HttpStatus.OK);
    }


    @GetMapping(value = "/todoscount",
            produces = {"application/json"})
    public ResponseEntity<?> getTodosCount()
    {
        ArrayList<CountTodos> myList = todoService.getCountTodos();
        myList.sort((q1, q2) -> q1.getUsername().compareToIgnoreCase(q2.getUsername()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }


    @PostMapping(value = "/todo")
    public ResponseEntity<?> addNewTodo(@Valid
                                         @RequestBody
                                                 Todo newTodo) throws URISyntaxException
    {
        newTodo = todoService.save(newTodo);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newTodoURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{todoid}").buildAndExpand(newTodo.getTodoid()).toUri();
        responseHeaders.setLocation(newTodoURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    /*
        PUT /todos/todoid/{todoid} - updates a
        to-do based on todoid. Can be done by any user.
         Note: null boolean is not a thing - it is false,
         so just set compeleted to whatever comes across
         in the RequestBody.
     */
    @PutMapping(value = "/todoid/{todoid}")
    public ResponseEntity<?> updateTodo(
            @RequestBody
                    Todo updateTodo,
            @PathVariable
                    long todoid)
    {
        todoService.update(updateTodo, todoid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}