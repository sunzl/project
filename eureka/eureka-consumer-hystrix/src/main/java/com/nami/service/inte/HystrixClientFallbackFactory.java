package com.nami.service.inte;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<HelloRemoteInterface>{

	@Override
	public HelloRemoteInterface create(Throwable t) {
		System.out.println(t.getMessage());
		return ()->"服务不可用";
	}

}
