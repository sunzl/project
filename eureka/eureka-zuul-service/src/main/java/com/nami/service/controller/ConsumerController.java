package com.nami.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class ConsumerController {


    @RequestMapping("/hello")
    public String hello() {
        return null;
    }
}
