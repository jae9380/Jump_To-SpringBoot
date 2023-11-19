package com.example.jump_to_sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControllerTest {
    @GetMapping("/hello")
    @ResponseBody
    public String showHello(){
        return "Hello!";
    }
}
