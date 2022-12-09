package com.tourbest.erp.chat.socket.handler;

import com.tourbest.erp.chat.socket.connection.info.PayLoad;
import com.tourbest.erp.chat.socket.connection.info.SocketRequest;
import com.tourbest.erp.chat.socket.connection.management.ConnectionManager;
import com.tourbest.erp.chat.socket.connection.management.SocketManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.io.*;


@Slf4j
@Setter
@Getter
@Component
@RequiredArgsConstructor
public class SocketHandler {

    private final ConnectionManager manager;
    private SocketManager socketManager = new SocketManager(this);
    private SocketBridge chatBridge;


    @Description("소켓 연결하기")
    public void connectSocket(SocketRequest socketRequest) throws Exception {
        log.info("소켓 연결 시도중");

        socketRequest.setPayLoad(
                payLoadBuilder ->
                        payLoadBuilder.payload(socketRequest.getId())
        );


        socketManager.connectSocket(socketRequest);
        send(socketRequest);
        manager.connectUser(socketRequest);

        afterConnectionEstablished(socketRequest);
    }

    @Description("소켓이 연결되었을때")
    public void afterConnectionEstablished(SocketRequest socketRequest) throws Exception {
        chatBridge.afterConnectionEstablished(socketRequest);
        log.info("소켓이 연결 되었습니다");
    }

    public void afterConnectionClosed(SocketRequest socketRequest) {
        log.info("소켓이 닫혔습니다");
    }

    public void handleTextMessage(PayLoad payload) throws IOException {
        log.info("소켓으로 수신했습니다 response : " + payload.get());
        chatBridge.onReceiveSocket(payload);
    }

    @Description("소켓으로 데이터 전송")
    public void send(SocketRequest socketRequest) throws IOException {
        log.info("소켓으로 송신했습니다 request : " + socketRequest.getPayLoad().get());
        socketManager.send(socketRequest.getPayLoad());
    }

}
