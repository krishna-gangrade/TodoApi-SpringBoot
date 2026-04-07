package com.example.TodoApiSpring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {

    private static List<Todo> todosList;

    public TodoController(){
        todosList = new ArrayList<>();
        todosList.add(new Todo(1, false, "Todo 1", 1));
        todosList.add(new Todo(2, true, "Todo 2", 2));
    }

    @GetMapping("/todos")
    public List<Todo> getTodos(){
        return todosList;
    }

    @PostMapping("/todos")
    public Todo createTodo(@RequestBody Todo newTodo) {
        todosList.add(newTodo);
        return newTodo;
    }


}
