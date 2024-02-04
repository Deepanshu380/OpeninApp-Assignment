package com.example.taskManager.impl;

import com.example.taskManager.dto.SubTaskRequest;
import com.example.taskManager.dto.SubTaskResponse;
import com.example.taskManager.dto.TaskUpdateRequest;
import com.example.taskManager.models.SubTask;
import com.example.taskManager.models.Task;
import com.example.taskManager.repository.SubTaskRepository;
import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.service.SubTaskService;
import com.example.taskManager.impl.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taskManager.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubTaskServiceImpl implements SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;
   @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;
    @Override
    public SubTaskResponse createSubTask(SubTaskRequest subTaskRequest) {
        SubTask subTask = new SubTask();
        Optional<Task> task = taskRepository.findById(subTaskRequest.getTaskId());
        if(task.isPresent()) {
            subTask.setTask(task.get());
        }
        subTask.setStatus(0); // Assuming default status is incomplete
        subTaskRepository.save(subTask);
        return mapSubTaskToResponse(subTask);
    }


    @Override
    public SubTask updateSubTaskStatus(Long subTaskId, int status) {
        Optional<SubTask> optionalSubTask = subTaskRepository.findById(subTaskId);
        if (optionalSubTask.isPresent()) {
            SubTask subTask = optionalSubTask.get();
            subTask.setStatus(status);
             subTaskRepository.save(subTask);
            List<SubTaskResponse> allUserSubtasks= getAllUserSubTasks((subTask.getTask()).getId());
            List<SubTaskResponse> filteredAllUserSubtasks = allUserSubtasks.stream()
                    .filter(subtask -> subtask.getStatus() == 1)
                    .collect(Collectors.toList());
            TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
            if(filteredAllUserSubtasks.size()==allUserSubtasks.size()) {
                taskUpdateRequest.setStatus("DONE");
            }
            else
            {
                taskUpdateRequest.setStatus("IN-PROGRESS");
            }
            taskService.updateTask((subTask.getTask()).getId(), taskUpdateRequest);
            return subTask;
        } else {
            throw new EntityNotFoundException("SubTask not found with id: " + subTaskId);
        }
    }

    @Override
    public void deleteSubTask(Long subTaskId) {
        Optional<SubTask> optionalSubTask = subTaskRepository.findById(subTaskId);
        if (optionalSubTask.isPresent()) {
            SubTask subTask = optionalSubTask.get();
            subTask.setDeletedAt(LocalDateTime.now());
            subTaskRepository.save(subTask);
        } else {
            throw new EntityNotFoundException("SubTask not found with id: " + subTaskId);
        }
    }

    @Override
    public List<SubTaskResponse> getAllUserSubTasks(Long taskId) {
        List<SubTask> subTasks;
        if (taskId != null) {
            subTasks = subTaskRepository.findByTaskId(taskId);
        } else {
            subTasks = subTaskRepository.findAll();
        }
        return subTasks.stream().map(this::mapSubTaskToResponse).collect(Collectors.toList());
    }

    private SubTaskResponse mapSubTaskToResponse(SubTask subTask) {
        SubTaskResponse response = new SubTaskResponse();
        response.setId(subTask.getId());
        response.setStatus(subTask.getStatus());
        response.setCreatedAt(subTask.getCreatedAt());
        response.setUpdatedAt(subTask.getUpdatedAt());
        response.setDeletedAt(subTask.getDeletedAt());
        return response;
    }
}
