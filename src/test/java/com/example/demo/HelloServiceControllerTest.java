package com.example.demo;

import com.example.demo.service.HelloService;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class HelloServiceControllerTest
{
    private final HelloService helloService = new HelloService();
    @Test
    void testGetGreeting_withOutValidName()
    {
        String greeting = helloService.getGreeting("");
        assertEquals("Hello World!", greeting);
    }
    @Test
    void testGetGreeting_withValidName()
    {
        String greeting = helloService.getGreeting("Timur");
        assertEquals("Hello Timur", greeting);
    }
}
