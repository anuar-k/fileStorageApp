package kz.anuar.audiostorage.service;

import kz.anuar.audiostorage.model.FileDto;
import kz.anuar.audiostorage.model.FileInfoDto;
import kz.anuar.audiostorage.model.LogDto;
import kz.anuar.audiostorage.model.LogEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LogService {
    void save(LogDto logDto);

    List<LogEntity> findAll();
}