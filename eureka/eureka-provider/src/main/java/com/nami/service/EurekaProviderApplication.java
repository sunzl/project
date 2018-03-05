package com.nami.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaProviderApplication {

	@RequestMapping("/test")
    public String home() {
        return "Hello world";
    }
	
	@Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String hi() {
        return port+" 端口为您服务！";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaProviderApplication.class, args);
	}
}
