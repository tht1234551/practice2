package com.tourbest.erp.chat.socket.connection.management;

import com.tourbest.erp.chat.socket.connection.info.PayLoad;
import com.tourbest.erp.chat.socket.connection.info.SocketRequest;
import com.tourbest.erp.chat.socket.handler.SocketHandler;
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

    private SocketRequest socketRequest;

    public SocketManager(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void connectSocket(SocketRequest socketRequest) throws Exception {
        this.socketRequest = socketRequest;

        openSocket();
        openStream();
        createThread();

        setSocketRequest();
    }

    public void openSocket() throws IOException {
        socket = new Socket(socketRequest.getIp(), socketRequest.getPort());
    }

    private void openStream() throws IOException {
        inputStream = socket.getInputStream();
        dataInputStream = new DataInputStream(inputStream);
        outputStream = socket.getOutputStream();
        dataOutputStream = new DataOutputStream(outputStream);
    }

    public void createThread() {
        thread = new Thread(() -> {
            while (true) {
                try {
                    listen();
                } catch (IOException e) {
                    socketHandler.afterConnectionClosed(socketRequest);
                    close();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    close();
                    break;
                }
            }
        });

        thread.start();
    }

    public void setSocketRequest() {
        socketRequest.setSocket(socket);
        socketRequest.setThread(thread);
        socketRequest.setThreadStopFunction(this::close);
    }

    public void listen() throws IOException {
        String response = dataInputStream.readUTF();

        PayLoad payLoad = PayLoad
                .builder()
                .payload(response)
                .build();
        socketHandler.handleTextMessage(payLoad);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

