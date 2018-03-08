package com.nami.boot.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
	
	public static final String WEBSOCKET_USERNAME = "websocket_username";
	
	public static final String WEBSOCKET_USERNAME_ID = "websocket_username_id";

	private static final List<WebSocketSession> users;

	static {
		users = new ArrayList<>();
	}
	
	// 服务，根据条件查询，推送消息
	// @Autowired
	// private WebSocketService webSocketService;

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
		logger.info("websocket connection closed......");
        users.remove(session);
        logger.info("======afterConnectionClosed-total==="+users.size());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("connect to the websocket success......");
        users.add(session);
        logger.info("**** id="+session.getId());
        logger.info("======total==="+users.size());
        String userName = (String) session.getAttributes().get(WEBSOCKET_USERNAME);
        if(userName!= null){
            //查询未读消息
            //int count = webSocketService.getUnReadNews((String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME));
            session.sendMessage(new TextMessage(session.getId()));
        }
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		TextMessage textMessage = (TextMessage)message;
		//sendMessageToUsers();
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable arg1) throws Exception {
		if(session.isOpen()){
            session.close();
        }
        logger.info("websocket connection closed......");
        users.remove(session);
        logger.info("======handleTransportError-total==="+users.size());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	/**
     * 给所有在线用户发送消息
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(WEBSOCKET_USERNAME).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;
            }
        }
    }

}
