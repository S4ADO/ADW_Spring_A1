package com.taskmanager.spring.controller;

import com.taskmanager.spring.Domain.User;
import com.taskmanager.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController
{
    @Autowired
    UserService userService;

    //Redirect to login page
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model)
    {
        return "redirect:/login";
    }

    //List of users to keep track of
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String home(Model model)
    {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "index";
    }
}
