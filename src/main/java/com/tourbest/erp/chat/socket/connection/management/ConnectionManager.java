package com.tourbest.erp.chat.socket.connection.management;

import com.tourbest.erp.chat.socket.connection.info.SocketRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Data
public class ConnectionManager {
    private Map<String, SocketRequest> connectUsers = new HashMap<>();
    private Map<String, List<String>> users = new HashMap<>();
    private Map<String, List<String>> rooms = new HashMap<>();

    public void connectUser(SocketRequest socketRequestInfo) throws Exception {
        String id = socketRequestInfo.getId();

        SocketRequest request = getSocketRequest(id);

        if (request != null) {
            throw new Exception(id + "는 이미 서버에서 사용중인 아이디입니다");
        }

        connectUsers.put(id, socketRequestInfo);
    }

    public boolean disconnectUser(String id) {
        SocketRequest socketRequest = connectUsers.remove(id);

        if(socketRequest == null) return false;

        socketRequest.threadStop();

        return true;
    }

    public boolean disconnectUser(WebSocketSession session) {
        String id = findIdByWebSocketSession(session);
        return disconnectUser(id);
    }

    public SocketRequest getSocketRequest(String id) {
        return connectUsers.get(id);
    }

    public SocketRequest getSocketRequest(WebSocketSession session) {
        String id = findIdByWebSocketSession(session);
        return getSocketRequest(id);
    }

    private String findIdByWebSocketSession(WebSocketSession session) {
        return connectUsers
                .entrySet()
                .stream()
                .filter(x -> x.getValue().getWebSocketSession() == session)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("");
    }
}

//    /**
//     * 스프링 서버가 받기
//     *
//     * @param response
//     */
//    public void messageListener(String response) {
//        StringTokenizer stringTokenizer = new StringTokenizer(response, "/");
//        String protocol = stringTokenizer.nextToken();
//        String message = stringTokenizer.nextToken();
//
//        log.info(response);
//
//        switch (protocol) {
//            case "NewUser":
//            case "OldUser":
//                users.add(message);
//                break;
//            case "UserOut":
//                users.remove(message);
//                break;
//            case "OldRoom":
//            case "NewRoom":
//                rooms.add(message);
//                break;
//            case "ExitRoom":
//                rooms.remove(message);
//                break;
//            default:
//                try {
//                    throw new IllegalArgumentException("존재하지 않는 커맨드: " + response);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return;
//        }
//
//        broadCast(response);
//    }
