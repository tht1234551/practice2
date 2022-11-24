package com.tourbest.erp.chat;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Component
public class ChatServer extends TextWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private Thread thread;

    private List<String> users = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();
    private Map<String, WebSocketSession> map = new HashMap<>();

    private String ip;
    private int port;

    public void network(String ip, int port, String id) {
        this.ip = ip;
        this.port = port;

        getSession().setAttribute("id", id);
    }

    public HttpSession getSession() {
        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttribute.getRequest().getSession();
    }

    public void close(String id) {
        try {
            messageListener("UserOut/" + id);
            users.remove(id);
            outputStream.close();
            inputStream.close();
            dataInputStream.close();
            dataOutputStream.close();
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

    public Socket_thread getSocketThread() {
        return new Socket_thread(this, dataInputStream, dataInputStream, outputStream, dataOutputStream, socket);
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

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("payload : " + payload);
        messageListener(payload);

        broadCast(message);
    }

    /* Client 가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info(session + " 클라이언트 접속");

        String query = Objects.requireNonNull(session.getUri()).getQuery();
        StringTokenizer stringTokenizer = new StringTokenizer(query, "=");
        String key = stringTokenizer.nextToken();
        String value = stringTokenizer.nextToken();

        try {
            users = new ArrayList<>();
            users.add(value);

            socket = new Socket(ip, port);
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

            map.put(value, session);
            messageListener("NewUser/" + value);

            send_message(value);

            thread = new Thread(getSocketThread());
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Client 가 접속 해제 시 호출되는 메서드드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(session + " 클라이언트 접속 해제");

        String query = Objects.requireNonNull(session.getUri()).getQuery();
        StringTokenizer stringTokenizer = new StringTokenizer(query, "=");
        String key = stringTokenizer.nextToken();
        String value = stringTokenizer.nextToken();

        map = map.entrySet()
                .stream()
                .filter(x -> x.getValue() != session)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        close(value);
        thread.interrupt();
    }
}
