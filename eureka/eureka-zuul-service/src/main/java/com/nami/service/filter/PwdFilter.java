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
public class PwdFilter extends ZuulFilter {

	//run中是具体的执行业务逻辑
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		System.out.println("--->>>请求方法："+request.getMethod()+"，请求url："+request.getRequestURL().toString());
		String username = request.getParameter("password");
        if (null != username && username.equals("123456")) {
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
            ctx.set("isSuccess", true);
            return null;
        } else {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            ctx.setResponseBody("The password cannot be empty");
            ctx.set("isSuccess", false);
            return null;
        }
	}

	//是否要执行该过滤器
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		// 判断上一个过滤器结果为true，否则就不走下面过滤器，直接跳过后面的所有过滤器并返回 上一个过滤器不通过的结果。
		return (boolean) ctx.get("isSuccess");
	}

	//过滤器的执行顺序，序号越小越先执行
	@Override
	public int filterOrder() {
		return 1;
	}

	//定义此过滤器是哪一种即什么时候执行
	//pre routting post error
	@Override
	public String filterType() {
		return "route";
	}

}
