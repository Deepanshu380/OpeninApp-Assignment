// TaskResponse.java
package com.example.taskManager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskResponse {
    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private int priority;

    private String status;
}
