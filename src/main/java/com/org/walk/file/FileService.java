package com.org.walk.file;

import com.org.walk.file.dto.FileDto;
import com.org.walk.user.dto.UserDto;

import java.io.File;
import java.util.List;

public interface FileService {

    List<FileDto> uploadFiles(File[] files, String category);

    void uploadFilesHist(UserDto userDto);

}
