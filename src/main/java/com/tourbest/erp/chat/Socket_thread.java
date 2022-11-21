package com.tourbest.erp.chat;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

@RequiredArgsConstructor
public class Socket_thread implements Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatServer chatServer;
    private final InputStream inputStream;
    private final DataInputStream dataInputStream;
    private final OutputStream outputStream;
    private final DataOutputStream dataOutputStream;
    private final Socket socket;



    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {
//                InMessage(dataInputStream.readUTF());
                String a = dataInputStream.readUTF();
                chatServer.messageListener(a);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                try {
                    outputStream.close();
                    inputStream.close();
                    dataInputStream.close();
                    dataOutputStream.close();
                    socket.close();

                    logger.info("서버와 접속 끊어짐");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                break;

            }
        }
    }
}
