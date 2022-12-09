package com.tourbest.erp.chat.socket.handler;

import com.tourbest.erp.chat.socket.connection.info.PayLoad;
import com.tourbest.erp.chat.socket.connection.info.SocketRequest;
import com.tourbest.erp.chat.socket.connection.management.ConnectionManager;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
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


    @Description("웹소켓 연결하기")
    public void connectSocket(SocketRequest socketRequest) throws Exception {
        socketHandler.connectSocket(socketRequest);
    }

    @Description("소켓이 연결되었을때")
    public void afterConnectionEstablished(SocketRequest socketRequest) throws Exception {

    }

    @Description("웹소켓으로 응답받았을 때")
    public void onReceiveWebSocket(SocketRequest socketRequest) throws IOException {
        socketHandler.send(socketRequest);
    }
    
    @Description("웹소켓으로 전송")
    public void sendWebSocket(SocketRequest socketRequest) throws IOException {
        webSocketHandler.send(socketRequest.getPayLoad());
    }

    @Description("소켓으로 응답받았을 때")
    public void onReceiveSocket(PayLoad payload) throws IOException {
        // 웹소켓으로 전송해줌
        webSocketHandler.send(payload);
    }

    @Description("소켓으로 전송하기")
    public void sendSocket(SocketRequest socketRequest) throws IOException {
        socketHandler.send(socketRequest);
    }

}
