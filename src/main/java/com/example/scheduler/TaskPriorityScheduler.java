package com.example.scheduler;

import com.example.taskManager.models.Task;
import com.example.taskManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskPriorityScheduler {
    @Autowired
    private static TaskRepository taskRepository;
    private static final int INITIAL_DELAY_SECONDS = 0;
    private static final int SCHEDULE_INTERVAL_SECONDS = 10; // Adjust as needed

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        // Schedule the task priority updater to run periodically
        scheduler.scheduleAtFixedRate(TaskPriorityScheduler::updateTaskPriorities, INITIAL_DELAY_SECONDS, SCHEDULE_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    private static void updateTaskPriorities() {
        // Get the list of tasks from the database or another source
        List<Task> tasks = taskRepository.findAll();


        // Update the priority for each task based on the due date
        for (Task task : tasks) {
            int newPriority = calculatePriority(task.getDueDate());
            task.setPriority(newPriority);
            taskRepository.save(task);
        }
    }

    private static int calculatePriority(LocalDate dueDate) {
        long daysUntilDueDate = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);

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

