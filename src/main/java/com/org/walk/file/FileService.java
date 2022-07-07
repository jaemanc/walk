package com.org.walk.file;

import com.org.walk.user.UserDto;
import com.org.walk.user.UserEntity;

import java.io.File;
import java.util.List;

public interface FileService {

    List<FileDto> uploadFiles(File[] files, String category);

    void uploadFilesHist(UserDto userDto);

}
