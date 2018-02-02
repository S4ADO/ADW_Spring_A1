package com.taskmanager.spring.service;

import com.taskmanager.spring.Domain.User;
import com.taskmanager.spring.Domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    UserRepository userRep;

    //Check to make sure username doesn't already exist in the database
    public User save(User u, boolean registering)
    {
        if(registering)
        {
            boolean userClash = false;
            List<User> ul = userRep.findAll();
            for (User user : ul)
            {
                //Check if username matches
                if (user.getUsername().toLowerCase().equals(u.getUsername().toLowerCase()))
                {
                    userClash = true;
                }
            }
            if (!userClash)
            {
                return userRep.save(u);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return userRep.save(u);
        }
    }

    public List<User> getAll()
    {
       return userRep.findAll();
    }

    public void deleteAll()
    {
        userRep.deleteAll();
    }

    public void delete(User u)
    {
        userRep.delete(u);
    }
}
