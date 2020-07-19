package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result")
public class ResultController {

    @GetMapping("/success/{msg}")
    public String success(@PathVariable("msg") String message, Model model) {
        model.addAttribute("message", message);
        return "success";
    }

    @GetMapping("/error/{msg}")
    public String error(@PathVariable("msg") String message, Model model) {
        model.addAttribute("message", message);
        return "error";
    }
}
