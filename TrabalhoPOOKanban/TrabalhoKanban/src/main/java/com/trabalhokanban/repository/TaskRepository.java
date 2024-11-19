package com.trabalhokanban.repository;

import com.trabalhokanban.model.Task;
import com.trabalhokanban.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByOrderByPrioridadeDesc();
}