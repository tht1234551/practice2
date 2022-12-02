package com.tourbest.erp.chat;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
public class WebSocketHandler extends TextWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    BiConsumer<WebSocketSession, String> receive;
    BiConsumer<WebSocketSession, String> open;
    BiConsumer<WebSocketSession, String> close;




    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
//        logger.info("payload : " + payload);
//        messageListener(payload);

//        broadCast(message);

        receive.accept(session, payload);
    }

    /* Client 가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

//        logger.info(session + " 클라이언트 접속");

        String query = Objects.requireNonNull(session.getUri()).getQuery();
        StringTokenizer stringTokenizer = new StringTokenizer(query, "=");
        String key = stringTokenizer.nextToken();
        String value = stringTokenizer.nextToken();
        String payload = "NewUser/" + value;

        try {
//            users = new ArrayList<>();
//            users.add(value);
//
//            socket = new Socket(ip, port);
//            inputStream = socket.getInputStream();
//            dataInputStream = new DataInputStream(inputStream);
//            outputStream = socket.getOutputStream();
//            dataOutputStream = new DataOutputStream(outputStream);
//
//            map.put(value, session);
//            messageListener("NewUser/" + value);
//
//            send_message(value);
//
//            thread = new Thread(getSocketThread());
//            thread.start();
        } catch (Exception e) {
//            e.printStackTrace();
        }

        open.accept(session, payload);
    }

    /* Client 가 접속 해제 시 호출되는 메서드드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        logger.info(session + " 클라이언트 접속 해제");

        String query = Objects.requireNonNull(session.getUri()).getQuery();
        StringTokenizer stringTokenizer = new StringTokenizer(query, "=");
        String key = stringTokenizer.nextToken();
        String value = stringTokenizer.nextToken();
//
//        map = map.entrySet()
//                .stream()
//                .filter(x -> x.getValue() != session)
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//        close(value);
//        thread.interrupt();

        close.accept(session,"UserOut/" + value);
    }
}
