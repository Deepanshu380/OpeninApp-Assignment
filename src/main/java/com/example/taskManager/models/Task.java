// Task.java
package com.example.taskManager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "task", schema = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private int priority;

    private String status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SubTask> subTasks;

    private LocalDate deletedAt;

    // Constructors, getters, and setters
}
