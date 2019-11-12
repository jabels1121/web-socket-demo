package com.jaybe.websocketdemo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class DemoController extends AbstractRestController {

    /**
     * For getting JSESSIONID cookie
     * @return hello if basic auth success
     */
    @GetMapping(path = "/hello")
    public String greetings() {
        return "Hello!";
    }

    @GetMapping(path = "/time")
    public LocalDateTime getTime() {
        return LocalDateTime.now();
    }
}
