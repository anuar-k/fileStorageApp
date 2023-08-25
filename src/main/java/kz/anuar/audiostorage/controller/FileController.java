package kz.anuar.audiostorage.controller;

import kz.anuar.audiostorage.model.FileDto;
import kz.anuar.audiostorage.model.FileInfoDto;
import kz.anuar.audiostorage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileController {

    private StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> loadFile(@RequestParam("file") MultipartFile file){
        storageService.saveFile(file);
        return ResponseEntity.ok(file.getName());
    }

    @PostMapping("/command")
    public ResponseEntity<?> getFile(@RequestBody FileInfoDto commandDto){
        FileDto fileDto = storageService.doCommand(commandDto);
        if (Objects.isNull(fileDto)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }

}