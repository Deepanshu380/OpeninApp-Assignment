// TaskUpdateRequest.java
package com.example.taskManager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskUpdateRequest {
    private LocalDate dueDate;

    private String status;
}
