package com.tourbest.erp.chat;

import com.tourbest.erp.chat.connection.info.PayLoad;
import com.tourbest.erp.chat.connection.info.SocketRequest;
import com.tourbest.erp.chat.socket.SocketBridge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SocketBridge bridge;

    @RequestMapping(value = "/view")
    public String view() {
        return "/chat/view";
    }

    @RequestMapping(value = "/list")
    public String list() {
        return "/chat/list";
    }

    @RequestMapping(value = "/connect")
    public String connect(HttpServletRequest request, HttpSession session, String ip, String port, String id) throws Exception {
        SocketRequest socketRequestInfo = SocketRequest.builder()
                .id(id)
                .port(port)
                .ip(ip)
                .httpServletRequest(request)
                .payLoad(
                        PayLoad.builder()
                                .senderId(id)
                                .payload("NewUser/" + id)
                                .build()
                )
                .build();

        session.setAttribute("id", id);

        try {
            bridge.connectSocket(socketRequestInfo);
            return "redirect:/chat/list";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
