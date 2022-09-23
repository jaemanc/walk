package com.org.walk.file;

import com.org.walk.file.dto.FileDto;
import com.org.walk.user.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {

    FileDto uploadFile(MultipartFile file, FileDto fileDto);

}
