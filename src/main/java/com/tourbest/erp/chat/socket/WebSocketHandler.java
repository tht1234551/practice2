package com.tourbest.erp.chat.socket;

import com.tourbest.erp.chat.connection.info.PayLoad;
import com.tourbest.erp.chat.connection.info.SocketRequest;
import com.tourbest.erp.chat.connection.management.ConnectionManager;
import com.tourbest.erp.chat.util.QueryStringUtil;
import com.tourbest.erp.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Setter
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ConnectionManager manager;

    private SocketBridge bridge;



    public void sendWebSocketToWebSocket(PayLoad payLoad) throws IOException {
        String receiver = payLoad.getReceiver();

        if(StringUtil.isEmpty(receiver)) {
            log.info("웹소켓으로 모든 사용자에게 메시지를 전송합니다");
            TextMessage message = new TextMessage(payLoad.get().getBytes());

            Collection<SocketRequest> list = manager.getConnectUsers().values();

            for (SocketRequest request : list) {
                try {
                    request.getWebSocketSession().sendMessage(message);
                } catch (Exception e) {
                /*
                    소켓을 생성했을때는 아직 웹소켓이 없어서 전송이 안되기 때문에
                    미전송 메시지로 저장해두고
                    웹소켓이 연결되었을때 미전송 메시지가 있으면 전송하고
                    리스트를 비움
                 */
                    request.getUnsent().add(payLoad);
                }
            }
        } else {
            log.info("웹소켓으로 특정 사용자에게 메시지를 전송합니다 " + payLoad);
            TextMessage message = new TextMessage(payLoad.get().getBytes());
            manager.getSocketRequest(receiver).getWebSocketSession().sendMessage(message);
        }


    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("웹소켓에서 데이터를 받았습니다");
        PayLoad payLoad = PayLoad.builder()
                .payload(message.getPayload())
                .build();

        SocketRequest socketRequest = manager.getSocketRequest(session);
        socketRequest.setPayLoad(payLoad);

        bridge.receiveWebSocketToWebSocket(socketRequest);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = Objects.requireNonNull(session.getUri()).getQuery();
        Map<String, String> params = QueryStringUtil.toMap(query);

        String id = params.get("id");

        if (StringUtil.isEmpty(id)) {
            throw new Exception("id가 없습니다");
        }

        SocketRequest socketRequest = manager.getSocketRequest(id);
        socketRequest.setWebSocketSession(session);

        List<PayLoad> unsent = socketRequest.getUnsent();
        for (PayLoad payload : unsent) {
            payload.setReceiver(id);
            sendWebSocketToWebSocket(payload);
        }
        unsent.clear();

        // 자기 자신도 추가되었다는걸 웹소켓으로 알려줌
        sendWebSocketToWebSocket(
            PayLoad.builder()
                    .senderId("SERVER")
                    .receiver(id)
                    .payload("NewUser/" + id)
                    .build()
        );

        log.info("웹소켓이 연결되었습니다");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        SocketRequest socketRequest = manager.getSocketRequest(session);

        socketRequest.setPayLoad(
                PayLoad.builder().payload("UserOut/" + socketRequest.getId())
                        .build()
        );
        bridge.sendSocketToSocket(socketRequest);

        if (manager.disconnectUser(session)) {
            log.info("사용자를 접속자에서 제거 했습니다");
        } else {
            log.info("사용자는 접속자가 아닙니다");
        }

        log.info("웹소켓 연결이 종료되었습니다");
    }
}
