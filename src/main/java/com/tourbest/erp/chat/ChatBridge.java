package com.tourbest.erp.chat;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class ChatBridge {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String ip;
    private int port;
    private String id;

    private List<String> users = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();

    private SocketHandler socketHandler = new SocketHandler();
    private WebSocketHandler webSocketHandler = new WebSocketHandler();

    private Map<String, WebSocketSession> map = new HashMap<>();

    public ChatBridge() {
        openWebSocket();
        closeWebSocket();
        receiveWebSocket();
    }

    public void openWebSocket() {
        webSocketHandler.setOpen((session, payload) -> {
            logger.info("클라이언트 접속 : " + payload);
            PayLoadVo payLoadVo = new PayLoadVo(payload);
            users.add(payLoadVo.getValue());

            map.put(id, session);

            socketHandler.openSocket(ip, port);
            socketHandler.send_message(id);
            try {
                session.sendMessage(new TextMessage(payload.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void closeWebSocket() {
        webSocketHandler.setClose((session, payload) -> {
            logger.info("클라이언트 닫힘 : " + payload);
            PayLoadVo payLoadVo = new PayLoadVo(payload);
            users.remove(payLoadVo.getValue());
            socketHandler.close(id);

            map.remove(id);
        });
    }

    public void receiveWebSocket() {
        webSocketHandler.setReceive((session, payload) -> {
            logger.info("메시지 받음 : " + payload);
            PayLoadVo payLoadVo = new PayLoadVo(payload);

            List<WebSocketSession> list = new ArrayList<>(map.values());
            list.forEach(it -> {
                try {
                    it.sendMessage(new TextMessage(payload.getBytes()));
                } catch (Exception e) {

                }
            });
        });

    }
}
