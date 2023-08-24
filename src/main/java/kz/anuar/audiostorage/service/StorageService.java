package kz.anuar.audiostorage.service;

import kz.anuar.audiostorage.model.FileDto;
import kz.anuar.audiostorage.model.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void saveFile(MultipartFile file);

    String saveLocalPath(MultipartFile file);

    FileDto doCommand(FileInfoDto fileDto);
}