package com.taskmanager.spring.controller;

import com.taskmanager.spring.Domain.User;
import com.taskmanager.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET) // Would output to /user/register
    public String loginView(Model model)
    {
        model.addAttribute("message", "");
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET) // Would output to /user/register
    public String registerView(Model model)
    {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST) // Would output to /user/register
    public String register(Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResult)
    {
        //Throw error form validation error
        if(bindingResult.hasErrors())
        {
            model.addAttribute("user", user);
            model.addAttribute("message", "Fill in all fields");
            return "register";
        }

        User saved = userService.save(user, true);
        if (saved != null)
        {
            return "redirect:/";
        }
        else
        {
            model.addAttribute("message", "Username already exists");
            return "register";
        }
    }

    @RequestMapping(value = "/deleteuser/{user}", method = RequestMethod.GET)
    public String delete(@PathVariable User user)
    {
        userService.delete(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/update/{user}", method = RequestMethod.GET)
    public String updateView(Model model, @PathVariable User user)
    {
        model.addAttribute("user", user);
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Model model, @ModelAttribute("user") User user)
    {
        userService.save(user, false);
        return "redirect:/users";
    }
}
