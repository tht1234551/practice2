package com.tourbest.erp.chat.socket;

import com.tourbest.erp.chat.connection.info.PayLoad;
import com.tourbest.erp.chat.connection.info.SocketRequest;
import com.tourbest.erp.chat.connection.management.ConnectionManager;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Data
@Slf4j
@Component
@RequiredArgsConstructor
public class SocketBridge {

    private final ConnectionManager manager;
    private final SocketHandler socketHandler;
    private final WebSocketHandler webSocketHandler;

    @PostConstruct
    public void init() {
        socketHandler.setChatBridge(this);
        webSocketHandler.setBridge(this);
    }


    public void receiveWebSocketToWebSocket(SocketRequest socketRequest) throws IOException {
        socketHandler.sendSocketToSocket(socketRequest);
    }

    public void connectSocket(SocketRequest socketRequest) throws Exception {
        log.info("소켓 연결 시도중");

        socketRequest.setPayLoad(
                PayLoad.builder()
                        // 원래의 서버 프로그램에서 접속시에는 아이디만 받게 되어있어 아이디만 세팅하도록 함
                        .payload(socketRequest.getId())
                        .build()
        );

        socketHandler.connectSocket(socketRequest);
        socketHandler.sendSocketToSocket(socketRequest);
        manager.connectUser(socketRequest);

        log.info("소켓 연결 성공");
    }

    // 소켓으로 응답받았을때
    public void receiveSocketToSocket(PayLoad payload) throws IOException {
        // 웹소켓으로 전송해줌
        webSocketHandler.sendWebSocketToWebSocket(payload);
    }


    public void sendSocketToSocket(SocketRequest socketRequest) throws IOException {
        socketHandler.sendSocketToSocket(socketRequest);
    }

}
