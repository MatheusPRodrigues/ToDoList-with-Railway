package br.com.todolist.controller;

import br.com.todolist.dto.TaskRequestDto;
import br.com.todolist.dto.TaskResponseDto;
import br.com.todolist.service.ITasksInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private ITasksInterface tasksInterface;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto requestDto) {
        var response = tasksInterface.createTask(requestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        var response = tasksInterface.getAllTasks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        var response = tasksInterface.getTaskById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> modifyTask(@PathVariable Long id, @RequestBody TaskRequestDto requestDto) {
        var response = tasksInterface.modifyTask(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/conclude/{id}")
    public ResponseEntity<TaskResponseDto> concludeTask(@PathVariable Long id) {
        var response = tasksInterface.concludeTask(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/resetAll")
    public ResponseEntity<List<TaskResponseDto>> resetAllTasks() {
        var respnse = tasksInterface.resetAllTasks();
        return ResponseEntity.ok(respnse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        tasksInterface.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
