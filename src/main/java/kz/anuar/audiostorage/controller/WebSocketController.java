package kz.anuar.audiostorage.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/public")
    public String greeting(String message) throws Exception {
        return "Hello, " + HtmlUtils.htmlEscape(message) + "!";
    }
}