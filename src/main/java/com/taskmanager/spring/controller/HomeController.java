package com.taskmanager.spring.controller;

import com.taskmanager.spring.Domain.Task;
import com.taskmanager.spring.Domain.User;
import com.taskmanager.spring.service.TaskService;
import com.taskmanager.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController
{
    @Autowired
    UserService userService;;

    @Autowired
    TaskService taskService;

    //Redirect to login page if not logged in
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexView(Model model, HttpSession session)
    {
        if(session.getAttribute("login") != null)
        {
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    //Redirect to home page if logged in
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homeView(Model model, HttpSession session, RedirectAttributes redirectAttributes)
    {
        if(session.getAttribute("login") != null)
        {
            List<Task> currentUsersTasks = taskService.getAllTasksByUser((Long)session.getAttribute("userid"));
            model.addAttribute("tasks", currentUsersTasks);
            return "home";
        }
        else
        {
            redirectAttributes.addAttribute("loginmsg", "You must be logged in to access that page");
            return "redirect:/login";
        }
    }

    //List of users to keep track of
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model)
    {
        List<User> users = userService.getAll();
        List<Task> tasks = taskService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("tasks", tasks);
        return "index";
    }
}
