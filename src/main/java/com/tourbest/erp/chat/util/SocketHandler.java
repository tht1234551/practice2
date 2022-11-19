package com.tourbest.erp.chat.util;

import com.tourbest.erp.chat.util.origin.ChatServer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.function.Consumer;


@Data
@Component
public class SocketHandler {

    private ChatBridge chatBridge;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private ChatServer chatServer;
    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private Socket_thread socket_thread;

    private Thread thread;

    private List<String> users = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();
    private Map<String, WebSocketSession> map = new HashMap<>();

    Consumer<String> receive;
    Consumer<String> open;
    Consumer<String> close;



    public void openSocket(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            socket_thread = new Socket_thread();
            thread = new Thread(socket_thread);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(String id) {
        try {
            messageListener("UserOut/" + id);
            users.remove(id);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 윈도우 서버에 보내기
     *
     * @param message
     */
    public void send_message(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 스프링 서버가 받기
     *
     * @param response
     */
    public void messageListener(String response) {
        StringTokenizer stringTokenizer = new StringTokenizer(response, "/");
        String protocol = stringTokenizer.nextToken();
        String message = stringTokenizer.nextToken();

        logger.info(response);

        switch (protocol) {
            case "NewUser":
            case "OldUser":
                users.add(message);
                break;
            case "UserOut":
                users.remove(message);
                break;
            case "OldRoom":
            case "NewRoom":
                rooms.add(message);
                break;
            case "ExitRoom":
                rooms.remove(message);
                break;
            default:
                try {
                    throw new IllegalArgumentException("존재하지 않는 커맨드: " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
        }

        broadCast(response);
    }



    public void broadCast(TextMessage message) {
        try {
            List<WebSocketSession> list = new ArrayList<>(map.values());

            for (WebSocketSession sess : list) {
                sess.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadCast(String message) {
        TextMessage textMessage = new TextMessage(message.getBytes());
        broadCast(textMessage);
    }

    class Socket_thread implements Runnable {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        public void run() {
            while (true) {
                try {
                    listen();
                } catch (IOException e) {
                    close();
                    break;
                }
            }
        }

        public void listen() throws IOException {
            String payload = dataInputStream.readUTF();
//            chatServer.messageListener(a);
            receive.accept(payload);
        }

        public void close() {
            try {
                outputStream.close();
                inputStream.close();
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();

                logger.info("서버와 접속 끊어짐");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
