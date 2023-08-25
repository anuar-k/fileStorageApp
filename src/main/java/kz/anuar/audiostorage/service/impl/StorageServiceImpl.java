package kz.anuar.audiostorage.service.impl;

import kz.anuar.audiostorage.config.StorageProperties;
import kz.anuar.audiostorage.model.FileDto;
import kz.anuar.audiostorage.model.FileEntity;
import kz.anuar.audiostorage.model.FileInfoDto;
import kz.anuar.audiostorage.repository.FileRepository;
import kz.anuar.audiostorage.repository.specification.FileSpecification;
import kz.anuar.audiostorage.service.StorageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private FileRepository fileRepository;

    private StorageProperties properties;

    private Path rootLocation;

    @Autowired
    public StorageServiceImpl(FileRepository fileRepository, StorageProperties properties) {
        this.properties = properties;
        this.fileRepository = fileRepository;
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void saveFile(MultipartFile file) {
        try {

            String originalFilename = file.getOriginalFilename();
            String fileName = originalFilename.substring(0, originalFilename.indexOf("."));

            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            LocalDateTime dateTime = LocalDateTime.parse(fileName, format);

            String savedLocalFilePath = saveLocalPath(file);

            FileDto fileDto = FileDto.builder()
                    .fileName(originalFilename)
                    .fileDate(Timestamp.valueOf(dateTime))
                    .filePath(savedLocalFilePath)
                    .build();

            if (findFile(fileDto)){
                FileEntity audio = FileEntity.builder()
                        .fileName(originalFilename)
                        .fileDate(Timestamp.valueOf(dateTime))
                        .filePath(savedLocalFilePath)
                        .build();
                fileRepository.save(audio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean findFile(FileDto filter) {
        List<FileEntity> files = fileRepository.findAll(new FileSpecification(filter));
        if (files.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public String saveLocalPath(MultipartFile file) {
        String path = null;
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            path = destinationFile.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
        return path;
    }

    @Override
    public FileDto doCommand(FileInfoDto fileDto) {
        FileEntity fileEntity = fileRepository.findByFileName(fileDto.getFileName())
                .orElseThrow(() -> new RuntimeException("File not found by name : " + fileDto.getFileName()));

        switch (fileDto.getCommand()) {
            case "fileinfo": {
                return FileDto.builder()
                        .fileName(fileEntity.getFileName())
                        .fileDate(fileEntity.getFileDate())
                        .filePath(fileEntity.getFilePath())
                        .build();
            }
            default: {
                return null;
            }
        }
    }
}