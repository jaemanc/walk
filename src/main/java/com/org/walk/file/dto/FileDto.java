package com.org.walk.file.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileDto {

    private Long fileId;

    private Long fileSize;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date uploadDate;

    private String fileLoc;

    private Character isDeleted;

    private Long userId;

    private String fileLatitude;

    private String fileLongitude;

    private Long coordinatesId;

    private Long courseId;

}
