package com.nami.boot.event;

import org.springframework.context.ApplicationEvent;

import com.nami.boot.entity.User;
/**
 * 在Spring内部中有多种方式实现监听如：
 * @EventListener注解、
 * 实现ApplicationListener泛型接口、
 * 实现SmartApplicationListener接口等
 */
public class UserRegisterEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7700336439735189441L;
	User user;
	/**
	 * 
	 * @param source 事件发起对象
	 * @param user 事件对象（监听对象）
	 */
	public UserRegisterEvent(Object source,User user) {
		super(source);
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	

}
