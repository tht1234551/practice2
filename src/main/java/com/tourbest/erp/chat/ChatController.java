package com.tourbest.erp.chat;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatServer chatServer;

    @RequestMapping(value = "/view")
    public String view() {
        return null;
    }

    @RequestMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("list", chatServer.getVector_user_list());

        return null;
    }


    @RequestMapping(value = "/connect")
    public String connect(String ip, int port, String id, RedirectAttributes rttr) {
        chatServer.network(ip, port, id);
        rttr.addFlashAttribute("id", id);
        return "redirect:/chat/list";
    }

    @RequestMapping(value = "/disconnect")
    public String disconnect(String id) {
        chatServer.close(id);
        return "redirect:/chat/view";
    }


}
