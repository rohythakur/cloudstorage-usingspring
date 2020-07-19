package com.udacity.jwdnd.course1.cloudstorage.utils;

import org.springframework.stereotype.Component;

@Component
public class MessageUrlComposer {

    public String success(String message) {
        return "redirect:/result/success/" + message;
    }

    public String error(String message) {
        return "redirect:/result/error/" + message;
    }
}
