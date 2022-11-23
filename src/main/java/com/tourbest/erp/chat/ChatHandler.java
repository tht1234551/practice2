//package com.tourbest.erp.chat;
//
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//
//@Component
//@RequiredArgsConstructor
//public class ChatHandler extends TextWebSocketHandler {
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Lazy
//    private final ChatServer chatServer;
//
//    private static List<WebSocketSession> list = new ArrayList<>();
//    private static Map<String, WebSocketSession> map = new HashMap<>();
//
//
//    public List<WebSocketSession> getList() {
//        return list;
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        logger.info("payload : " + payload);
//        chatServer.messageListener(payload);
//
//        for(WebSocketSession sess: new ArrayList<>(map.values())) {
//            sess.sendMessage(message);
//        }
//    }
//
//    /* Client 가 접속 시 호출되는 메서드 */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//        list.add(session);
//
//        logger.info(session + " 클라이언트 접속");
//    }
//
//    /* Client 가 접속 해제 시 호출되는 메서드드 */
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//
//        logger.info(session + " 클라이언트 접속 해제");
//        list.remove(session);
//    }
//}