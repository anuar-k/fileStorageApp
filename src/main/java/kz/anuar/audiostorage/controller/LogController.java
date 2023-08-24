package kz.anuar.audiostorage.controller;

import kz.anuar.audiostorage.model.LogDto;
import kz.anuar.audiostorage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LogController {

    private LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @MessageMapping("/send.command")
    @SendTo("/topic/public")
    public List<LogDto> sendMessage(@Payload LogDto logDto) {

        switch (logDto.getContent()) {
            case "addLog": default:{
                logService.save(logDto);
                break;
            }
            case "logs": {
                return logService.findAll().stream().map( logEntity -> LogDto.builder().content(logEntity.getContent()).build()).toList();
            }
        }
        return null;
    }
}
