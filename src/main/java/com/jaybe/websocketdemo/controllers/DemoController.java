package com.jaybe.websocketdemo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@Slf4j
public class DemoController {


    @GetMapping(path = "/hello")
    public String greetings() {
        return "Hello!";
    }

}
