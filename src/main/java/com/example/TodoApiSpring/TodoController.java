package com.example.TodoApiSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private static List<Todo> todosList;

    public TodoController(){
        todosList = new ArrayList<>();
        todosList.add(new Todo(1, false, "Todo 1", 1));
        todosList.add(new Todo(2, true, "Todo 2", 2));
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(){
        return ResponseEntity.ok(todosList);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {
        // We can use this annotation to set STATUS CODE "@ResponseStatus(HttpStatus.CREATED)"
        todosList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable("todoId") int todoId){
        for(Todo todo: todosList){
            if(todo.getId()==todoId){
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Todo with id " + todoId + " not found"));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable("todoId") int todoId){
        Optional<Todo> todo = todosList.stream().filter(x -> x.getId() == todoId).findFirst();
        if (todo.isPresent()) {
            todosList.removeIf(x -> x.getId() == todoId);
            return ResponseEntity.ok(todo.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Todo with id " + todoId + " not found"));
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable int todoId, @RequestBody Map<String, Object> updates) {
        for (Todo todo : todosList) {
            if (todo.getId() == todoId) {
                updates.forEach((key, value) -> {
                    switch (key) {
                        case "title":
                            todo.setTitle((String) value);
                            break;
                        case "completed":
                            todo.setCompleted((Boolean) value);
                            break;
                        case "userId":
                            todo.setUserId((Integer) value);
                            break;
                        case "id":
                            // Ignore ID update
                            break;
                        default:
                            throw new RuntimeException("Invalid field: " + key);
                    }
                });
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Todo with id " + todoId + " not found"));
    }
}
