package com.trabalhokanban.service;

import com.trabalhokanban.model.Task;
import com.trabalhokanban.repository.TaskRepository;
import com.trabalhokanban.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Criar nova tarefa (sempre começa em "A Fazer")
    public Task createTask(Task task) {
        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    // Listar tarefas organizadas por coluna
    public Map<String, List<Task>> getAllTasksByColumn() {
        List<Task> allTasks = taskRepository.findAll();

        Map<String, List<Task>> tasksByColumn = new HashMap<>();
        tasksByColumn.put("A Fazer", allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.TODO)
                .collect(Collectors.toList()));
        tasksByColumn.put("Em Progresso", allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                .collect(Collectors.toList()));
        tasksByColumn.put("Concluído", allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .collect(Collectors.toList()));

        return tasksByColumn;
    }

    // Mover tarefa para próxima coluna
    public Task moveTask(Long id) {
        Task task = getTaskById(id);
        switch (task.getStatus()) {
            case TODO:
                task.setStatus(TaskStatus.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                task.setStatus(TaskStatus.DONE);
                break;
            case DONE:
                throw new RuntimeException("Tarefa já está concluída");
        }
        return taskRepository.save(task);
    }

    // Atualizar tarefa
    public Task updateTask(Long id, Task taskDetails) {
        Task task = getTaskById(id);
        task.setTitulo(taskDetails.getTitulo());
        task.setDescricao(taskDetails.getDescricao());
        task.setPrioridade(taskDetails.getPrioridade());
        return taskRepository.save(task);
    }

    // Excluir tarefa
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Método auxiliar para buscar tarefa por ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }
}