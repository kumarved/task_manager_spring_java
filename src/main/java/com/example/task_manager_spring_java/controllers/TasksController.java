package com.example.task_manager_spring_java.controllers;

import com.example.task_manager_spring_java.dtos.CreateTaskDto;
import com.example.task_manager_spring_java.dtos.ErrorResponseDto;
import com.example.task_manager_spring_java.dtos.UpdateTaskDTO;
import com.example.task_manager_spring_java.entities.TaskEntity;
import com.example.task_manager_spring_java.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TaskService taskService;

    public TasksController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable("id") Integer id){
        var task = taskService.getTaskById(id);

        if(task == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDto body) throws ParseException {
        var task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());

        return ResponseEntity.ok(task);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleErrors(Exception e){
        if(e instanceof ParseException) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto("Invalid Date format"));
        }
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDto("Internal Server Error"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDTO body) throws ParseException {
        var task = taskService.updateTask(id, body.getDescription(), body.getDeadline(), body.getCompleted());

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }
}
