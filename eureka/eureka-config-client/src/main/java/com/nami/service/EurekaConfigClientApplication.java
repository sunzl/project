package com.nami.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * RefreshScope注解只能添加到controller上 并且要手动发送post请求刷新配置（http://localhost:8889/refresh）
 * 要在pom中添加 监控插件。因为refresh接口在该插件中，在配置文件中设置management.security.enabled=false，不需要验证
 */
@SpringBootApplication
//@EnableEurekaClient
@RefreshScope
@RestController
public class EurekaConfigClientApplication {
	
	@Value("${spring.datasource.url}")
    String content;
    @RequestMapping("/test")
    public String home() {
        return "content:" + content;
    }

	public static void main(String[] args) {
		SpringApplication.run(EurekaConfigClientApplication.class, args);
	}
}
