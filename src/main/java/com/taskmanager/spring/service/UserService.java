package com.taskmanager.spring.service;

import com.taskmanager.spring.Domain.LoginForm;
import com.taskmanager.spring.Domain.User;
import com.taskmanager.spring.Domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRep;

    //To create a hash for the password
    private MessageDigest digest;

    //Get Hash
    private static String bytesToHex(byte[] hash)
    {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    //Set hash for user
    private User sethHashedPassword(User user)
    {
        try
        {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
        String hashedPass = bytesToHex(hash);
        user.setPassword(hashedPass);
        return user;
    }

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
                //u = sethHashedPassword(u);
                return userRep.save(u);
            }
            else
            {
                return null;
            }
        }
        else
        {
            //u = sethHashedPassword(u);
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

    //Get user input and compare to hashed pass
    public User validateLogin(LoginForm lf)
    {
/*      try
        {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(lf.getPassword().getBytes(StandardCharsets.UTF_8));
        String hashedPass = bytesToHex(hash);
        lf.setPassword(hashedPass);*/
        List<User> users = userRep.findByUsernameAndPassword(lf.getUsername(), lf.getPassword());
        if (users != null && users.size() > 0)
        {
            return users.get(0);
        }
        else
        {
            return null;
        }
    }
}
