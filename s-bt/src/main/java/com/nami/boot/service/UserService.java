package com.nami.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.nami.boot.entity.User;
import com.nami.boot.event.UserRegisterEvent;

@Service
public class UserService {

	@Autowired
    ApplicationContext applicationContext;

    /**
     * 用户注册方法
     * @param user
     */
    public void register(User user)
    {
        //../省略其他逻辑

        //发布UserRegisterEvent事件
        applicationContext.publishEvent(new UserRegisterEvent(this,user));
    }

}
