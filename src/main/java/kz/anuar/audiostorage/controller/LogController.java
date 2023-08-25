package kz.anuar.audiostorage.controller;

import com.google.gson.Gson;
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

        Gson gson = new Gson();
        LogDto deserializedLogDto = gson.fromJson(logDto.getContent(), LogDto.class);

        System.out.println(deserializedLogDto);
        switch (deserializedLogDto.getCommand()) {
            case "addLog": {
                logService.save(deserializedLogDto);
                break;
            }
            case "logs": {
                return logService.findAll().stream().map(logEntity -> LogDto
                                .builder()
                                .content(logEntity.getContent())
                                .build())
                        .toList();
            }
            default: {
                break;
            }
        }
        return null;
    }
}
