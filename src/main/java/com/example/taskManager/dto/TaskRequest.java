
// TaskRequest.java
package com.example.taskManager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskRequest {
    private String title;

    private String description;

    private LocalDate dueDate;
}
