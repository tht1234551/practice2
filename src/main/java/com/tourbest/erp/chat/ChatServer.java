package com.tourbest.erp.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

@Data
@Component
public class ChatServer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    Vector vector_user_list = new Vector();
    Vector vector_room_list = new Vector();
    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;

    public void network(String ip, int port, String id) {
        try {
            vector_user_list = new Vector();
            socket = new Socket(ip, port);
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

            send_message(id); //first connect -> send id
            vector_user_list.add(id);
            Thread thread = new Thread(getSocketThread());
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close(String id) {
        try {
            vector_user_list.remove(id);
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

    public void messageListener(String response) {
        StringTokenizer stringTokenizer = new StringTokenizer(response, "/");
        String protocol = stringTokenizer.nextToken();
        String message = stringTokenizer.nextToken();

        logger.info(response);

        switch (protocol) {
            case "NewUser":
            case "OldUser":
                vector_user_list.add(message);
                break;
            case "UserOut":
                vector_user_list.remove(message);
                break;
            case "NewRoom":
                vector_room_list.add(message);
                break;
            case "ExitRoom":
                vector_room_list.remove(message);
                break;
            default:
                break;
        }
    }

    public Socket_thread getSocketThread() {
        return new Socket_thread(this, dataInputStream, dataInputStream, outputStream, dataOutputStream, socket);
    }
}
