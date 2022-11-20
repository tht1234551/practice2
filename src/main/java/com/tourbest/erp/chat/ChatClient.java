package com.tourbest.erp.chat;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

@Component
@RequiredArgsConstructor
public class ChatClient {

    private final ChatServer chatServer;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Socket socket = null;

    public void network(String ip, int port, String id){
        try {
            socket = new Socket(ip, port);
            if(socket != null){ //socket ok!!
                logger.info("socket is not null");
                connection(id);
            }
        } catch (UnknownHostException e) {
            logger.warn("연결 실패");
        } catch (IOException e) {
            logger.warn("연결 실패");
        }
    }

    InputStream inputStream;
    DataInputStream dataInputStream;
    OutputStream outputStream;
    DataOutputStream dataOutputStream;

    private void connection(String id){

        try {
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
        }
        send_message(id); //first connect -> send id
        chatServer.addUserList(id);
        Thread thread = new Thread(new Socket_thread(dataInputStream,dataInputStream,outputStream, dataOutputStream, socket));
        thread.start();

    }

    private void send_message(String message){ //button
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
