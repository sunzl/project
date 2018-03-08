package com.nami.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
/**
 *boot中两种方式配置jsp
 *1、采用属性文件（application.properties/yml）设view.prefix view.suffix
 *2、自定义配置类并继承 WebMvcConfigurerAdapter，实例化InternalResourceViewResolver，并重写configureViewResolvers方法
 */
@Configuration
public class ViewResolverConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public InternalResourceViewResolver resourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //请求视图文件的前缀地址
        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
        //请求视图文件的后缀
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
	}
	
	/**
     * 视图配置
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(resourceViewResolver());
        /*registry.jsp("/WEB-INF/jsp/",".jsp");*/
    }
	
}
