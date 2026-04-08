package com.example.TodoApiSpring;

import com.sun.net.httpserver.HttpsServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Todo>> getTodos(){
        return ResponseEntity.ok(todosList);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {

        // We can use this annotation to set STATUS CODE "@ResponseStatus(HttpStatus.CREATED)"

        todosList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

}
