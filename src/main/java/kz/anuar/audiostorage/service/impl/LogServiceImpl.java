package kz.anuar.audiostorage.service.impl;

import kz.anuar.audiostorage.model.*;
import kz.anuar.audiostorage.repository.LogRepository;
import kz.anuar.audiostorage.service.LogService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class LogServiceImpl implements LogService {

    private LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void save(LogDto logDto) {
        logRepository.save(LogEntity
                .builder()
                .content(logDto.getContent())
                .build());
    }


    @Override
    public List<LogEntity> findAll() {
        return logRepository.findAll();
    }
}