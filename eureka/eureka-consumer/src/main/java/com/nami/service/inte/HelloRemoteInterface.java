package com.nami.service.inte;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(name = "eureka-provider")
public interface HelloRemoteInterface {

	@RequestMapping(value = "/hi")
    public String hi();
}
