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
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        logger.info("payload : " + payload);
////        messageListener(payload);
////
////        broadCast(message);
//    }
//
//    /* Client 가 접속 시 호출되는 메서드 */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        logger.info(session + " 클라이언트 접속");
////
////        String query = Objects.requireNonNull(session.getUri()).getQuery();
////        StringTokenizer stringTokenizer = new StringTokenizer(query, "=");
////        String key = stringTokenizer.nextToken();
////        String value = stringTokenizer.nextToken();
////
////        try {
////            users = new ArrayList<>();
////            users.add(value);
////
////            socket = new Socket(ip, port);
////            inputStream = socket.getInputStream();
////            dataInputStream = new DataInputStream(inputStream);
////            outputStream = socket.getOutputStream();
////            dataOutputStream = new DataOutputStream(outputStream);
////
////            map.put(value, session);
////            messageListener("NewUser/" + value);
////
////            send_message(value);
////
////            thread = new Thread(getSocketThread());
////            thread.start();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//    /* Client 가 접속 해제 시 호출되는 메서드드 */
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        logger.info(session + " 클라이언트 접속 해제");
////
////        String query = Objects.requireNonNull(session.getUri()).getQuery();
////        StringTokenizer stringTokenizer = new StringTokenizer(query, "=");
////        String key = stringTokenizer.nextToken();
////        String value = stringTokenizer.nextToken();
////
////        map = map.entrySet()
////                .stream()
////                .filter(x -> x.getValue() != session)
////                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
////
////        close(value);
////        thread.interrupt();
//    }
//}