package com.tourbest.erp.chat;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

@Data
@Component
public class ChatServer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    Vector vector_user_list = new Vector();
    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;

    public void network(String ip, int port, String id) {
        try {
            this.socket = new Socket(ip, port);
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

            send_message(id); //first connect -> send id
            addUserList(id);
            Thread thread = new Thread(getSocketThread());
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addUserList(String id) {
        vector_user_list.add(id);
    }

    public void removeUserList(String id) {
        vector_user_list.remove(id);
    }

    public void close(String id) {
        try {
            removeUserList(id);
            outputStream.close();
            inputStream.close();
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send_message(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket_thread getSocketThread() {
        return new Socket_thread(dataInputStream, dataInputStream, outputStream, dataOutputStream, socket);
    }


}
