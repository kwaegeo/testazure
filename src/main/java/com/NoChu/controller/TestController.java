package com.NoChu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class TestController {

    @GetMapping("/test")
    public String Test(){
        return "Menu/Test";
    }


    @GetMapping("/test3")
    public String Test33(){
        return "/Menu/Test";
    }


}
