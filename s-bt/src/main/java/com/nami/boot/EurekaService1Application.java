package com.nami.boot;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nami.boot.entity.User;

@SpringBootApplication
@Controller
@EnableCaching
@EnableAsync
@MapperScan("com.nami.boot.mapper")
public class EurekaService1Application {
	
	Logger logger = Logger.getLogger(EurekaService1Application.class);
	
	@RequestMapping("/")
	public String toIndex() {
		logger.debug("-------=------");
		logger.info("-------------");
		return "index";
	}
	
	
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value="/vaild",method=RequestMethod.POST)
	@ResponseBody
	public Object vaildOjb(@RequestBody @Valid User user,BindingResult result) {
		String mg = this.messageSource.getMessage("foo.message", new Object[]{"One","Two","Three"}, Locale.US);  
		System.out.println("===msg==="+mg);
		mg = this.messageSource.getMessage("foo.message", new Object[]{"一","二","三"}, Locale.getDefault()); 
		System.out.println("===msg==="+mg);
		if (result.hasErrors()) {
			StringBuffer b = new StringBuffer();
			List<FieldError> errors = result.getFieldErrors();
			errors.forEach(ferror->{
				String field = ferror.getField();
				String msg = "";
				msg = ferror.getDefaultMessage();
				if ("name".equals(field)) {
					msg = messageSource.getMessage(ferror.getField(), new Object[] {3,30},  Locale.getDefault());
				}
				b.append(field).append(" : ").append(msg).append(" ; ");
			});
			return b;
		}
		return null;
	}
	
	@RequestMapping(value="/cache",method=RequestMethod.GET)
	@ResponseBody
	@Cacheable(cacheNames= {"user"})
	public Object testcache() {
		User user = new User();
		user.setAge(20);
		user.setName("tt");
		user.setMail("tt@163.com");
		System.out.println("------------");
		return user;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaService1Application.class, args);
	}
}
