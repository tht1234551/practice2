package com.tourbest.erp.chat.socket.connection.info;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Builder
public class SocketRequest {

    private HttpSession httpSession;
    private WebSocketSession webSocketSession;
    private PayLoad payLoad;
    private List<PayLoad> unsent;
    private Socket socket;
    private Thread thread;

    private String id;
    private String ip;
    private int port;

    private Runnable threadStopFunction;

    public void threadStop() {
        try {
            threadStopFunction.run();
        } catch (NullPointerException e) {
            log.info("스레드 정지를 하지 못했습니다");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PayLoad> getUnsent() {
        if (unsent == null) {
            unsent = new ArrayList<>();
        }

        return unsent;
    }

    // lombok 으로 사용중이라 경고를 뜨는것이라 무시하도록 처리
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    public static class SocketRequestBuilder {
        private int port;

        public SocketRequestBuilder port(String port) {
            this.port = Integer.parseInt(port);

            return this;
        }
    }
}
