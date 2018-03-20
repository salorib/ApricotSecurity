/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apricot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Argos
 */
@Controller
public class DefaultController {
    
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        model.addObject("welcomeMessage", "Welcome internet user");
        model.setViewName("/index");
        return model;
    }
    
    @RequestMapping("/admin")
    public ModelAndView admin() {
        ModelAndView model = new ModelAndView();
        model.addObject("adminMessage", "Welcome admin"
                + " this content is only available for users with admin role");
        model.setViewName("/admin/admin");
        return model;
    }
    
    @RequestMapping("/alumno")
    public ModelAndView user() {
        ModelAndView model = new ModelAndView();
        model.addObject("userMessage", "Welcome user "
                + "this contetn is only available for regular users");
        model.setViewName("/user/alumno");
        return model;
    }
    
    @RequestMapping("/403")
    public ModelAndView page() {
        ModelAndView model = new ModelAndView();
        model.addObject("errorMessage", "403 - You are not allowed to see this content");
        model.setViewName("/error/403");
        return model;
    }
    
}
