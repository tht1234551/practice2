package com.tourbest.erp.chat;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatBridge extends WebSocketHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<String> users = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();
    WebSocketHandler webSocketHandler = new WebSocketHandler();
    SocketHandler socketHandler = new SocketHandler();

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
