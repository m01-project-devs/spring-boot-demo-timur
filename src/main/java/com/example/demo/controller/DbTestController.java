package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/test")
public class DbTestController
{

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db")
    public String testDbConnection() throws Exception
    {
        try(Connection connection = dataSource.getConnection())
        {
            return "Connected to: " + connection.getMetaData().getURL();
        }
    }
}
