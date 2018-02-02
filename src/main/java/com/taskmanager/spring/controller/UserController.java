package com.taskmanager.spring.controller;

import com.taskmanager.spring.Domain.LoginForm;
import com.taskmanager.spring.Domain.User;
import com.taskmanager.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController
{
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET) // Would output to /user/register
    public String loginView(Model model)
    {
        LoginForm user = new LoginForm();
        model.addAttribute("message", "");
        model.addAttribute("user", user);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST) // Would output to /user/register
    public String login(Model model, @Valid @ModelAttribute("user") LoginForm user, BindingResult bindingResult, HttpSession session)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("user", user);
            model.addAttribute("message", "Fill in all fields");
            return "login";
        }

        User retrievedUser = userService.validateLogin(user);
        //No records matched for supplied username and password in database
        if(retrievedUser == null)
        {
            model.addAttribute("user", user);
            model.addAttribute("message", "Your details did not match any record in the database");
            return "login";
        }

        session.setAttribute("login", true);
        session.setAttribute("userid", retrievedUser.getId());
        session.setAttribute("username", retrievedUser.getUsername());
        return "redirect:/home";
    }

    //Deletes session variables and sends user to login page
    @RequestMapping(value = "/logout", method = RequestMethod.GET) // Would output to /user/register
    public String logout(Model model, HttpSession session, RedirectAttributes redirectAttributes)
    {
        session.removeAttribute("login");
        session.removeAttribute("userid");
        session.removeAttribute("username");

        redirectAttributes.addAttribute("loginmsg", "You have been logged out");
        return "redirect:/login";
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
        return "redirect:/users";
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
