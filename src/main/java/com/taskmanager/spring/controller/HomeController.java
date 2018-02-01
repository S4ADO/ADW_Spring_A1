package com.taskmanager.spring.controller;

import com.taskmanager.spring.Domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController
{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    //@ResponseBody - when we want to return a plain string without a template
    public String index(Model model)
    {
        User user = new User("Saad", "Pass");
        model.addAttribute("user", user);
        model.addAttribute("seconduser", new User("jack", "daniels"));
        return "index"; //Refers to index.html
    }

    //Another view
    //@RequestMapping(value = "/home", method = RequestMethod.GET)
    //@ResponseBody
    //public String home()
    //{
      //  return "eat ice cream";
    //}
}
