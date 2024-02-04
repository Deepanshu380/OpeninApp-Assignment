package com.example.taskManager.impl;

import com.example.taskManager.dto.TaskRequest;
import com.example.taskManager.dto.TaskResponse;
import com.example.taskManager.dto.TaskUpdateRequest;
import com.example.taskManager.models.Task;
import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        // Set default priority and status
        task.setPriority(calculatePriority(LocalDate.from(taskRequest.getDueDate())));
        task.setStatus("TODO"); // Assuming the initial status is "TODO"
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (taskUpdateRequest.getDueDate() != null) {
                task.setDueDate(taskUpdateRequest.getDueDate());
                task.setPriority(calculatePriority(LocalDate.from(taskUpdateRequest.getDueDate())));
            }
            if (taskUpdateRequest.getStatus() != null) {
                task.setStatus(taskUpdateRequest.getStatus());
            }
            return taskRepository.save(task);
        } else {
            throw new EntityNotFoundException("Task not found with id: " + taskId);
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setDeletedAt(LocalDate.from(LocalDateTime.now()));
            taskRepository.save(task);
        } else {
            throw new EntityNotFoundException("Task not found with id: " + taskId);
        }
    }

    @Override
    public List<TaskResponse> getAllUserTasks(Integer priority, LocalDate dueDate, int page, int size) {
        // Implement pagination logic if needed
        List<Task> tasks ;
        if(priority!=null&&dueDate!=null) {
            tasks = taskRepository.findAllByPriorityAndDueDate(priority, dueDate);
        }
        else if(priority!=null) {
            tasks = taskRepository.findAllByPriority(priority);
        }
        else if(dueDate!=null) {
            tasks = taskRepository.findAllByDueDate( dueDate);
        }
       else {
            tasks = taskRepository.findAll();
        }
        return tasks.stream().filter(task -> task.getDeletedAt() == null).map(this::mapTaskToResponse).collect(Collectors.toList());
    }

    private TaskResponse mapTaskToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setPriority(task.getPriority());
        response.setStatus(task.getStatus());
        // Map subtasks if needed
        return response;
    }

    public int calculatePriority(LocalDate dueDate) {
        // Implement priority calculation logic based on due date
        // For simplicity, assuming priority 0 for tasks due today, priority 1 for tasks due tomorrow or day after tomorrow, etc.
        // You can adjust this logic based on your requirements
        LocalDate today = LocalDate.now();
        long daysUntilDueDate = ChronoUnit.DAYS.between(today, dueDate);
        if (daysUntilDueDate == 0) {
            return 0;
        } else if (daysUntilDueDate <= 2) {
            return 1;
        } else if (daysUntilDueDate <= 4) {
            return 2;
        } else {
            return 3;
        }
    }
}
