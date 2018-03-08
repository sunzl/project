package com.nami.service.inte;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(name = "eureka-provider",fallback=HiHelloRemoteInterface2.class)
public interface HelloRemoteInterface2 {

	@RequestMapping(value = "/hi")
    public String hi();
	
}
