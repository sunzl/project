package com.nami.boot.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.nami.boot.entity.User;
@Component
public class RegisterListener implements ApplicationListener<UserRegisterEvent>{

	@Override
	public void onApplicationEvent(UserRegisterEvent event) {
		//获取注册用户对象
        User user = event.getUser();

        //../省略逻辑

        //输出注册用户信息
        System.out.println("@EventListener注册信息2："+user);
	}

}
