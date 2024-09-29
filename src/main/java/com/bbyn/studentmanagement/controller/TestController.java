package com.bbyn.studentmanagement.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("someTest")
public class TestController {


    @GetMapping
    public String getTest(){
        return "Hello";
    }
}
