package com.tourbest.erp.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/view")
    public String view() {
        return null;
    }


    @RequestMapping(value = "/connect")
    public String connect(String ip, int port, String id) {
        System.out.println(ip);
        System.out.println(port);
        System.out.println(id);

        ChatClient client = new ChatClient();
        client.network(ip, port);

        return null;
    }


}
