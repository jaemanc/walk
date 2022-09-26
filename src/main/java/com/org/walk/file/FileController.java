package com.org.walk.file;

import com.org.walk.file.dto.FileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags={"FileController"})
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileServiceImpl fileService;

    private final Logger log_error = LogManager.getLogger("com.error");
    private final Logger log_file = LogManager.getLogger("com.file");

    @PostMapping(value = "")
    @ApiOperation(value = "upload files", notes = " 파일 업로드 ")
    public ResponseEntity<?> uploadFile(
            @RequestPart(value="file", required = false) MultipartFile file
            ,FileDto fileDto
    ) {

        FileDto uploadedFiles = null;

        try {
            // aws s3 file upload
            uploadedFiles = fileService.uploadFile(file, fileDto );

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<FileDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FileDto>(uploadedFiles, HttpStatus.OK);
    }

    @GetMapping(value = "/{courseId}")
    @ApiOperation(value = "get preview File", notes = " 코스 미리보기 파일 조회 ")
    public ResponseEntity<?>  getPreviewFile(
            @RequestParam Long courseId
    ) {

        FileDto uploadedFiles = null;

        try {

            fileService.getPreviewFile(courseId);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<FileDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FileDto>(uploadedFiles, HttpStatus.OK);
    }


}
