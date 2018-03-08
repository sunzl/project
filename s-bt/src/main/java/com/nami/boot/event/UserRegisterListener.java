package com.nami.boot.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import com.nami.boot.entity.User;
import com.nami.boot.service.UserService;
/**
 * 实现SmartApplicationListener，可以使我们的监听事件按照顺序执行。
 * 实现ApplicationListener或使用@EventListener注解监听不能保证顺序执行
 */
@Component
public class UserRegisterListener implements SmartApplicationListener{

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		//转换事件类型
        UserRegisterEvent userRegisterEvent = (UserRegisterEvent) event;
        //获取注册用户对象信息
        User user = userRegisterEvent.getUser();
        //.../完成注册业务逻辑
        System.out.println("注册信息："+user);

	}

	//事件执行的序号，越小越早执行
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
		//只有UserRegisterEvent监听类型才会执行下面逻辑
        return eventType == UserRegisterEvent.class;
	}

	@Override
	public boolean supportsSourceType(Class<?> sourceType) {
		//只有UserRegisterEvent监听类型才会执行下面逻辑
        return sourceType == UserService.class;
	}

}
