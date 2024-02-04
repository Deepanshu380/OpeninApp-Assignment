package com.example.taskManager.repository;

import com.example.taskManager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByPriorityAndDueDate(Integer priority, LocalDate dueDate);

    List<Task> findAllByPriority(Integer priority);

    List<Task> findAllByDueDate(LocalDate dueDate);
}

