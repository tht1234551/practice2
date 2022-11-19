package com.tourbest.erp.chat.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@RequiredArgsConstructor
public class ChatBridge {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String ip;
    private int port;
    private String id;

//    private Map<String, WebSocketSession> map = new HashMap<>();
//    private List<String> users = new ArrayList<>();
//    private List<String> rooms = new ArrayList<>();

    private final SocketHandler socketHandler;
    private final WebSocketHandler webSocketHandler;

//    @PostConstruct
//    public void init() {
//        socketHandler.setChatBridge(this);
//        webSocketHandler.setChatBridge(this);
//    }


    public void socketToWebSocket(String payload){
//        List<WebSocketSession> list = new ArrayList<>(map.values());
//
//        list.forEach(it -> {
//            try {
//                it.sendMessage(new TextMessage(payload.getBytes()));
//            } catch (Exception e) {
//
//            }
//        });
    }
    public void webSocketToSocket(String payload){
//        socketHandler.send_message(payload);
    }
}
