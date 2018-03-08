package com.nami.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nami.service.inte.HelloRemoteInterface;

@RestController
public class ConsumerController {

	@Autowired
    HelloRemoteInterface helloRemoteInterface;

    @RequestMapping("/hello")
    public String hello() {
        return helloRemoteInterface.hi();
    }
}
