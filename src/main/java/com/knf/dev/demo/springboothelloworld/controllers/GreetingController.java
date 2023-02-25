package com.knf.dev.demo.springboothelloworld.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/k8s")
public class GreetingController {

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        String msg = "Hello: " + name + " Welcome to k8s";
        log.info("{} ", msg);
        return msg;
    }
}
