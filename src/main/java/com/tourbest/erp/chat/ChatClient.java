package com.tourbest.erp.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Socket socket = null;

    public void network(String ip, int port){
        try {
            socket = new Socket(ip, port);
            if(socket != null){ //socket ok!!
                logger.info("socket is not null");
                connection();
            }
        } catch (UnknownHostException e) {
            logger.warn("연결 실패");
        } catch (IOException e) {
            logger.warn("연결 실패");
        }
    }

    private void connection(){
//        try {
//            inputStream = socket.getInputStream();
//            dataInputStream = new DataInputStream(inputStream);
//            outputStream = socket.getOutputStream();
//            dataOutputStream = new DataOutputStream(outputStream);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
//        }
//        this.setVisible(true);
//        this.Login_GUI.setVisible(false);
//        send_message(id); //first connect -> send id
//        vector_user_list.add(id); //add my id in user_list
//        Thread thread = new Thread(new ChatClientTest.Socket_thread());
//        thread.start();

    }
}
