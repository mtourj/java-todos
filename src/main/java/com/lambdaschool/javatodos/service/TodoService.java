package com.lambdaschool.javatodos.service;


import com.lambdaschool.javatodos.model.Todo;
import com.lambdaschool.javatodos.view.CountTodos;

import java.util.ArrayList;
import java.util.List;

public interface TodoService
{
    List<Todo> findAll();

    Todo findTodoById(long id);

    List<Todo> findByUserName (String username);

    void delete(long id);

    Todo save(Todo todo);

    Todo update(Todo todo, long id);

    ArrayList<CountTodos> getCountTodos();
}