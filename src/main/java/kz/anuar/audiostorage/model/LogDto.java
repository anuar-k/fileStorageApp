package kz.anuar.audiostorage.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogDto {

    private String command;

    private String content;
}