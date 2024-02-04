// SubTaskService.java
package com.example.taskManager.service;

import com.example.taskManager.dto.SubTaskRequest;
import com.example.taskManager.dto.SubTaskResponse;
import com.example.taskManager.models.SubTask;

import java.util.List;

public interface SubTaskService {
    SubTaskResponse createSubTask(SubTaskRequest subTaskRequest);
    SubTask updateSubTaskStatus(Long subTaskId, int status);
    void deleteSubTask(Long subTaskId);
    List<SubTaskResponse> getAllUserSubTasks(Long taskId);
}
