package kz.anuar.audiostorage.controller;

import kz.anuar.audiostorage.model.LogDto;
import kz.anuar.audiostorage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    private LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping()
    public ResponseEntity<?> loadFile(@RequestBody LogDto logDto){
        switch (logDto.getCommand()){
            case "addLog":{
                logService.save(logDto);
                break;
            }
            case "logs":{
                return new ResponseEntity<>(logService.findAll(), HttpStatus.OK);
            }
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
