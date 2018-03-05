package com.nami.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nami.service.inte.HelloRemoteInterface;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@RestController
public class ConsumerController {

	//@Autowired
    HelloRemoteInterface helloRemoteInterface;
	
	@Autowired
    private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod="defaultStores")
    @RequestMapping("/hello")
    public String hello() {
        //return helloRemoteInterface.hi();
    	return restTemplate.getForEntity("http://eureka-provider/hi", String.class).getBody();
    }
    
    
    @RequestMapping("/test")
    public String test() {
    	return restTemplate.getForEntity("http://eureka-provider/test", String.class).getBody();
    }
    
    public String defaultStores() {
        return "Ribbon + hystrix ,提供者服务挂了";
    }
}
