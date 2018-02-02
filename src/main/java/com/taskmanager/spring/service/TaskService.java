package com.taskmanager.spring.service;

import com.taskmanager.spring.Domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

@Service
public class TaskService
{
    @Autowired
    private TaskRepository taskRep;

    //Check to make sure username doesn't already exist in the database
    public Task save(Task t)
    {
       return taskRep.save(t);
    }

    public List<Task> getAllTasksByUser(Long id)
    {
        return taskRep.findAllByUserid(id);
    }

    public List<Task> getAll()
    {
        return taskRep.findAll();
    }

    public void delete(Task t)
    {
        taskRep.delete(t);
    }

}
