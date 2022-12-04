package com.tourbest.erp.chat.socket.handler;

import com.tourbest.erp.chat.socket.connection.info.PayLoad;
import com.tourbest.erp.chat.socket.connection.info.SocketRequest;
import com.tourbest.erp.chat.socket.connection.management.ConnectionManager;
import com.tourbest.erp.chat.socket.connection.management.SocketManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
    

    // 소켓 연결
    public void connectSocket(SocketRequest socketRequestInfo) throws Exception {
        socketManager.connectSocket(socketRequestInfo);
    }

    // 소켓으로 보내기
    public void sendSocketToSocket(SocketRequest socketRequest) throws IOException {
        socketManager.send(socketRequest.getPayLoad());
    }
    
    // 소켓으로 받기
    public void receiveSocketToSocket(PayLoad payload) throws IOException {
        chatBridge.receiveSocketToSocket(payload);
    }

}
