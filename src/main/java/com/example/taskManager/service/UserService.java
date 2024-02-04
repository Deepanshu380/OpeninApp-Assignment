package com.example.taskManager.service;

import com.example.taskManager.models.User;
import jakarta.persistence.EntityNotFoundException;

public interface UserService {


    void createOrUpdateUser(String phoneNumber);

}
