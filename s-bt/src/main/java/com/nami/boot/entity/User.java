package com.nami.boot.entity;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3052598582263862099L;

	@NotBlank
	@Length(min=3,max=30,message="姓名长度不符合要求")
	private String name;
	@NotNull
	//@Min(value=1,message="年龄不能小于1")
	//@Max(value=150,message="年龄不能大于150")
	@Range(min=1,max=150,message="年龄在1到150之间")
	private Integer age;
	@Email//(message="不符合邮件格式")
	private String mail;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", mail=" + mail + "]";
	}
	
	
}
