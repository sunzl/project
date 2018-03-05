package com.nami.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nami.service.inte.HelloRemoteInterface;
import com.nami.service.inte.HelloRemoteInterface2;

@RestController
public class ConsumerController {

	@Autowired
    HelloRemoteInterface helloRemoteInterface;
	@Autowired
    HelloRemoteInterface2 helloRemoteInterface2;

    @RequestMapping("/hello")
    public String hello() {
        return helloRemoteInterface.hi();
    }
    
    @RequestMapping("/hello2")
    public String hello2() {
        return helloRemoteInterface2.hi();
    }
}
