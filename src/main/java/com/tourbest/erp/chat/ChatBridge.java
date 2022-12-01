package com.tourbest.erp.chat;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public ChatBridge() {
        openWebSocket();
        closeWebSocket();
        receiveWebSocket();
    }

    public void openWebSocket() {
        webSocketHandler.setOpen(payload -> {
            logger.info("클라이언트 접속");
            PayLoadVo payLoadVo = new PayLoadVo(payload);
            users.add(payLoadVo.getValue());
            socketHandler.openSocket(ip, port);
        });
    }

    public void closeWebSocket() {
        webSocketHandler.setClose(payload -> {
            logger.info("클라이언트 닫힘");
            PayLoadVo payLoadVo = new PayLoadVo(payload);
            users.remove(payLoadVo.getValue());
        });
    }

    public void receiveWebSocket() {
        webSocketHandler.setReceive(payload -> {
            logger.info("");
            PayLoadVo payLoadVo = new PayLoadVo(payload);
        });
    }
}
