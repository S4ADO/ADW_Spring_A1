package com.taskmanager.spring.controller;

import com.taskmanager.spring.Domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user") //Top level dir
public class UserController
{
    @RequestMapping(value = "/register", method = RequestMethod.GET) // Would output to /user/register
    public String RegitserView(Model model)
    {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST) // Would output to /user/register
    @ResponseBody
    public String Regitser(Model model, @ModelAttribute("user") User user)
    {
        return "Registration for " + user.getUsername() + " with password " + user.getPassword();
    }
}
