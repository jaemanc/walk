package com.org.walk.file;

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

    private Date uploadDate;

    private String fileLoc;

    private Character isDeleted;

    private Long userId;

}
