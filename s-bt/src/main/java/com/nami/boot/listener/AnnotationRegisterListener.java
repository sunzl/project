package com.nami.boot.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.nami.boot.entity.User;
import com.nami.boot.event.UserRegisterEvent;

//@EventListener
@Component
public class AnnotationRegisterListener {

	/**
	 * 注册监听实现方法
	 * 
	 * @param userRegisterEvent
	 *            用户注册事件
	 */
	//@EventListener
	public void register(UserRegisterEvent userRegisterEvent) {
		// 获取注册用户对象
		User user = userRegisterEvent.getUser();

		// ../省略逻辑

		// 输出注册用户信息
		System.out.println("@EventListener注册信息1：" + user);
	}
	
	@EventListener
	public void sendMail(UserRegisterEvent userRegisterEvent) {
		
		System.out.println("@EventListener注册信息1：");
	}

}
