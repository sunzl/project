package com.nami.boot.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class PlaceHolderPropertiesConfig {

	//MessageSource
	
	@Bean //【注意】这个bean的名字必须叫messageSource，否则无效  
    public MessageSource messageSource(){  
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();  
        //如果设置为-1，表示Cache forever。一般生产环境下采用-1，开发环境为了方便调测采用某个正整数，规范地我们可通过profile来定义  
        messageSource.setCacheSeconds(-1);   
        /* 设置缺省的资源文件的编码， 
         * 如果个别文件采用其他编码（不适用缺省编码，但一般我们应统一进行设置），可以通过setFileEncoding()来指定 
         * Properties properties = new Properties(); 
         * properties.setProperty("/WEB-INF/i18n/test_zh_CN", "GBK"); 
         * messageSource.setFileEncodings(properties);     */  
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());  
        //设置properties文件的basename，以便找到响应的资源文件  
        messageSource.setBasenames("/WEB-INF/i18n/messages", "/WEB-INF/i18n/errors","/WEB-INF/i18n/test");  
        return messageSource;  
    }  
}
