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

    private final ChatBridge chatBridge;

    @RequestMapping(value = "/view")
    public String view() {
        return "/chat/view";
    }

    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "/chat/list";
    }

    @RequestMapping(value = "/connect")
    public String connect(String ip, int port, String id, RedirectAttributes rttr) {
        rttr.addFlashAttribute("ip", ip);
        rttr.addFlashAttribute("port", port);
        rttr.addFlashAttribute("id", id);
        chatBridge.setIp(ip);
        chatBridge.setPort(port);
        chatBridge.setId(id);
        return "redirect:/chat/list";
    }
}
