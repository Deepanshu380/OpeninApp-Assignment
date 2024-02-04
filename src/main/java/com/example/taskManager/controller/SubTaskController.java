package com.example.taskManager.controller;
// SubTaskController.java

import com.example.taskManager.dto.SubTaskRequest;
import com.example.taskManager.dto.SubTaskResponse;
import com.example.taskManager.models.SubTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.service.SubTaskService;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    // Create SubTask
    @PostMapping
    public ResponseEntity<SubTaskResponse> createSubTask(@RequestBody SubTaskRequest subTaskRequest) {
        SubTaskResponse createdSubTask = subTaskService.createSubTask(subTaskRequest);
        return new ResponseEntity<>(createdSubTask, HttpStatus.CREATED);
    }

    // Get all user subtasks with filter by task_id
    @GetMapping
    public ResponseEntity<List<SubTaskResponse>> getAllUserSubTasks(@RequestParam(required = false) Long taskId) {
        List<SubTaskResponse> subTasks = subTaskService.getAllUserSubTasks(taskId);
        return new ResponseEntity<>(subTasks, HttpStatus.OK);
    }

    // Update subtask
    @PutMapping("/{subTaskId}")
    public ResponseEntity<SubTask> updateSubTaskStatus(@PathVariable Long subTaskId, @RequestParam int status) {
        SubTask updatedSubTask = subTaskService.updateSubTaskStatus(subTaskId, status);
        return new ResponseEntity<>(updatedSubTask, HttpStatus.OK);
    }

    // Soft delete subtask
    @DeleteMapping("/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(@PathVariable Long subTaskId) {
        subTaskService.deleteSubTask(subTaskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
