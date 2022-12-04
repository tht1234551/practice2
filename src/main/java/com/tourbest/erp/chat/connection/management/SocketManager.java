package com.tourbest.erp.chat.connection.management;

import com.tourbest.erp.chat.connection.info.PayLoad;
import com.tourbest.erp.chat.connection.info.SocketRequest;
import com.tourbest.erp.chat.socket.SocketHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
@Getter
public class SocketManager {

    private Socket socket;
    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private Thread thread;
    @SuppressWarnings("FieldMayBeFinal")
    private SocketHandler socketHandler;

    public SocketManager(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void connectSocket(SocketRequest socketRequest) throws Exception {
        openSocket(socketRequest);
        openStream();
        createThread();

        setSocketRequest(socketRequest);
    }

    public void openSocket(SocketRequest socketRequest) throws IOException {
        socket = new Socket(socketRequest.getIp(), socketRequest.getPort());
    }

    private void openStream() throws IOException {
        inputStream = socket.getInputStream();
        dataInputStream = new DataInputStream(inputStream);
        outputStream = socket.getOutputStream();
        dataOutputStream = new DataOutputStream(outputStream);
    }

    public void createThread() {
        thread = ThreadManager.getThread(this);
        thread.start();
    }

    public void setSocketRequest(SocketRequest socketRequest) {
        socketRequest.setSocket(socket);
        socketRequest.setThread(thread);
        socketRequest.setThreadStopFunction(this::close);
    }

    public void listen() throws IOException {
        String response = dataInputStream.readUTF();
        log.info("소켓으로 수신했습니다 response : " + response);
        PayLoad payLoad = PayLoad
                .builder()
                .payload(response)
                .build();
        socketHandler.receiveSocketToSocket(payLoad);
    }

    public void send(PayLoad payload) throws IOException {
        dataOutputStream.writeUTF(payload.get());
    }

    public void close() {
        try {
            outputStream.close();
            inputStream.close();
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            thread.interrupt();

            log.info("소켓 i/o가 닫혔습니다");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

@Slf4j
class ThreadManager {
    public static Thread getThread(SocketManager socketManager) {
        return new Thread(() -> {
            log.info("소켓간 통신을 시작합니다");

            while (true) {
                try {
                    socketManager.listen();
                } catch (IOException e) {
                    socketManager.close();
                    break;
                }
            }

            log.info("소켓간 통신을 종료합니다");
        });
    }
}