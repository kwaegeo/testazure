package com.NoChu.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test2 {

    @GetMapping(value= "/test")
    public String test2(){
        return "Sample/index";
    }

    @GetMapping(value= "/test2")
    public String test3(){
        return "Sample/index2";
    }

}
