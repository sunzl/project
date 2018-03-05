package com.nami.boot.config;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
	
	private static Logger logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			/*ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
            	Map user = (Map) session.getAttribute(HttpSessionUtils.LOGIN_USER);
        		String userId = String.valueOf(user.get("userId"));
        		String userName = String.valueOf(user.get("userName"));
        		logger.info("**** userId="+userId+" userName="+userName+" , connection websocket.....");
                attributes.put(SystemWebSocketHandler.WEBSOCKET_USERNAME_ID,userId);
            }*/
            attributes.put(SystemWebSocketHandler.WEBSOCKET_USERNAME,UUID.randomUUID().toString());
        }
        return true;
	}

}
