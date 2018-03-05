package com.nami.service.inte;

import org.springframework.stereotype.Component;

@Component
public class HiHelloRemoteInterface2 implements HelloRemoteInterface2{

	@Override
	public String hi() {
		return "sorry service error";
	}

}
