package com.nami.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.nami.boot.entity.Assets;
import com.nami.boot.entity.User;
import com.nami.boot.mapper.AssetsMapper;
import com.nami.boot.service.UserService;

@RestController
public class TsController {

	// 用户业务逻辑实现
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Autowired
	AssetsMapper assetsMapper;

	/**
	 * 注册控制方法
	 * 
	 * @param user
	 *            用户对象
	 * @return
	 */
	@RequestMapping(value = "/register")
	public String register() {
		User user = new User();
		user.setName("Jack");
		user.setAge(25);
		user.setMail("jack@qq.com");
		// 调用注册业务逻辑
		userService.register(user);
		return "注册成功.";
	}
	
	
	@RequestMapping(value = "/asset")
	public Object asset() {
		
		return assetsMapper.selectByPrimaryKey(3013972);
	}
	
	@RequestMapping(value = "/assets")
	public Object listAssets() {
		//将参数传给这个方法就可以实现物理分页了，非常简单。
		PageHelper.startPage(1, 3);
        List<Assets> list = assetsMapper.selectAllUser();
        System.out.println("==list=="+list.size());
		return list;
	}

}
