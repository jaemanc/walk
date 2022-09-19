package com.org.walk.file;

import com.org.walk.file.dto.FileDto;
import com.org.walk.user.dto.UserDto;

import java.io.File;
import java.util.List;

public interface FileService {

    FileDto uploadFile(File file, String category);

}
