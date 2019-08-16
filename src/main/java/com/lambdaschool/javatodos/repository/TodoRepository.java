package com.lambdaschool.javatodos.repository;

import com.lambdaschool.javatodos.view.CountTodos;
import com.lambdaschool.javatodos.model.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TodoRepository extends CrudRepository<Todo, Long>
{
    @Query(value = "SELECT u.username, count(t.todosid) as counttodos FROM todos t JOIN users u on t.userid = u.userid GROUP BY u.username", nativeQuery = true)
    ArrayList<CountTodos> getCountTodos();
}