package com.trabalhokanban.controller;

import com.trabalhokanban.model.Task;
import com.trabalhokanban.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*") // Adicionar se precisar de CORS
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Criar task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    // Listar todas as tasks
    @GetMapping
    public ResponseEntity<Map<String, List<Task>>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasksByColumn());
    }

    // Buscar task por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // Atualizar task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    // Mover task para próximo status
    @PutMapping("/{id}/move")
    public ResponseEntity<Task> moveTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.moveTask(id));
    }

    // Deletar task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}