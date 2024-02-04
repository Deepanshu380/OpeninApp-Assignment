// TaskService.java
package com.example.taskManager.service;

import com.example.taskManager.dto.TaskRequest;
import com.example.taskManager.dto.TaskResponse;
import com.example.taskManager.dto.TaskUpdateRequest;
import com.example.taskManager.models.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task createTask(TaskRequest taskRequest);
    Task updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest);
    void deleteTask(Long taskId);
    List<TaskResponse> getAllUserTasks(Integer priority, LocalDate dueDate, int page, int size);
}
