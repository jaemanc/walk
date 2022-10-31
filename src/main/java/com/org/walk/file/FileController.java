package com.org.walk.file;

import com.org.walk.file.dto.FileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            e.printStackTrace(pinrtStream);
            System.out.println(out.toString());
            log_error.error(e.getStackTrace());
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

    @GetMapping(value = "/{courseId}/list")
    @ApiOperation(value = "get preview File", notes = "코스 파일 목록 조회 ")
    public ResponseEntity<?>  getPreviewFileList(
            @RequestParam Long courseId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        
        FileDto uploadedFiles = null;

        try {
            
            Pageable pageable = PageRequest.of(page, size);

            fileService.getPreviewFiles(courseId, pageable);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<FileDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FileDto>(uploadedFiles, HttpStatus.OK);
    }


}
