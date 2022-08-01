package com.org.walk.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private Long fileId;

    private String fileCategory;

    private Long fileSize;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date uploadDate;

    private String fileLoc;

    private Character isDeleted;

    private Long userId;

}
