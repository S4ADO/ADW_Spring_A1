package com.taskmanager.spring.controller;

import com.taskmanager.spring.Domain.*;
import com.taskmanager.spring.service.TaskService;
import com.taskmanager.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Controller
public class TaskController
{
    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/addtask", method = RequestMethod.GET) // Would output to /user/register
    public String addTaskView(Model model, HttpSession session, RedirectAttributes redirectAttributes)
    {
        if(session.getAttribute("login") == null)
        {
            redirectAttributes.addAttribute("loginmsg", "You must be logged in to access that page");
            return "redirect:/login";
        }
        TaskForm taskForm = new TaskForm();
        model.addAttribute("task", taskForm);
        return "addtask";
    }

    @RequestMapping(value = "/addtask", method = RequestMethod.POST) // Would output to /user/register
    public String addTask(Model model, @Valid @ModelAttribute("taskform") TaskForm taskForm, BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes)
    {
        //Throw error form validation error
        if(bindingResult.hasErrors())
        {
            model.addAttribute("task", taskForm);
            model.addAttribute("message", "Fill in all fields");
            return "addtask";
        }
        else if(!taskForm.getDateComplete().matches("\\d{4}-\\d{2}-\\d{2}"))
        {
            model.addAttribute("task", taskForm);
            model.addAttribute("message", "You must input the correct date format (yyyy-mm-dd)");
            return "addtask";
        }

        Task task = new Task();
        task.setUserid((Long)session.getAttribute("userid"));
        task.setTitle(taskForm.getTitle());
        task.setBody(taskForm.getBody());
        task.setDateComplete(taskForm.getDateComplete());
        task.setCompleted(new Integer(0));
        Date date = new Date();
        task.setDateCreated(date.toString());
        taskService.save(task);
        redirectAttributes.addAttribute("homemsg", "Task added");
        return "redirect:/home";
    }

    @RequestMapping(value = "/edit/{task}", method = RequestMethod.GET)
    public String updateView(Model model, @PathVariable Task task, HttpSession session, RedirectAttributes redirectAttributes)
    {
        Task find = taskService.getTask(task.getTaskid(), (Long)session.getAttribute("userid"));
        if(find == null)
        {
            redirectAttributes.addAttribute("homemsg", "That task does not belong to you or does not exist");
            return "redirect:/home";
        }
        model.addAttribute("task", task);
        return "edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(Model model, @ModelAttribute("task") Task task, HttpSession session, RedirectAttributes redirectAttributes)
    {
        //Check if task exists and belongs to user
        Task find = taskService.getTask(task.getTaskid(), (Long)session.getAttribute("userid"));
        if(find != null)
        {
            //check for valid input
            if(task.getCompleted() < 0 || task.getCompleted() > 1 || !task.getDateComplete().matches("\\d{4}-\\d{2}-\\d{2}") )
            {
                redirectAttributes.addAttribute("homemsg", "Task date must be in the correct format and complete status should be either 0 or 1");
                return "redirect:/home";
            }
            find.setBody(task.getBody());
            find.setTitle(task.getTitle());
            find.setDateComplete(task.getDateComplete());
            find.setCompleted(task.getCompleted());
            taskService.save(find);
            redirectAttributes.addAttribute("homemsg", "Task updated");
            return "redirect:/home";
        }
        else
        {
            redirectAttributes.addAttribute("homemsg", "That task does not belong to you or does not exist");
            return "redirect:/home";
        }
    }

    @RequestMapping(value = "/delete/{task}", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable Task task, HttpSession session, RedirectAttributes redirectAttributes)
    {
        Task find = taskService.getTask(task.getTaskid(), (Long)session.getAttribute("userid"));
        if(find == null)
        {
            redirectAttributes.addAttribute("homemsg", "That task does not belong to you or does not exist");
            return "redirect:/home";
        }
        taskService.deleteTask(find.getTaskid());
        redirectAttributes.addAttribute("homemsg", "Task deleted");
        return "redirect:/home";
    }
}
