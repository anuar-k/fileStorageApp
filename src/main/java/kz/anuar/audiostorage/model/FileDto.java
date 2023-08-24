package kz.anuar.audiostorage.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class FileDto {

    private String fileName;

    private Timestamp fileDate;

    private String filePath;
}