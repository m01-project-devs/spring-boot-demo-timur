package com.example.demo.controller;


import com.example.demo.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController
{

    private final HelloService helloService;

    public HelloController(HelloService helloService)
    {
        this.helloService = helloService;
    }
    @GetMapping("/hello")
    public String sayHello(@RequestParam String name)
    {
        return helloService.getGreeting(name);
    }
}
