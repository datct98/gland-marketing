package com.example.be_project.service;

import com.example.be_project.model.entities.Task;
import com.example.be_project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public void createTask(Task body, String creator){
        body.setCreatedBy(creator);
        //body.setCreatedAt(new Date());
        taskRepository.save(body);

    }
}
