package com.nami.service.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
/**
 * 网关过滤器：
 * 把一些基础业务放到网关中处理，譬如：权限
 */
@Component
public class TokenFilter extends ZuulFilter {

	//run中是具体的执行业务逻辑
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		System.out.println("---====请求方法："+request.getMethod()+"，请求url："+request.getRequestURL().toString());
		String token = request.getParameter("token");// 获取请求的参数
		String htoken = request.getHeader("token");// 从请求头中获取
		if (StringUtils.isEmpty(token) && StringUtils.isEmpty(htoken)) {
			ctx.setSendZuulResponse(false); //不对其进行路由
			ctx.setResponseStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			ctx.setResponseBody("token is empty");
			ctx.set("isSuccess", false);
			return null;
        } else {
        	ctx.setSendZuulResponse(true); //对请求进行路由
        	ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
        	ctx.set("isSuccess", true);
        	return null;
        }
	}

	//是否要执行该过滤器
	@Override
	public boolean shouldFilter() {
		return true;
	}

	//过滤器的执行顺序，序号越小越先执行
	@Override
	public int filterOrder() {
		return 0;
	}

	//定义此过滤器是哪一种即什么时候执行
	//pre routting post error
	@Override
	public String filterType() {
		return "pre";
	}

}
