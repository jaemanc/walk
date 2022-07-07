package com.org.walk.file;

import com.org.walk.user.UserDto;
import com.org.walk.user.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@Api(tags={"FileController"})
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileServiceImpl fileService;

    @GetMapping("")
    @ApiOperation(value = "upload files", notes = " 파일 업로드 ")
    public ResponseEntity<?> uploadFile(
            HttpServletResponse httpServlet
            , String category
            , File[] files
    ) {

        UserDto userDto = new UserDto();
        
        try {

            //header의 id 값으로 userDto 조회 
            // httpServlet에서 조회 - > 업로드한 파일 셋팅 -> 업데이트

            // aws s3 file upload
            List<FileDto> uploadedFiles = fileService.uploadFiles(files, category );

            //userDto.setFiles(uploadedFiles);

            // db history log
            fileService.uploadFilesHist(userDto);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<FileDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }


}
